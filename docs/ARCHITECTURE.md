# Архитектура проекта

Проект — мод Minecraft 26.1.2 на NeoForge 26.1.2.71, Java 25, Gradle 9.3.0.
Точка входа: `net.ssc14.integration.neoforge.Ssc14ModEntrypoint`, чья регистрационная композиция
делегируется canonical `net.ssc14.integration.Ssc14Bootstrap`. Поддерживаемый исходный
код находится в `src/main/java/net/ssc14`, legacy compatibility facades — в
`src/main/java/net/mcreator/ssc`, ресурсы — в `src/main/resources`.

## Текущее устройство

| Подсистема | Основные классы | Ответственность |
| --- | --- | --- |
| Регистрация | `integration/neoforge/Ssc14ModEntrypoint`, `integration/Ssc14Bootstrap`, `init/*` | NeoForge entrypoint, регистрационная композиция, идентификаторы и события |
| Атмосфера | `atmosphere/*`, `AtmosCell`, `AtmosRegion`, `AtmosphereManager`, `AtmosphereSavedData` | Газовые расчёты, очередь регионов, мир и сохранения |
| Энергетика | `energy/*`, `EnergyNetworkManager`, `EnergySimulationServer` | Компоненты графа, бюджеты устройств, топология и распределение энергии |
| Химия | `common/chemistry/*`, `content/item/reagent/*`, `ReagentService` | Расчёт количеств, атомарные реакции, контейнерный runtime и серверный перенос |
| Сигнальные соединения | `NetworkManager`, `NetworkSavedData`, `INetworkAction` | Сохранение связей и выполнение действий |
| Броня | `combat/ResistanceRegistry`, `ArmorResistanceHelper`, `procedures/ModArmorSetupProcedure` | Чистые правила, Curios-адаптер и настройка |
| Интерфейс | `client/*`, `world/inventory/*`, `network/validation/*`, сетевые сообщения | Отрисовка, меню, ограничения пакетов и серверная синхронизация |
| Контент | `block/*`, `item/*`, `entity/*`, `procedures/*` | Игровые объекты и их обработчики |

## Направление рефакторинга

Описанное выше дерево фиксирует текущее состояние и ещё не является целевой
иерархией. Проверяемые слои, допустимые зависимости и поэтапный рецепт переноса
определены в [целевой архитектуре](TARGET_STRUCTURE.md).

1. **Домен**: вычисления и правила без Minecraft, NeoForge и Curios.
   Новые правила группируются по подсистемам, например `combat`.
2. **Адаптеры**: чтение игрового состояния, события, сеть, NBT и вызовы домена.
   Существующие публичные фасады сохраняются для совместимости с процедурами.
3. **Композиция и контент**: регистрация объектов, настройка правил, ресурсы.

Сейчас в коде сочетаются генерируемые процедуры, игровые адаптеры и выделенные
доменные правила. Разделение ещё не охватывает все подсистемы.
Операции чтения, добавления и удаления реагентов проходят через
`ReagentContainerAccess`; владелец chemistry runtime теперь
`content/item/reagent/ReagentService`; legacy NBT/GameTest callers переведены на
canonical owner, а `ModReagents` alias удалён.
Синхронизация access-полей ID-карт полностью проходит через
`IdJobAssignmentService`; `ID_Code_CHECKProcedure` сохраняет только совместимый
оркестрационный фасад и отдельные исторические блоковые helpers. Следующая
граница миграции — коммуникационная консоль: её обработчик пока объединяет
выбор ключа, серверную запись инвентаря, изменение `type` block state и снятие
ключа, поэтому перенос должен быть выделен в отдельный сервис с тестом
серверного авторитета.
Уже завершены прямые runtime-переходы для oxygen producer и debug gravity:
`GasProducerO2Block` вызывает `OxygenProducerService`, а
`DebugGravityGeneratorBlock` изменяет `StationGravityState` без промежуточных
adapter-классов. Их compatibility-тесты теперь проверяют canonical runtime
контракты. Аналогично station-code и network-button interaction boundaries
отбрасывают клиентские вызовы до legacy-адаптера. Fuel tank, C4 и nuclear
warhead blocks теперь напрямую вызывают canonical lifecycle services; их
block-level adapters удалены после прохождения compatibility и GameTest
проверок.
Upper closet и secure closet adapters пока сохраняются: legacy-процедура
одновременно выполняет access-config GUI, shift-операции и проверку доступа,
тогда как `ClosetInteractionService` отвечает только за структуру и обмен
предметами. Перед их удалением нужен отдельный access policy/service contract
с dedicated GameTest.
Оба оставшихся closet adapter-а уже имеют server-authority guard: клиентские
вызовы не доходят до legacy procedure, а серверные access-config и lock
операции сохраняются.
Mod ID, registry ID, NBT-ключи и порядок `GasType` являются частью формата
сохранений; форматы пакетов определяют совместимость клиента и сервера.
Исполняемый baseline этих контрактов и правила migration описаны в
[документе совместимости](COMPATIBILITY.md).

## Исторические исходники MCreator

`ssc_14.mcreator`, `elements/` и `models/` сохранены только как исторический источник ресурсов
и compatibility baseline. Runtime-код больше не генерируется и не поддерживается через MCreator;
новые подсистемы реализуются в target-layer и NeoForge bootstrap.
Перед регенерацией нужно зафиксировать изменения, проверить блокировку
редактируемых элементов в MCreator и затем просмотреть весь Git diff.
Не считать Java-файл защищённым от перезаписи только из-за ручных правок.
Новая доменная логика выносится из генерируемых процедур; после генерации
необходимо повторить сборку и тесты.

## Симуляция и хранение

`GasMixture` отвечает за количество вещества, температуру и диффузию. Изменение
смеси пробуждает ячейку и её регион. `AtmosRegion` обрабатывает до 2048 ячеек за
шаг; `AtmosphereManager` каждые четыре тика берёт до 128 готовых регионов.
Выгруженные чанки хранят ожидающую работу отдельно. Событие загрузки обрабатывается
на следующем тике, когда чанк доступен; проверяются его регионы и четыре соседа.
Открытые состояния шлюза соответствуют состояниям без коллизии: 11, 13 и 14.

`AtmosphereSavedData` хранит атмосферу каждого измерения под ID `ssc_14:atmosphere`.
Секции восстанавливаются по мере обращения; непосещённые сохранённые секции
остаются в следующем снимке. Ключи координат, ячеек, `m_<индекс>` и `temp`
сохранены. Порядок `GasType` менять без миграции нельзя.

Энергосети строятся только по загруженным кабелям. Удалённый мост разбивает граф
на компоненты, события block entity ставят перестроение в очередь. Для каждого
измерения и тика создаётся общий бюджет устройств: одно устройство, подключённое
к нескольким сетям, не получает повторный лимит. Источник отдаёт только принятую
потребителем энергию; батарея без нагрузки не разряжается. Целочисленный расход
лампы остаётся 1 Дж/тик при существующем отображении 12 Вт.

Химическая реакция сначала рассчитывает полный результат и вместимость, затем
записывает NBT. Недостаток ингредиентов, переполнение или нехватка места не
оставляют частично изменённую смесь. Перенос и глоток распределяют целые единицы
по составу смеси с сохранением суммарного объёма.

## Сеть и границы доверия

Сообщения зарегистрированы по направлению передачи. При декодировании проверяются
размеры строк и коллекций, а также числовые состояния GUI. Команды меню проверяют открытое
меню, координаты, расстояние и возможность взаимодействия; разрешённые клиентские
поля состояния перечислены явно. Перенос реагентов использует серверные предметы.
Газоанализатор получает снимок серверной атмосферы, клиентский кеш очищается при
отключении; декали удаляются из кеша при выгрузке чанка.

## Известные ограничения

## Статус миграции MCreator / MCreator migration status

Основные runtime-пути уже работают через канонические NeoForge-сервисы: кислородный
генератор, гравитация, топливный бак, C4, ядерная боеголовка, реестр паллет и
проверки доступа шлюза/шкафов. Оставшиеся классы `IntegrationService` являются
явными переходными границами, а не новой игровой архитектурой. Они нужны там, где
ещё сохраняются старые GUI, сетевые меню, формат сохранения или отдельные legacy API:
communication console, commerce console, station-code debug,
шкафы, portable menus и несколько строительных/оружейных взаимодействий.
Их удаление допустимо только после переноса соответствующего поведения и
регрессионной проверки сохранений.

Критический медицинский tick и расход жидкости работают через
`MedicalCriticalTickHandler` и `LiquidMetabolismTickHandler`; generated wrappers
для этих путей удалены после полного тестового прогона.

Управление движением игрока в zero-gravity работает через
`ZeroGravityPlayerHandler`; временная generated procedure удалена.

Client slot interaction работает через `InteractionKeyMappings` и
`InteractionSlotKeyHandler`; старое поле key mapping сохранено только как alias.

Рендер игрока регистрируется через канонический клиентский
`HumanModelRenderHandler`. Он сохраняет существующие registry render-state,
humanoid-моделей и race-атрибутов как явные legacy-входы; эти registry остаются
отдельной границей миграции и не дублируются молча.

Health HUD overlay зарегистрирован в canonical `net.ssc14.client.overlay`;
`net.mcreator.ssc.client.screens.HealthUIOverlay` сохранён как тонкий
compatibility facade с тем же static event-handler API.

Overlay газоанализатора зарегистрирован в canonical
`net.ssc14.client.overlay`; generated screen class сохранён только как
compatibility facade.

World-check HUD overlay зарегистрирован в canonical
`net.ssc14.client.overlay`; generated screen class сохранён как
compatibility facade с прежним static event-handler API.

Hunger/thirst HUD overlays зарегистрированы в canonical
`net.ssc14.client.overlay`; generated screen classes сохранены как
compatibility facades, а thresholds и animation-frame behavior не изменены.

Lamp and emergency-light block entities now share the canonical
`net.ssc14.content.block.lighting.LightingBlockEntity`. The generated entity
names remain constructor-compatible facades; registration, persistence, menu
behavior, and energy lifecycle are owned by the canonical lighting layer.

Сущности лампы и аварийного светильника теперь используют общий canonical
`net.ssc14.content.block.lighting.LightingBlockEntity`. Generated-имена сохранены
как совместимые фасады, а регистрация, сохранение, menu-поведение и energy
lifecycle принадлежат lighting-слою.

Communication-console menu construction and server-side opening now belong to
`CommunicationConsoleIntegrationService`; the composition root no longer embeds
an anonymous generated-menu adapter. The injectable adapter remains only as a
compatibility/test seam.

Создание меню и серверное открытие communication console теперь принадлежат
`CommunicationConsoleIntegrationService`; composition root больше не содержит
anonymous generated-menu adapter. Инъекционный adapter сохранён только как
compatibility/test seam.

Airlock controller mode handling and server-authority checks now live in
`AirlockControllerService` itself. The separate `LegacyAirlockControllerInteractionAdapter`
was removed; the `Adapter` port remains injectable for compatibility tests.

Обработка режимов airlock controller и server-authority checks теперь находятся
в самом `AirlockControllerService`. Отдельный
`LegacyAirlockControllerInteractionAdapter` удалён; порт `Adapter` сохранён для
совместимых тестовых замен.

RSU mode routing, construction/deconstruction dispatch and aiming-particle
updates now use the default adapter inside `RsuInteractionService`; the
separate `LegacyRsuInteractionAdapter` was removed while the injectable port
remains available.

Маршрутизация режимов RSU, dispatch строительства/демонтажа и aiming-particle
updates теперь выполняются default adapter внутри `RsuInteractionService`;
отдельный `LegacyRsuInteractionAdapter` удалён, а injectable port сохранён.

HUD overlays action-progress, firearm-ammo, multitool и health-analyzer также
зарегистрированы в canonical `net.ssc14.client.overlay`; generated screen
classes сохранены как compatibility facades с прежними event-handler API.

Standard closet block entities используют canonical shared
`net.ssc14.content.block.storage.ClosetBlockEntity`; generated entity names
сохранены как thin compatibility facades с прежними registry paths и display
names.

Маршрутизация right-click инструментов шлюза вынесена в
`AirlockToolInteractionHandler`. Он сохраняет порядок проверки access, welding,
manual opening, tile installation и deconstruction; legacy adapters остаются
явными входами до отдельной миграции их контрактов.

Персистентный журнал шлюза реализован в `AirlockEventLogService`; прежний
`AirlockEventLog` сохранён как тонкий compatibility facade для legacy/GameTest
вызовов.

Обработка физических redstone-сигналов вынесена в `AirlockSignalHandler`; старый
`AirlockSignals` сохранён как compatibility facade; canonical handler сохраняет
ключ `HOLD`.

Server-side AI control шлюза реализован в `AirlockAiControlService`; старый
`AirlockAiControl` сохраняет типы `Authority`/`Action` и делегирует вызов.

Удаление изолированной сваренной двери реализовано в `AirlockDisassemblyService`;
`AirlockDisassembly` сохранён как compatibility facade, а canonical tool handler
вызывает сервис напрямую.

Проверка двери и подача сетевого питания вынесены в `AirlockPowerService`;
`AirlockPower` сохранён как compatibility facade; energy topology использует
canonical service.

Server-authoritative wire edits теперь принадлежат canonical
`net.ssc14.content.block.airlock.AirlockWireService`; старый класс сохранён как
facade для network/menu callers.

Синхронизация permissions конфигуратора доступа вынесена в
`AirlockConfiguratorService`; старый `AirlockConfigurator` сохранён как facade
для menu/integration callers и прежнего формата access keys.

Планирование wire-pulse теперь принадлежит `AirlockWirePulseService`: deadline-
ключи, загрузка chunk и level-tick semantics сохранены; старый
`AirlockWirePulses` оставлен compatibility facade.

Welding jobs теперь принадлежат `AirlockWeldingService`; сохранены отмена при
движении/смене инструмента, fuel charge только при завершении и player-tick
семантика. Старый `AirlockWelding` сохранён как compatibility facade.

Manual-opening jobs теперь принадлежат `AirlockManualOpeningService`; сохранены
проверки powered/bolted/welded, дистанции, отмена движения и player-tick
семантика. Старый `AirlockManualOpening` сохранён как compatibility facade.

Оценка доступа двери вынесена в `AirlockAccessPolicyService`; сохранены emergency
bypass, unrestricted-door semantics и bitmask permissions. Старый
`AirlockAccessPolicy` сохранён как compatibility facade после переноса generated
open/close процедур.

В `net.mcreator.ssc.init` больше не осталось registry aliases для блоков и
предметов. `Ssc14ModBlocks` и `Ssc14ModItems` удалены после перевода всех
потребителей на `Ssc14Blocks` и `Ssc14Items`; миграции сохранили существующие
registry IDs и не добавили дублирующих регистраций.

`Ssc14Mod` теперь является тонким NeoForge entrypoint/compatibility facade, а
регистрационная композиция принадлежит `Ssc14Bootstrap`. Bootstrap напрямую
регистрирует canonical `Ssc14MenuTypes` и `Ssc14CreativeTabs`
напрямую; gravity holders, machine-cable item и blocks также читаются из
canonical registries. `Ssc14ModMenus` сохранён только как compatibility
alias/initialization для legacy callers; `Ssc14ModBlocks` и `Ssc14ModItems`
удалены после проверки отсутствия production consumers. Factory bindings меню принадлежат
`Ssc14MenuTypes`, а `Ssc14ModTabs` удалён после проверки отсутствия production
consumers.

Синхронизация wire/lamp state вынесена в `AirlockLampStateService`; сохранены
все persisted cable flags, power restoration и bolt-cut side effects. Старый
`CPlogLampPrProcedure` оставлен compatibility facade.

Инициализация состояния двери, верхней plug-части и случайной cable-схемы
вынесена в `AirlockStateInitializer`; старый `BaseAirlockD1PutProcedure` оставлен
facade для legacy callers.

Door lifecycle state machine (open/close, auto-close, signal requests and manual
open) теперь принадлежит `AirlockLifecycleService`; старый
`BaseAirlockOpenCloseProcedure` оставлен facade для generated callers.

Периодический radio-frequency tick коммуникационных серверов уже выполняется через
`CommunicationServerLifecycleService.activate`; legacy `Adapter.tick` оставлен только
как compatibility-hook для внешних интеграций. Методы menu/use по-прежнему владеют
GUI- и сетевым контрактом и требуют отдельной миграции поведения.

Portable-menu boundary больше не вызывает generated inventory procedure: генерация
яиц и PDA state выполняются canonical services; bridge оставлен для legacy menu и
network binding.

Целевые пакеты `net.ssc14` не импортируют `net.mcreator.ssc.procedures` и
generated `Ssc14ModVariables`; facade удалён после перевода compatibility
callers на `Ssc14WorldVariables`. Saved-data/runtime state теперь имеет одного
canonical owner, а remaining generated вызовы изолированы в bootstrap и явных
legacy adapters.

На текущем контрольном прогоне dedicated NeoForge GameTest все 217 обязательных
сценариев прошли успешно; сервер также корректно сохранил все три измерения при
завершении.

The primary runtime paths now use canonical NeoForge services: oxygen production,
gravity, fuel tanks, C4, nuclear warhead detonation, pallet registry and airlock/
closet access checks. Remaining `IntegrationService` classes are explicit migration
boundaries rather than a new gameplay architecture. They are retained where legacy
GUIs, menu networking, save formats or isolated legacy APIs are still involved:
communication and commerce consoles, station-code debug, closets,
portable menus, and several construction/firearm interactions. Each bridge
must be removed only after its behavior and save compatibility are migrated and
covered by regression tests.

Energy topology, device mapping, telemetry and LV batching now live under the
canonical `net.ssc14.content.energy` package. The former
`net.mcreator.ssc.energy.*` classes remain only as deprecated compatibility
facades and are not part of the canonical runtime.

Reagent registration, reaction planning and container operations now live in
`net.ssc14.content.item.reagent.ReagentService`; the `net.mcreator.ssc.ModReagents`
alias was removed after generated callers and GameTests moved to the canonical owner.

Critical medical ticking and liquid metabolism now run through
`MedicalCriticalTickHandler` and `LiquidMetabolismTickHandler`; their generated
wrappers were removed after the full test suite passed.

Zero-gravity player movement now runs through `ZeroGravityPlayerHandler`; the
temporary generated procedure was removed.

Client slot interaction now runs through `InteractionKeyMappings` and
`InteractionSlotKeyHandler`; the old key-mapping field remains only as an alias.

Player rendering now registers through canonical `HumanModelRenderHandler` in the
client layer. The handler preserves the existing render-state, humanoid-model and
race-attribute registries as explicit legacy inputs; those registries remain a
separate migration boundary and are not silently duplicated.

The health HUD overlay is registered in canonical `net.ssc14.client.overlay`;
`net.mcreator.ssc.client.screens.HealthUIOverlay` remains a thin compatibility
facade with the same static event-handler API.

The gas analyser HUD overlay is also registered in canonical
`net.ssc14.client.overlay`; its generated screen class remains only as a
compatibility facade.

The world-check HUD overlay is registered in canonical
`net.ssc14.client.overlay`; its generated screen class remains a compatibility
facade with the existing static event-handler API.

The hunger/thirst HUD overlays are registered in canonical
`net.ssc14.client.overlay`; generated screen classes remain compatibility
facades, with thresholds and animation-frame behavior unchanged.

Action-progress, firearm-ammo, multitool, and health-analyzer HUD overlays are
also registered in canonical `net.ssc14.client.overlay`; generated screen
classes remain compatibility facades with their existing event-handler APIs.

Standard closet block entities use the canonical shared
`net.ssc14.content.block.storage.ClosetBlockEntity`; generated entity names
remain thin compatibility facades with their existing registry paths and
display names.

Airlock tool right-click routing now lives in `AirlockToolInteractionHandler`.
It preserves the access, welding, manual-opening, tile-installation, and
deconstruction check order; legacy adapters remain explicit inputs until their
contracts are migrated separately.

Persistent airlock logging is implemented by `AirlockEventLogService`; the old
`AirlockEventLog` remains a thin compatibility facade for legacy/GameTest calls.

Physical redstone signal handling now lives in `AirlockSignalHandler`; the old
`AirlockSignals` remains a compatibility facade; the canonical handler preserves
the `HOLD` key.

Server-side airlock AI control is implemented by `AirlockAiControlService`; the
old `AirlockAiControl` preserves the `Authority`/`Action` types and delegates.

Isolated welded-door removal is implemented by `AirlockDisassemblyService`;
`AirlockDisassembly` remains a compatibility facade, while the canonical tool
handler calls the service directly.

Door identification and network-power supply now live in `AirlockPowerService`;
`AirlockPower` remains a compatibility facade; energy topology uses the
canonical service.

Server-authoritative wire edits now belong to canonical
`net.ssc14.content.block.airlock.AirlockWireService`; the old class remains a
facade for network/menu callers.

Access-configurator permission synchronization now lives in
`AirlockConfiguratorService`; the old `AirlockConfigurator` remains a facade
for menu/integration callers and the existing access-key format.

Wire-pulse scheduling now belongs to `AirlockWirePulseService`; deadline keys,
chunk loading, and level-tick semantics are preserved, while the old
`AirlockWirePulses` remains a compatibility facade.

Welding jobs now belong to `AirlockWeldingService`; cancellation on movement or
tool changes, completion-only fuel charging, and player-tick semantics are
preserved. The old `AirlockWelding` remains a compatibility facade.

Manual-opening jobs now belong to `AirlockManualOpeningService`; powered,
bolted, welded, distance, movement-cancellation, and player-tick semantics are
preserved. The old `AirlockManualOpening` remains a compatibility facade.

Door access evaluation now lives in `AirlockAccessPolicyService`; emergency
bypass, unrestricted-door semantics, and bitmask permissions are preserved. The
old `AirlockAccessPolicy` remains a compatibility facade after generated
open/close procedures moved to canonical services.

No block or item registry aliases remain in `net.mcreator.ssc.init`.
`Ssc14ModBlocks` and `Ssc14ModItems` were removed after all consumers moved to
`Ssc14Blocks` and `Ssc14Items`; both migrations preserve existing registry IDs
and do not add duplicate registrations.

The generated entrypoint `Ssc14Mod` is now a thin NeoForge entrypoint and
compatibility facade; registration composition belongs to canonical
`net.ssc14.integration.Ssc14Bootstrap`, which directly wires the canonical
registries and integration services. The entrypoint registers canonical
`Ssc14MenuTypes` and `Ssc14CreativeTabs`
directly; gravity holders, the machine-cable item, and blocks are also read from
canonical registries. `Ssc14ModMenus` remains only as a compatibility
alias/initialization for legacy callers; `Ssc14ModBlocks` and `Ssc14ModItems`
were removed after verifying that no production consumers remained. Menu factory
bindings also belong to `Ssc14MenuTypes`, and `Ssc14ModTabs` was removed after
verifying that no production consumers remained.

Wire/lamp state synchronization now lives in `AirlockLampStateService`; all
persisted cable flags, power restoration, and bolt-cut side effects are
preserved. The old `CPlogLampPrProcedure` remains a compatibility facade.

Door-state initialization, upper-plug handling, and randomized cable assignment
now live in `AirlockStateInitializer`; the old `BaseAirlockD1PutProcedure`
remains a facade for legacy callers.

The door lifecycle state machine (open/close, auto-close, signal requests, and
manual open) now belongs to `AirlockLifecycleService`; the old
`BaseAirlockOpenCloseProcedure` remains a facade for generated callers.

The communication-server periodic radio-frequency tick now runs through
`CommunicationServerLifecycleService.activate`; the legacy `Adapter.tick` remains
only as a compatibility hook for external integrations. Its menu/use methods still
own the GUI and network contract and cannot be removed until that behavior is migrated.

The portable-menu boundary no longer calls a generated inventory procedure: egg
generation and PDA state now use canonical services, while the bridge remains for the
legacy menu and network binding.

Target `net.ssc14` packages do not import `net.mcreator.ssc.procedures` or the
generated `Ssc14ModVariables`; the facade was removed after compatibility
callers moved to `Ssc14WorldVariables`. Saved-data/runtime state now has one
canonical owner, while remaining generated calls are isolated to bootstrap code
and explicit legacy adapters.

The current dedicated NeoForge GameTest run passed all 217 required scenarios, and
the server saved all three dimensions successfully during shutdown.

После удаления отладочной station-code процедуры и мёртвого gravity-plug lifecycle
адаптера и network-button моста в каноническом дереве остаётся 19 интерфейсных
migration boundaries.

Shutters animation, linkage updates, and debug-activator interaction now run through
canonical services; the old controller remains only as a compatibility wrapper.
Число является текущим инвентарным показателем, а не разрешением удалять мосты
без переноса их контрактов.

After removing the debug station-code procedure and the dead gravity-plug lifecycle
adapter and network-button bridge, 19 interface-based migration boundaries remain
in the canonical tree.
This is an inventory metric, not permission to remove bridges without migrating
their contracts.

Shutter animation, linkage updates, and debug-activator interaction now run through
canonical services; the old controller remains only as a compatibility wrapper.

- Большие сгенерированные процедуры доступа и инвентарей ещё требуют отдельных
  поведенческих тестов. Новые операции ID-карт проходят через
  `IdJobAssignmentService`, а legacy `ID_Code_CHECKProcedure` остаётся переходным
  menu-tick до фиксации его полного поведенческого контракта. Отчёт сложности не
  заменяет проверку их логики.
- Обработка всех старых Curios/инвентарных API ещё не завершена: компилятор
  сообщает об устаревших методах.
- `AlcoholSystem.giveAlcohol` остаётся заглушкой; анимация питья и момент расхода
  жидкости требуют отдельной проверки в клиенте.
- Автоматические сценарии не покрывают длительную нагрузку, все сочетания модов,
  несколько игроков и полноценное закрытие/повторное открытие сохранённого мира.
- README указывает AGPLv3, а `neoforge.mods.toml` — Academic Free License v3.0.
  Согласование лицензионных метаданных требует решения правообладателя.

## Сложность

Поиск компонент кабелей BFS — O(V + E), память O(V). Перестроение проходит
известные кабели и ограниченную область приборов около каждого кабеля;
изменение одного узла всё ещё может потребовать обхода всех известных сетей.
Каждый серверный тик обрабатывает зарегистрированные устройства без прежнего
сканирования 18513 блоков вокруг каждого игрока.

Реакция и диффузия работают за O(G), память O(G), где G = 10. Выбор готового
пакета регионов — O(B), где B ≤ 128; количество выгруженных секций не увеличивает
его время. Пробуждение чанка — O(R) по числу его ожидающих регионов.
Распределение реагентов методом наибольшего остатка — O(N log N), планирование
реакции — O(N + I + O) по исходным реагентам, ингредиентам и продуктам.
Обработка брони — O(S) по числу слотов, со средним O(1) поиском сопротивления.

## Контракты выделенного домена

`combat.ResistanceRegistry` хранит сопротивления по структурированной паре ключей.
Конечные значения ограничиваются диапазоном [0, 1], `NaN`/бесконечность
отклоняются при регистрации. Повторная регистрация заменяет только то же правило.
Несколько предметов последовательно уменьшают остаточный урон, как до рефакторинга.
`ArmorResistanceHelper` сохраняет публичный API и занимается обходом Curios.
Диагностика выполняется через `Ssc14Mod.LOGGER.debug`, без печати в stdout.

`GasReaction` копирует входные массивы и возвращает копии из accessors.
Фабрика и прямой конструктор проходят одну валидацию. Неправильные размеры
массивов — ошибка вызова; некорректные числа ячейки, недостаток вещества и
переполнение дают `false` без изменения входного состояния.
Температурные границы включительные, охлаждение ограничено 1 K.
Теплоёмкость суммируется в `double`, поэтому возможны небольшие отличия
округления от прежней арифметики `float`; формулы и рецепты сохранены.


## Совмещение бюджетов и жизненного цикла

Топология энергии использует canonical `net.ssc14.content.energy.EnergyTopologyService`
с пошаговым обходом и общим
бюджетом одного мира. queueUpdate и applyPendingUpdates сохраняют совместимость
с жизненным циклом блоков основной ветки. updatePosition теперь может завершить
обход через несколько тиков. Неготовые компоненты не поставляют энергию; лампы
выключаются на первом энергетическом тике. EnergyTickBudget ограничивает вход
и выход одного устройства суммарно по всем сетям. LampTickBatch исключает
двойную оплату общих ламп и повторное чтение их состояния внутри тика.
Старые классы `net.mcreator.ssc.energy.*` оставлены только как deprecated
compatibility-фасады и не участвуют в canonical runtime.

Атмосфера сохраняет уведомления владельца смеси, очередь загрузки чанков,
отложенную обработку блоков и сохранение секций из основной ветки. На шаг
обрабатывается до 8 регионов по 256 ячеек; сохраняются обмен теплом и перенос
следовых концентраций. Полный снимок HUD и совместимые процедуры чтения
анализатора продолжают получать серверные данные.

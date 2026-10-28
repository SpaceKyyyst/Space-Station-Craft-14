# Архитектура проекта

Проект — мод Minecraft 26.1.2 на NeoForge 26.1.2.71, Java 25, Gradle 9.3.0.
Точка входа: `net.mcreator.ssc.Ssc14Mod`. Исходный код находится в
`src/main/java/net/mcreator/ssc`, ресурсы — в `src/main/resources`.

## Текущее устройство

| Подсистема | Основные классы | Ответственность |
| --- | --- | --- |
| Регистрация | `Ssc14Mod`, `init/*` | Идентификаторы, события, типы блоков и предметов |
| Атмосфера | `atmosphere/*`, `AtmosCell`, `AtmosRegion`, `AtmosphereManager`, `AtmosphereSavedData` | Газовые расчёты, очередь регионов, мир и сохранения |
| Энергетика | `energy/*`, `EnergyNetworkManager`, `EnergySimulationServer` | Компоненты графа, бюджеты устройств, топология и распределение энергии |
| Химия | `common/chemistry/*`, `content/item/reagent/*`, `ProcessReactionsMessage`, legacy `ModReagents` adapter | Расчёт количеств, атомарные реакции, контейнерный runtime и серверный перенос |
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
`ReagentContainerAccess`; `ModReagents` остаётся переходным runtime-адаптером
для legacy NBT и реакций до завершения миграции chemistry runtime.
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
ещё сохраняются старые GUI, сетевые меню, формат сохранения или legacy energy API:
communication console, commerce console, сетевой topology, station-code debug,
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

Маршрутизация right-click инструментов шлюза вынесена в
`AirlockToolInteractionHandler`. Он сохраняет порядок проверки access, welding,
manual opening, tile installation и deconstruction; legacy adapters остаются
явными входами до отдельной миграции их контрактов.

Персистентный журнал шлюза реализован в `AirlockEventLogService`; прежний
`AirlockEventLog` сохранён как thin compatibility facade для generated callers.

Обработка физических redstone-сигналов вынесена в `AirlockSignalHandler`; старый
`AirlockSignals` оставлен фасадом, сохраняя ключ `HOLD` и legacy callers.

Server-side AI control шлюза реализован в `AirlockAiControlService`; старый
`AirlockAiControl` сохраняет типы `Authority`/`Action` и делегирует вызов.

Удаление изолированной сваренной двери реализовано в `AirlockDisassemblyService`;
`AirlockDisassembly` оставлен compatibility facade, а canonical tool handler
вызывает сервис напрямую.

Проверка двери и подача сетевого питания вынесены в `AirlockPowerService`;
`AirlockPower` сохранён как фасад для energy topology и legacy callers.

Server-authoritative wire edits теперь принадлежат canonical
`net.ssc14.content.block.airlock.AirlockWireService`; старый класс сохранён как
facade для network/menu callers.

Синхронизация permissions конфигуратора доступа вынесена в
`AirlockConfiguratorService`; старый `AirlockConfigurator` сохраняет API для
menu/integration callers и прежний формат access keys.

Планирование wire-pulse теперь принадлежит `AirlockWirePulseService`: deadline-
ключи, загрузка chunk и level-tick semantics сохранены; старый
`AirlockWirePulses` оставлен compatibility facade.

Welding jobs теперь принадлежат `AirlockWeldingService`; сохранены отмена при
движении/смене инструмента, fuel charge только при завершении и player-tick
семантика. Старый `AirlockWelding` оставлен фасадом.

Manual-opening jobs теперь принадлежат `AirlockManualOpeningService`; сохранены
проверки powered/bolted/welded, дистанции, отмена движения и player-tick
семантика. Старый `AirlockManualOpening` оставлен compatibility facade.

Оценка доступа двери вынесена в `AirlockAccessPolicyService`; сохранены emergency
bypass, unrestricted-door semantics и bitmask permissions. Старый
`AirlockAccessPolicy` оставлен compatibility facade для generated open/close
процедур.

Оставшиеся canonical inputs из `net.mcreator.ssc.init` — registry aliases
(`Ssc14ModItems`, `Ssc14ModBlocks`, `Ssc14ModAttributes`). Их замена допустима
только через ID-preserving registry migration: массовая текстовая замена создаёт
риск несовместимости сохранений и дублирования регистраций.

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

Целевые пакеты `net.ssc14` не импортируют `net.mcreator.ssc.procedures`; generated
runtime вызовы изолированы в bootstrap и явных legacy adapters.

На текущем контрольном прогоне dedicated NeoForge GameTest все 217 обязательных
сценариев прошли успешно; сервер также корректно сохранил все три измерения при
завершении.

The primary runtime paths now use canonical NeoForge services: oxygen production,
gravity, fuel tanks, C4, nuclear warhead detonation, pallet registry and airlock/
closet access checks. Remaining `IntegrationService` classes are explicit migration
boundaries rather than a new gameplay architecture. They are retained where legacy
GUIs, menu networking, save formats or the legacy energy API are still involved:
communication and commerce consoles, cable topology, station-code debug, closets,
portable menus, and several construction/firearm interactions. Each bridge
must be removed only after its behavior and save compatibility are migrated and
covered by regression tests.

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

Airlock tool right-click routing now lives in `AirlockToolInteractionHandler`.
It preserves the access, welding, manual-opening, tile-installation, and
deconstruction check order; legacy adapters remain explicit inputs until their
contracts are migrated separately.

Persistent airlock logging is implemented by `AirlockEventLogService`; the old
`AirlockEventLog` remains as a thin compatibility facade for generated callers.

Physical redstone signal handling now lives in `AirlockSignalHandler`; the old
`AirlockSignals` remains a facade preserving the `HOLD` key and legacy callers.

Server-side airlock AI control is implemented by `AirlockAiControlService`; the
old `AirlockAiControl` preserves the `Authority`/`Action` types and delegates.

Isolated welded-door removal is implemented by `AirlockDisassemblyService`;
`AirlockDisassembly` remains a compatibility facade, while the canonical tool
handler calls the service directly.

Door identification and network-power supply now live in `AirlockPowerService`;
`AirlockPower` remains a facade for energy topology and legacy callers.

Server-authoritative wire edits now belong to canonical
`net.ssc14.content.block.airlock.AirlockWireService`; the old class remains a
facade for network/menu callers.

Access-configurator permission synchronization now lives in
`AirlockConfiguratorService`; the old `AirlockConfigurator` preserves the API
for menu/integration callers and the existing access-key format.

Wire-pulse scheduling now belongs to `AirlockWirePulseService`; deadline keys,
chunk loading, and level-tick semantics are preserved, while the old
`AirlockWirePulses` remains a compatibility facade.

Welding jobs now belong to `AirlockWeldingService`; cancellation on movement or
tool changes, completion-only fuel charging, and player-tick semantics are
preserved. The old `AirlockWelding` remains a facade.

Manual-opening jobs now belong to `AirlockManualOpeningService`; powered,
bolted, welded, distance, movement-cancellation, and player-tick semantics are
preserved. The old `AirlockManualOpening` remains a compatibility facade.

Door access evaluation now lives in `AirlockAccessPolicyService`; emergency
bypass, unrestricted-door semantics, and bitmask permissions are preserved. The
old `AirlockAccessPolicy` remains a compatibility facade for generated
open/close procedures.

The remaining canonical inputs from `net.mcreator.ssc.init` are registry aliases
(`Ssc14ModItems`, `Ssc14ModBlocks`, `Ssc14ModAttributes`). They must be replaced
only through an ID-preserving registry migration; broad textual replacement
risks save incompatibility and duplicate registrations.

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

Target `net.ssc14` packages do not import `net.mcreator.ssc.procedures`; generated
runtime calls are isolated to bootstrap code and explicit legacy adapters.

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

Топология энергии использует EnergyTopologyService с пошаговым обходом и общим
бюджетом одного мира. queueUpdate и applyPendingUpdates сохраняют совместимость
с жизненным циклом блоков основной ветки. updatePosition теперь может завершить
обход через несколько тиков. Неготовые компоненты не поставляют энергию; лампы
выключаются на первом энергетическом тике. EnergyTickBudget ограничивает вход
и выход одного устройства суммарно по всем сетям. LampTickBatch исключает
двойную оплату общих ламп и повторное чтение их состояния внутри тика.

Атмосфера сохраняет уведомления владельца смеси, очередь загрузки чанков,
отложенную обработку блоков и сохранение секций из основной ветки. На шаг
обрабатывается до 8 регионов по 256 ячеек; сохраняются обмен теплом и перенос
следовых концентраций. Полный снимок HUD и совместимые процедуры чтения
анализатора продолжают получать серверные данные.

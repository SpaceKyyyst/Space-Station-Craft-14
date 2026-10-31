# Remaining generated runtime boundaries / Оставшиеся generated boundaries

> **Актуальный снимок / Current snapshot (2026-10-29):** в каталоге
> `src/main/java/net/mcreator/ssc/procedures` больше не содержит активных процедур.
> Microwave, MK58 breech/fire, spanner, construction и RSU перенесены в canonical
> services. Удалённые procedural wrappers больше не являются частью runtime;
> несколько thin compatibility facades сохранены для legacy/GameTest контрактов.
> Тестовая версия проекта — `0.1.28-beta`.

> **Current snapshot (2026-10-29):** no active procedures remain in
> `src/main/java/net/mcreator/ssc/procedures`.
> Microwave, MK58 breech/fire, spanner, construction, and RSU now live in canonical
> services. Deleted procedural wrappers are no longer runtime sources; a small set
> of thin compatibility facades remains for legacy/GameTest contracts. The project
> test version is `0.1.28-beta`.

## Migration checkpoint / Контрольная точка миграции

The canonical source tree now has no imports from `net.mcreator.ssc`. Legacy
items, blocks, entities, and registries remain only as compatibility facades or
integration bridges so existing IDs and external contracts continue to work.

В canonical-дереве больше нет импортов `net.mcreator.ssc`. Legacy-предметы,
блоки, сущности и регистраторы сохранены только как compatibility facades или
integration bridges, чтобы не ломать существующие ID и внешние контракты.

This checkpoint is intentionally documentation-only and does not change runtime
registration or compatibility behavior.

Эта контрольная точка изменяет только документацию и не меняет runtime-регистрацию
или поведение compatibility-слоя.

## RU — исторические границы и оставшиеся legacy-адаптеры

Списки ниже сохранены как исторический журнал миграции. Они не означают, что
процедуры физически остаются в каталоге `procedures`: каталог пуст. Оставшиеся
границы находятся в обычных legacy-классах, регистраторах и интеграционных
адаптерах; их удаление требует отдельного аудита клиентских событий и сохранений.

Исторически перенесённые event subscribers и процедурные фасады перечислены ниже;
их текущие владельцы находятся в canonical-слоях или legacy integration boundary:

### Текущие runtime-границы / Current runtime boundaries

Эти классы ещё не удаляются автоматически: каждый из них либо реализует
`ReagentContainerRuntime`, либо подключает внешний event/API контракт. Их замена
требует отдельной миграции владельца, а не переименования фасада.

These classes are intentionally retained: each implements `ReagentContainerRuntime`
or connects an external event/API contract. Replacing them requires migrating the
owner, not merely renaming the facade.

- `LegacyReagentContainerRuntime` — storage/color/drink bridge for chemistry;
- `LegacyCuriosProcedureAdapter` — Curios capability bridge;
- `LegacyFirearmInteractionAdapter` — firearm item hook bridge;
- `LegacyConstructionItemInteractionAdapter` — construction item hook bridge;
- `LegacyCommunicationConsoleAdapter` — communication-console event bridge.

- `AirlockToolInteractionHandler` — canonical right-click маршрутизатор
  инструментов шлюза; legacy access/deconstruction adapters остаются явными
  compatibility inputs;
- `CPlogLampPrProcedure` — thin compatibility facade; wire/lamp state ownership
  moved to `AirlockLampStateService`;
- `BaseAirlockD1PutProcedure` — thin compatibility facade; initialization and
  state synchronization moved to `AirlockStateInitializer`;
- `BaseAirlockOpenCloseProcedure` — thin compatibility facade; door lifecycle
  state machine moved to `AirlockLifecycleService`;
- `UniversalDeconstructionProcedureProcedure` — thin compatibility facade;
  deconstruction ownership moved to `AirlockDeconstructionService`, while the
  construction registry remains an explicit legacy input;
- client GUI key handling перенесён в `InteractionSlotKeyHandler`; generated
  `Interaction_PRProcedure` удалён, а key mapping оставлен compatibility alias;
- `ReagentTransferHandler` перенесён в canonical client/chemistry слой; packet
  `ProcessReactionsMessage` и действие `hotbar_transfer` сохранены;
- decal и portable-menu interaction adapters перенесены в canonical
  `content/item` слои; их menu contracts и server-authority checks сохранены;
- network configurator, shutters, commerce-console и ID-console adapters
  перенесены в соответствующие canonical `content` слои без изменения menu,
  position-buffer и server-authority контрактов;
- `HumanModelRenderHandler` — canonical client render hooks; it still consumes
  legacy render-state/model registries until those registries receive canonical
  owners;
- generated `Ssc14Mod` now only provides the NeoForge entrypoint and preserved
  static compatibility helpers; registration composition lives in canonical
  `net.ssc14.integration.Ssc14Bootstrap`;
- Renderer implementations now live in `net.ssc14.client.renderer`; the
  `TestSunEntity` owner and renderer are canonical as well.
- `LiquidMetabolismTICProcedure`, `SSC14CriticalTickProcedure` и
  `TEMPORARYGRAVITYPLUGplayerProcedure` удалены после canonical-переноса;
- остальные subscribers должны проверяться по event annotation, а не по числу прямых вызовов.

Для удаления каждого оставшегося boundary требуется:

1. перенести event registration в canonical client/common слой;
2. сохранить packet, menu и server-authority контракт;
3. добавить regression/GameTest для серверной части и отдельную client-проверку;
4. только затем удалить generated source и обновить архитектурную документацию.

Прямое удаление subscriber-класса без проверки annotation-регистрации считается
регрессией, даже если `rg` не находит вызовов класса.

## EN — historical boundaries and remaining legacy adapters

The lists below are retained as a migration history. They do not mean that
procedures still exist in the `procedures` directory: that directory is empty.
Remaining boundaries are ordinary legacy classes, registries, and integration
adapters; removing them requires a separate audit of client events and saves.

The historically migrated event subscribers and procedural facades are listed
below; their current owners are in canonical layers or the legacy integration
boundary:

- `AirlockToolInteractionHandler` — canonical right-click routing for airlock
  tools; legacy access/deconstruction adapters remain explicit compatibility
  inputs;
- `CPlogLampPrProcedure` — thin compatibility facade; wire/lamp state ownership
  moved to `AirlockLampStateService`;
- `BaseAirlockD1PutProcedure` — thin compatibility facade; initialization and
  state synchronization moved to `AirlockStateInitializer`;
- `BaseAirlockOpenCloseProcedure` — thin compatibility facade; door lifecycle
  state machine moved to `AirlockLifecycleService`;
- `UniversalDeconstructionProcedureProcedure` — thin compatibility facade;
  deconstruction ownership moved to `AirlockDeconstructionService`, while the
  construction registry remains an explicit legacy input;
- client GUI key handling was moved to `InteractionSlotKeyHandler`; generated
  `Interaction_PRProcedure` was removed and the key mapping remains a compatibility alias;
- `ReagentTransferHandler` was moved to the canonical client/chemistry layer;
  `ProcessReactionsMessage` and the `hotbar_transfer` action remain unchanged;
- decal and portable-menu interaction adapters were moved to canonical
  `content/item` layers; their menu contracts and server-authority checks remain intact;
- network configurator, shutters, commerce-console, and ID-console adapters
  were moved to their canonical `content` layers without changing menu,
  position-buffer, or server-authority contracts;
- construction-item interaction adapter remains only as an event-wiring
  boundary; steel, plasteel, and cable behavior is owned by canonical
  `ConstructionAssemblyService` and `SheathingCableService`;
- firearm interaction adapter was moved to `net.ssc14.content.item.weapon`;
  canonical firearm state routing and MK-58/Pistol procedure contracts remain
  unchanged;
- both lamp block variants call canonical `LampInteractionService` directly;
  no block package class imports generated procedures;
- `Ssc14Items.REGISTRY` is the sole active item registry; `Ssc14ModItems` was
  removed after all compatibility and GameTest callers moved to `Ssc14Items`;
- all generated inventory capability implementations were moved to canonical
  item packages; `Ssc14ItemCapabilities` now owns their NeoForge registration,
  while the generated item registry facade is no longer part of the runtime;
- client screen overlays now route health-analyzer and world-check visibility
  through integration adapters; `net.mcreator.ssc.client.screens` has no direct
  generated-procedure imports;
- the generated `net.mcreator.ssc.procedures` package is no longer present;
  procedure ownership is canonical or explicitly isolated in integration
  adapters;
- Curios item capability registration is owned by
  `Ssc14CuriosCompatibility`; `Ssc14ModCuriosCompat` is no longer part of the
  generated init layer;
- `Ssc14ModBlockEntities` was removed after all compatibility callers moved to
  the canonical block-entity registries;
- `Ssc14ModBlocks` was removed after all compatibility and GameTest callers moved
  to the canonical `Ssc14Blocks` registry;
- client block-entity renderer bootstrap uses canonical block-entity registries;
- `LocalChatEventHandler` remains in `net.mcreator.ssc.integration`; message
  routing, radio prefixes, popup formatting, and server-side audience checks
  remain unchanged pending extraction of a pure chat/radio domain service;
- `LegacyReagentContainerRuntime` now owns the integration bridge in
  `net.mcreator.ssc.integration`; the redundant `ReagentColorHelper` facade
  was removed while color mixing remains owned by canonical `ReagentColorMixer`;
- canonical `ReagentContainerStorage` now owns codec-backed container
  reads/additions/removals; the legacy bridge retains only drinking and
  catalog/effect integration;
- the unused generated `ReagentFluidHandler` nested class was removed after
  repository-wide reference verification; fluid storage remains covered by
  the canonical container contract;
- `LegacyReactionRuntime` is the compatibility entry point for server-side
  reaction execution; generated procedures and network handlers no longer call
  the removed `ModReagents` alias directly;
- `HumanModelRenderHandler` — canonical client render hooks; it still consumes
  legacy render-state/model registries until those registries receive canonical
  owners;
- Renderer implementations now live in `net.ssc14.client.renderer`; the
  `TestSunEntity` owner and renderer are canonical as well.
- `LiquidMetabolismTICProcedure`, `SSC14CriticalTickProcedure`, and
  `TEMPORARYGRAVITYPLUGplayerProcedure` were removed after canonical migration;
- every remaining subscriber must be audited by event annotation, not by direct-call count.

Each remaining boundary requires:

1. moving event registration into the canonical client/common layer;
2. preserving packet, menu, and server-authority contracts;
3. adding server regression/GameTests plus a separate client check;
4. deleting generated source only afterward and updating architecture documentation.

Deleting a subscriber solely because `rg` finds no direct calls is a regression risk.

## RU — Construction adapter boundary

`LegacyConstructionItemInteractionAdapter` остаётся тонкой legacy-границей для
item-event wiring. Игровая логика уже принадлежит `ConstructionAssemblyService`
и `SheathingCableService`; старые steel/plasteel/cable procedures не являются
runtime-владельцами этой механики.

## EN — Construction adapter boundary

`LegacyConstructionItemInteractionAdapter` remains a thin legacy boundary for
item-event wiring. The gameplay logic is owned by `ConstructionAssemblyService`
and `SheathingCableService`; the old steel/plasteel/cable procedures are not
runtime owners of this behavior.

## RU — Текущий остаток generated-слоя

- `Ssc14MenuTypes` является владельцем регистрации всех меню, factory bindings и
  типизированных `MenuType<T>`; `Ssc14ModMenus` удалён после переноса wiring.
- `Ssc14ModScreens` удалён после переноса screen registration и menu-state
  client wiring в `Ssc14Screens`.
- `Ssc14WorldVariables` является единственным владельцем saved-data runtime;
  `Ssc14ModVariables` удалён после перевода compatibility callers на canonical
  state service и сохранения `worldvars`/`mapvars` контрактов.
- generated `Ssc14LegacyEntityBehavior` удалён; hook bridge перенесён в
  canonical `Ssc14EntityBehaviorHooks`, который не изменяет entity behavior
  contracts и использует canonical block/item/attribute owners.
- Пять generated network registration facade-классов удалены после проверки, что
  payload registration и handlers принадлежат canonical chemistry/menu слоям;
  event annotations и payload IDs не изменялись.
- Generated command, reagent-fluid-model, Handy compatibility и cargo-order
  event facades удалены после подтверждения canonical owners; command routes,
  client event listeners и cargo menu behavior не менялись.
- Три wrapper-класса с явным `forRemoval=true` удалены после перевода entity и
  portable-menu registry ownership в canonical classes; registry IDs и renderer
  contracts сохранены.
- `ModReagents` alias удалён после перевода audit GameTests и unit tests на
  `ReagentService`; `ssc_14_reagents` NBT format и reagent behavior сохранены.
- `Ssc14Mod` оставлен только как тонкий NeoForge entrypoint и compatibility facade;
  регистрационная композиция перенесена в canonical `Ssc14Bootstrap`.
- SSC14 command registrations, cargo order menu events, reagent fluid models,
  and the Handy mouse compatibility hook now live in canonical command,
  commerce, and client compatibility packages; their generated classes are
  facades only.

## EN — Current generated-layer remainder

- `Ssc14MenuTypes` owns registration, factory bindings, and typed `MenuType<T>`
  values for every menu; `Ssc14ModMenus` was removed after its wiring moved to
  the canonical owner.
- `Ssc14ModScreens` was removed after screen registration and menu-state client
  wiring moved to `Ssc14Screens`.
- `Ssc14WorldVariables` is the sole saved-data runtime owner;
  `Ssc14ModVariables` was removed after compatibility callers moved to the
  canonical state service and the `worldvars`/`mapvars` contracts were retained.
- generated `Ssc14LegacyEntityBehavior` was removed; the hook bridge now lives
  in canonical `Ssc14EntityBehaviorHooks`, preserving entity behavior contracts
  and using canonical block/item/attribute owners.
- Five generated network registration facade classes were removed after verifying
  that payload registration and handlers are owned by canonical chemistry/menu
  layers; event annotations and payload IDs were not changed.
- Generated command, reagent-fluid-model, Handy compatibility, and cargo-order
  event facades were removed after confirming canonical owners; command routes,
  client event listeners, and cargo menu behavior were not changed.
- Three wrappers explicitly marked `forRemoval=true` were removed after entity
  and portable-menu registry ownership moved to canonical classes; registry IDs
  and renderer contracts were preserved.
- The `ModReagents` alias was removed after audit GameTests and unit tests moved
  to `ReagentService`; the `ssc_14_reagents` NBT format and reagent behavior were preserved.
- `Ssc14Mod` remains only as a thin NeoForge entrypoint and compatibility facade;
  registration composition moved to canonical `Ssc14Bootstrap`.
- SSC14 command registrations, cargo order menu events, reagent fluid models,
  and the Handy mouse compatibility hook now live in canonical command,
  commerce, and client compatibility packages; their generated classes are
  facades only.

<!-- Governance checkpoint synchronized after label assignment. -->

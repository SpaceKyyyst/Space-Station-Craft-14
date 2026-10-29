# Remaining generated runtime boundaries / Оставшиеся generated boundaries

> **Актуальный снимок / Current snapshot (2026-10-29):** в каталоге
> `src/main/java/net/mcreator/ssc/procedures` больше не содержит активных процедур.
> Microwave, MK58 breech/fire, spanner, construction и RSU перенесены в canonical
> services. Все удалённые фасады и procedural wrappers из старых списков ниже больше
> не являются частью runtime. Тестовая версия проекта — `0.1.28-beta`.

> **Current snapshot (2026-10-29):** no active procedures remain in
> `src/main/java/net/mcreator/ssc/procedures`.
> Microwave, MK58 breech/fire, spanner, construction, and RSU now live in canonical
> services. Deleted facades and wrappers listed historically below are no longer
> runtime sources. The project test version is `0.1.28-beta`.

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
- `AirlockSignals` — thin compatibility facade; redstone routing ownership moved
  to `AirlockSignalHandler`;
- `AirlockAiControl` — thin compatibility facade; server-side AI control ownership
  moved to `AirlockAiControlService`;
- `AirlockWireService` — thin compatibility facade; server-authoritative wire
  edits moved to the canonical airlock package;
- `AirlockConfigurator` — thin compatibility facade; access-card permission
  synchronization moved to `AirlockConfiguratorService`;
- `AirlockWirePulses` — thin compatibility facade; pulse scheduling ownership
  moved to `AirlockWirePulseService`;
- `AirlockAccessPolicy` — thin compatibility facade; permission evaluation moved
  to `AirlockAccessPolicyService`;
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
- `AirlockSignals` — thin compatibility facade; redstone routing ownership moved
  to `AirlockSignalHandler`;
- `AirlockAiControl` — thin compatibility facade; server-side AI control ownership
  moved to `AirlockAiControlService`;
- `AirlockWireService` — thin compatibility facade; server-authoritative wire
  edits moved to the canonical airlock package;
- `AirlockConfigurator` — thin compatibility facade; access-card permission
  synchronization moved to `AirlockConfiguratorService`;
- `AirlockWirePulses` — thin compatibility facade; pulse scheduling ownership
  moved to `AirlockWirePulseService`;
- `AirlockAccessPolicy` — thin compatibility facade; permission evaluation moved
  to `AirlockAccessPolicyService`;
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
- `Ssc14Items.REGISTRY` is the sole active item registry; `Ssc14ModItems`
  remains a field-compatible alias only and its empty legacy registry is not
  registered;
- client screen overlays now route health-analyzer and world-check visibility
  through integration adapters; `net.mcreator.ssc.client.screens` has no direct
  generated-procedure imports;
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
  `ModReagents.processReactions` directly;
- `HumanModelRenderHandler` — canonical client render hooks; it still consumes
  legacy render-state/model registries until those registries receive canonical
  owners;
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

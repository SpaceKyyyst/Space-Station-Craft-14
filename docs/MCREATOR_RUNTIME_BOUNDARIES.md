# Remaining generated runtime boundaries / Оставшиеся generated boundaries

> **Актуальный снимок / Current snapshot (2026-10-29):** в каталоге
> `src/main/java/net/mcreator/ssc/procedures` осталось 5 активных процедур:
> `MicrwGUIbutEProcedure`, `MK58_Breech_Open_Procedure`, `MK58_Fire_Procedure`,
> `RSU_useOnBlock_Procedure` и `SpannerEntUseProcedure`. `PlaSteelUseProcedure` и
> `SteeluseProcedure` перенесены в `ConstructionAssemblyService`. Все удалённые фасады
> и procedural wrappers из старых списков
> ниже больше не являются частью runtime. Версия проекта остаётся `0.1.27` до
> завершения этих переносов.

> **Current snapshot (2026-10-29):** 5 active procedures remain in
> `src/main/java/net/mcreator/ssc/procedures`:
> `MicrwGUIbutEProcedure`, `MK58_Breech_Open_Procedure`, `MK58_Fire_Procedure`,
> `RSU_useOnBlock_Procedure`, and `SpannerEntUseProcedure`. `PlaSteelUseProcedure`
> and `SteeluseProcedure` now live in `ConstructionAssemblyService`. Deleted facades
> and wrappers listed historically below are
> no longer runtime sources. The project version remains `0.1.27` until these
> migrations are complete.

## RU

После текущих переносов в `src/main/java/net/mcreator/ssc/procedures` остаются только
event subscribers и процедурные фасады, которые ещё имеют runtime-владельца:

- `AirlockToolInteractionHandler` — canonical right-click маршрутизатор
  инструментов шлюза; legacy access/deconstruction adapters остаются явными
  compatibility inputs;
- `AirlockEventLog` — thin compatibility facade; persisted log ownership moved to
  `AirlockEventLogService`;
- `AirlockSignals` — thin compatibility facade; redstone routing ownership moved
  to `AirlockSignalHandler`;
- `AirlockAiControl` — thin compatibility facade; server-side AI control ownership
  moved to `AirlockAiControlService`;
- `AirlockDisassembly` — thin compatibility facade; isolated-door removal moved
  to `AirlockDisassemblyService`;
- `AirlockPower` — thin compatibility facade; power supply ownership moved to
  `AirlockPowerService`;
- `AirlockWireService` — thin compatibility facade; server-authoritative wire
  edits moved to the canonical airlock package;
- `AirlockConfigurator` — thin compatibility facade; access-card permission
  synchronization moved to `AirlockConfiguratorService`;
- `AirlockWirePulses` — thin compatibility facade; pulse scheduling ownership
  moved to `AirlockWirePulseService`;
- `AirlockWelding` — thin compatibility facade; cancellable welding jobs moved
  to `AirlockWeldingService`;
- `AirlockManualOpening` — thin compatibility facade; manual-opening jobs moved
  to `AirlockManualOpeningService`;
- `AirlockAccessPolicy` — thin compatibility facade; permission evaluation moved
  to `AirlockAccessPolicyService`;
- `CPlogLampPrProcedure` — thin compatibility facade; wire/lamp state ownership
  moved to `AirlockLampStateService`;
- `BaseAirlockD1PutProcedure` — thin compatibility facade; initialization and
  state synchronization moved to `AirlockStateInitializer`;
- `BaseAirlockOpenCloseProcedure` — thin compatibility facade; door lifecycle
  state machine moved to `AirlockLifecycleService`;
- `AirlockMaintenance` — thin compatibility facade; construction-job ownership
  moved to `AirlockMaintenanceService`;
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

## EN

After the current migrations, `src/main/java/net/mcreator/ssc/procedures` contains
only event subscribers and procedural facades that still own runtime behavior:

- `AirlockToolInteractionHandler` — canonical right-click routing for airlock
  tools; legacy access/deconstruction adapters remain explicit compatibility
  inputs;
- `AirlockEventLog` — thin compatibility facade; persisted log ownership moved to
  `AirlockEventLogService`;
- `AirlockSignals` — thin compatibility facade; redstone routing ownership moved
  to `AirlockSignalHandler`;
- `AirlockAiControl` — thin compatibility facade; server-side AI control ownership
  moved to `AirlockAiControlService`;
- `AirlockDisassembly` — thin compatibility facade; isolated-door removal moved
  to `AirlockDisassemblyService`;
- `AirlockPower` — thin compatibility facade; power supply ownership moved to
  `AirlockPowerService`;
- `AirlockWireService` — thin compatibility facade; server-authoritative wire
  edits moved to the canonical airlock package;
- `AirlockConfigurator` — thin compatibility facade; access-card permission
  synchronization moved to `AirlockConfiguratorService`;
- `AirlockWirePulses` — thin compatibility facade; pulse scheduling ownership
  moved to `AirlockWirePulseService`;
- `AirlockWelding` — thin compatibility facade; cancellable welding jobs moved
  to `AirlockWeldingService`;
- `AirlockManualOpening` — thin compatibility facade; manual-opening jobs moved
  to `AirlockManualOpeningService`;
- `AirlockAccessPolicy` — thin compatibility facade; permission evaluation moved
  to `AirlockAccessPolicyService`;
- `CPlogLampPrProcedure` — thin compatibility facade; wire/lamp state ownership
  moved to `AirlockLampStateService`;
- `BaseAirlockD1PutProcedure` — thin compatibility facade; initialization and
  state synchronization moved to `AirlockStateInitializer`;
- `BaseAirlockOpenCloseProcedure` — thin compatibility facade; door lifecycle
  state machine moved to `AirlockLifecycleService`;
- `AirlockMaintenance` — thin compatibility facade; construction-job ownership
  moved to `AirlockMaintenanceService`;
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
- construction-item interaction adapter remains in the legacy integration
  boundary because steel, plasteel, and cable procedures are still direct
  generated-runtime inputs;
- firearm interaction adapter was moved to `net.mcreator.ssc.integration`;
  canonical firearm state routing and MK-58/Pistol procedure contracts remain
  unchanged;
- `LegacyLampInteractionAdapter` now owns the remaining lamp replacement
  procedure call for both lamp block variants; no block package class imports
  generated procedures directly;
- `Ssc14Items.REGISTRY` is the sole active item registry; `Ssc14ModItems`
  remains a field-compatible alias only and its empty legacy registry is not
  registered;
- client screen overlays now route health-analyzer and world-check visibility
  through integration adapters; `net.mcreator.ssc.client.screens` has no direct
  generated-procedure imports;
- `LocalChatEventHandler` was moved to `net.mcreator.ssc.integration`; message
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

## RU — Construction adapter boundary`LegacyConstructionItemInteractionAdapter` пока остаётся в legacy-границе: он напрямую вызывает steel/plasteel/cable procedures. Перенос возможен только после выделения канонической логики этих процедур.

`LegacyConstructionItemInteractionAdapter` теперь находится в
`net.ssc14.integration.construction`. Перенос изменяет только расположение
границы: steel/plasteel/cable procedures и их аргументы сохранены для
поэтапной миграции без изменения поведения предметов.

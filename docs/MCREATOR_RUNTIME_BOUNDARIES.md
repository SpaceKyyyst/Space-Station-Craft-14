# Remaining generated runtime boundaries / Оставшиеся generated boundaries

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
- client GUI key handling перенесён в `InteractionSlotKeyHandler`; generated
  `Interaction_PRProcedure` удалён, а key mapping оставлен compatibility alias;
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
- client GUI key handling was moved to `InteractionSlotKeyHandler`; generated
  `Interaction_PRProcedure` was removed and the key mapping remains a compatibility alias;
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

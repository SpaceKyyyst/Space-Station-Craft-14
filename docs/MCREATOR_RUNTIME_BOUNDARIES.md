# Remaining generated runtime boundaries / Оставшиеся generated boundaries

## RU

После текущих переносов в `src/main/java/net/mcreator/ssc/procedures` остаются только
event subscribers и процедурные фасады, которые ещё имеют runtime-владельца:

- `ToolsBlockClickProcedure` — общий right-click маршрутизатор инструментов шлюза;
- `Interaction_PRProcedure` — client GUI key handling и сетевой слот-командный контракт;
- `HumanModelRenderProcedure` — client render hooks;
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

- `ToolsBlockClickProcedure` — shared right-click routing for airlock tools;
- `Interaction_PRProcedure` — client GUI key handling and the slot-command network contract;
- `HumanModelRenderProcedure` — client render hooks;
- `LiquidMetabolismTICProcedure`, `SSC14CriticalTickProcedure`, and
  `TEMPORARYGRAVITYPLUGplayerProcedure` were removed after canonical migration;
- every remaining subscriber must be audited by event annotation, not by direct-call count.

Each remaining boundary requires:

1. moving event registration into the canonical client/common layer;
2. preserving packet, menu, and server-authority contracts;
3. adding server regression/GameTests plus a separate client check;
4. deleting generated source only afterward and updating architecture documentation.

Deleting a subscriber solely because `rg` finds no direct calls is a regression risk.

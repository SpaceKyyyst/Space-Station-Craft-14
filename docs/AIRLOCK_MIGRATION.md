# Airlock migration boundary / Граница миграции шлюзов

## RU

Канонический блок шлюза находится в `net.ssc14.content.block.airlock`.
`BaseAirlockD1Block` и его block entity не импортируют MCreator-процедуры напрямую: события проходят через `AirlockDoorIntegrationService`.

### Текущие обязанности

- `AirlockDoorIntegrationService` — стабильный lifecycle-контракт: placement, interaction, scheduled tick, neighbor updates и menu creation.
- `LegacyAirlockDoorIntegrationAdapter` — временный composition-root bridge к существующей игровой логике.
- `AirlockFloor` — каноническая логика нижней части двери и порога.
- `BaseAirlockOpenCloseProcedure` — legacy state machine анимации, interlock-проверок, автозакрытия и сигналов.
- `BaseAirlockD1PutProcedure` — legacy initialization, cable seed и восстановление верхней части двери.

### Почему bridge пока сохраняется

Обе процедуры вызываются не только из lifecycle блока, но и из `AirlockSignals`, `AirlockWirePulses`, `AirlockWelding`, `AirlockAiControl` и construction flow.
Механическое удаление процедур поэтому нарушит сохранённые NBT-контракты, scheduled ticks или обход ID для аппаратных сигналов.

### Следующий перенос

1. Выделить чистый canonical state model для полей `_ssc14_airlock_action`, `_ssc14_airlock_step` и связанных таймеров.
2. Перенести initialization/placement contract отдельно от animation state machine.
3. Перенести signal entry points на canonical service и сохранить legacy wrappers для совместимости.
4. Удалять generated procedures только после серверных GameTest на restart, chunk unload, power restore и redstone/wire signals.

Каждый шаг должен оставлять `LegacyAirlockDoorIntegrationAdapter` компилируемым и сохранять registry IDs и NBT keys.

## EN

The canonical airlock block lives under `net.ssc14.content.block.airlock`.
`BaseAirlockD1Block` and its block entity do not import MCreator procedures directly; lifecycle events pass through `AirlockDoorIntegrationService`.

### Current responsibilities

- `AirlockDoorIntegrationService` — stable lifecycle contract: placement, interaction, scheduled tick, neighbor updates, and menu creation.
- `LegacyAirlockDoorIntegrationAdapter` — temporary composition-root bridge to the existing gameplay logic.
- `AirlockFloor` — canonical lower-part and threshold behavior.
- `BaseAirlockOpenCloseProcedure` — legacy animation state machine, interlocks, auto-close, and signal handling.
- `BaseAirlockD1PutProcedure` — legacy initialization, cable seeding, and upper-part restoration.

### Why the bridge remains

Both procedures are called outside block lifecycle code by `AirlockSignals`, `AirlockWirePulses`, `AirlockWelding`, `AirlockAiControl`, and construction flow.
Mechanical deletion would risk persisted NBT contracts, scheduled ticks, and hardware-signal ID bypass behavior.

### Next migration

1. Extract a canonical state model for `_ssc14_airlock_action`, `_ssc14_airlock_step`, and related timers.
2. Move initialization/placement separately from the animation state machine.
3. Move signal entry points to the canonical service while retaining compatibility wrappers.
4. Delete generated procedures only after server GameTests cover restart, chunk unload, power restore, and redstone/wire signals.

Every step must keep `LegacyAirlockDoorIntegrationAdapter` compilable and preserve registry IDs and NBT keys.

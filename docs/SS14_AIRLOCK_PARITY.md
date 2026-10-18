# Совместимость механик шлюза с SS14

Источник поведения: `Content.Shared/Doors/Components/AirlockComponent.cs`,
`Content.Shared/Doors/Systems/SharedAirlockSystem.cs` и
`Content.Shared/Access/Components/AccessReaderComponent.cs` оригинальной SS14.

## Переносимые контракты

| Механика SS14 | Слой мода | Состояние |
| --- | --- | --- |
| Powered | `AirlockPower` + persistent data | реализовано |
| Bolts | `AirlockSignals` и открытие двери | реализовано |
| EmergencyAccess | `AirlockSignals` и политика доступа | реализовано |
| AccessReader.Enabled | `AirlockAccessPolicy.access_enabled` | реализовано |
| AccessReader access groups | `PlayerAccessService` + `IdCardRegistry` | реализовано |
| AccessReader deny tags | `AirlockAccessPolicy.access_deny_mask` | реализовано |
| AutoClose | `BaseAirlockOpenCloseProcedure` | реализовано |
| Safety collision | `AirlockCrushing` | реализовано |
| Wires panel | `AirlockWireService` | реализовано |
| Pry / welding | `AirlockManualOpening` + `AirlockWelding` | реализовано |
| Access logging | `AirlockEventLog` | реализовано |
| Visual states | `AirlockDoorRenderer` | частично |
| Width 1–3 multiblock | `AirlockMultiblockLayout` | не подключено |

## Оставшиеся отличия

Многоблочное размещение, единый lifecycle частей структуры и полная клиентская сверка визуальных состояний требуют отдельной реализации. Эти пункты нельзя закрывать только NBT-флагами: корневой BlockEntity должен владеть частями, питанием, коллизией и удалением.

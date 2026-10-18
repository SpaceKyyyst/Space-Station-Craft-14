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

## Оставшиеся отличия

Шлюз оригинальной SS14 имеет одну клетку по ширине. В моде сохранена эта геометрия: два блока над полом и нижняя часть под плиткой. Поддержка произвольной ширины намеренно не добавляется.

По текущему сравнению с `AirlockComponent` в цель добавлены следующие пункты для проверки и переноса:

- отдельные настройки `OpenPanelVisible` и `KeepOpenIfClicked`;
- множитель задержки автозакрытия, изменяемый проводом;
- отдельные множители времени взлома для запитанного и заболтированного шлюза;
- длительность анимации отказа доступа;
- независимые слои аварийного света, незапитанного открытого шлюза и анимации панели;
- точные звуки включения и выключения аварийного доступа;
- сетевое/клиентское обновление состояния без рассинхронизации при автозакрытии.

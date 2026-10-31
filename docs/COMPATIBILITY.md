# Контракты совместимости / Compatibility contracts

Этот документ фиксирует совместимость SSC14 на baseline-коммите этапа миграции.
Он описывает контракты, которые нельзя менять обычным рефакторингом.

This document freezes SSC14 compatibility at the migration baseline commit.
It describes contracts that ordinary refactoring must not change.

The historical source-package compatibility layer has now been removed. This is
an intentional source and binary breaking change: consumers must migrate to
`net.ssc14`. Registry IDs, saved-data keys, payload contracts, and world-save
formats remain covered by the compatibility baseline below.

## RU

### Завершение перехода

Совместимость с историческим Java package удалена намеренно. Это breaking
change для исходных и бинарных consumers: новый код должен использовать
`net.ssc14`. Registry ID, SavedData/NBT-ключи, payload-контракты и форматы миров
сохранены и по-прежнему проверяются baseline-тестами ниже.

### Назначение

Baseline защищает существующие миры, контент и соединение клиента с сервером во
время отказа от MCreator. Источником истины являются исполняемые тесты и два
manifest-файла, а не списки, скопированные в документацию:

- `src/gameTest/resources/compatibility/registry-ids.txt`;
- `src/gameTest/resources/compatibility/network-contracts.txt`.

Изменение manifest без migration-кода и проверки старых данных не является
исправлением теста: это изменение публичного контракта.

### Registry ID и namespace

Основной mod/resource namespace — `ssc_14`. Runtime GameTest проверяет точное
совпадение всех ID SSC14 в десяти реестрах:

| Реестр | Количество ID |
| --- | ---: |
| Blocks | 225 |
| Items | 546 |
| Entity types | 12 |
| Block entity types | 106 |
| Menus | 21 |
| Particle types | 32 |
| Sound events | 81 |
| Attributes | 11 |
| Creative mode tabs | 6 |
| Fluids | 472 |
| **Всего** | **1 512** |

Legacy namespace `ssc14` сохраняется для `ssc14:network_data`. Его нельзя
«исправлять» на `ssc_14` без чтения старого ID и согласованной миграции.

### Порядок газов

Ordinal `GasType` записывается как индекс `m_<ordinal>` и поэтому является частью
формата сохранения:

| Ordinal | ID |
| ---: | --- |
| 0 | `oxygen` |
| 1 | `nitrogen` |
| 2 | `carbon_dioxide` |
| 3 | `nitrous_oxide` |
| 4 | `plasma` |
| 5 | `ammonia` |
| 6 | `tritium` |
| 7 | `freon` |
| 8 | `helium` |
| 9 | `water_vapor` |

Переупорядочивание требует versioned migration codec; простая перестановка enum
повредит газовые смеси существующих миров.

### SavedData и сериализация

Зафиксированы следующие ID и ключи:

| Данные | SavedData ID | Схема |
| --- | --- | --- |
| Атмосфера | `ssc_14:atmosphere` | root `regions`; region `x`, `y`, `z`, `cells`; cell `idx`, `temp`, sparse `m_0`…`m_9` |
| Сигнальные соединения | `ssc14:network_data` | root `connections`; entry `sourcePos`, `triggerId`, `targetPos`, `actionId` |
| Переменные мира | `ssc_14:worldvars` | `Station_Budget`, `PalletRegistry` |
| Переменные карты | `ssc_14:mapvars` | `station_code`, `station_name` и восемь `*_Radio_Frequency` ключей |

JUnit-тесты проверяют точные наборы ключей, значения после round-trip, defaults
при отсутствующих полях и текущее поведение неизвестных legacy-полей.
Непосещённые атмосферные регионы сохраняются без потери дополнительных полей.

### Сетевые контракты

Dedicated-server GameTest наблюдает payload только после успешного вызова
регистрации NeoForge, а не по объявленным `TYPE` или очереди намерений. Baseline
содержит 33 контракта:

| Направление и версия | Количество |
| --- | ---: |
| Serverbound, version `ssc_14` | 25 |
| Clientbound, version `ssc_14` | 8 |
| Bidirectional, version `ssc_14` | 0 |
| Clientbound, version `1` | 1 |

Manifest фиксирует для каждого payload его ID, направление и channel version.
Изменение codec полей требует отдельного characterization-теста той подсистемы;
клиентские данные не должны становиться источником авторитетного состояния.

### Проверка и изменение контракта

```powershell
.\gradlew.bat test domainTest --no-daemon --no-build-cache
.\gradlew.bat runGameTestServer --no-daemon --no-build-cache --console=plain
py -3 .github/scripts/check_gametests.py build/test-results/gameTest/TEST-atmosphere.xml
```

Намеренное breaking change допустимо только в отдельном PR, который одновременно:

1. объясняет необходимость изменения на RU и EN;
2. содержит чтение старого формата и migration path;
3. добавляет fixture старого формата и RED→GREEN регрессионный тест;
4. обновляет manifest и этот документ после доказанной миграции;
5. помечен `risk:compatibility`.

### Ограничение world fixture

В репозитории нет разрешённого обезличенного сохранения старого мира и не
зафиксирован диапазон поддерживаемых версий мира. Поэтому baseline использует
синтетические legacy-shaped NBT fixtures и dedicated-server GameTests. Полный
old-world load/save/reopen остаётся обязательным перед удалением MCreator и
требует fixture и решения сопровождающего проекта; пользовательские сохранения
публиковать нельзя.

### Оставшаяся клиентская граница

`HumanModelRenderHandler` использует canonical `Ssc14RenderStateModifiers` и
`Ssc14HumanoidModels`. Эти registry не участвуют в server-side logic,
SavedData, network payloads или registry ID baseline. Их перенос допускается только
одним клиентским PR после проверки render-state registration и dedicated-client
запуска; до этого они считаются явными compatibility inputs.

## EN

### Purpose

The baseline protects existing worlds, content, and client/server connections
during the MCreator removal. Executable tests and two manifest files are the
source of truth, rather than identifier lists copied into documentation:

- `src/gameTest/resources/compatibility/registry-ids.txt`;
- `src/gameTest/resources/compatibility/network-contracts.txt`.

Changing a manifest without migration code and old-data verification is not a
test fix; it changes a public contract.

### Registry IDs and namespaces

The primary mod/resource namespace is `ssc_14`. A runtime GameTest checks exact
equality for all SSC14 IDs in ten registries:

| Registry | ID count |
| --- | ---: |
| Blocks | 225 |
| Items | 546 |
| Entity types | 12 |
| Block entity types | 106 |
| Menus | 21 |
| Particle types | 32 |
| Sound events | 81 |
| Attributes | 11 |
| Creative mode tabs | 6 |
| Fluids | 472 |
| **Total** | **1,512** |

The legacy `ssc14` namespace remains in use by `ssc14:network_data`. It must not
be “corrected” to `ssc_14` without reading the old ID and providing an agreed
migration.

### Gas ordering

The `GasType` ordinal is stored as the `m_<ordinal>` index and is therefore part
of the save format:

| Ordinal | ID |
| ---: | --- |
| 0 | `oxygen` |
| 1 | `nitrogen` |
| 2 | `carbon_dioxide` |
| 3 | `nitrous_oxide` |
| 4 | `plasma` |
| 5 | `ammonia` |
| 6 | `tritium` |
| 7 | `freon` |
| 8 | `helium` |
| 9 | `water_vapor` |

Reordering requires a versioned migration codec; moving enum constants directly
would corrupt gas mixtures in existing worlds.

### SavedData and serialization

The following IDs and keys are frozen:

| Data | SavedData ID | Schema |
| --- | --- | --- |
| Atmosphere | `ssc_14:atmosphere` | root `regions`; region `x`, `y`, `z`, `cells`; cell `idx`, `temp`, sparse `m_0`…`m_9` |
| Signal connections | `ssc14:network_data` | root `connections`; entry `sourcePos`, `triggerId`, `targetPos`, `actionId` |
| World variables | `ssc_14:worldvars` | `Station_Budget`, `PalletRegistry` |
| Map variables | `ssc_14:mapvars` | `station_code`, `station_name`, and eight `*_Radio_Frequency` keys |

JUnit tests check exact key sets, round-trip values, defaults for missing fields,
and the current handling of unknown legacy fields. Unvisited atmosphere regions
retain additional fields without data loss.

### Network contracts

The dedicated-server GameTest observes a payload only after NeoForge accepts its
registration call, rather than reading declared `TYPE` fields or queued intent.
The baseline contains 33 contracts:

| Direction and version | Count |
| --- | ---: |
| Serverbound, version `ssc_14` | 25 |
| Clientbound, version `ssc_14` | 8 |
| Bidirectional, version `ssc_14` | 0 |
| Clientbound, version `1` | 1 |

For every payload, the manifest freezes its ID, direction, and channel version.
Changing codec fields requires a subsystem-specific characterization test;
client data must not become the source of authoritative state.

### Verifying and changing a contract

```powershell
.\gradlew.bat test domainTest --no-daemon --no-build-cache
.\gradlew.bat runGameTestServer --no-daemon --no-build-cache --console=plain
py -3 .github/scripts/check_gametests.py build/test-results/gameTest/TEST-atmosphere.xml
```

An intentional breaking change is allowed only in a separate PR that:

1. explains the need in both RU and EN;
2. reads the old format and provides a migration path;
3. adds an old-format fixture and a RED→GREEN regression test;
4. updates the manifest and this document only after migration is proven;
5. carries the `risk:compatibility` label.

### World fixture limitation

The repository has no approved sanitized old-world save and no defined supported
world-version range. The baseline therefore uses synthetic legacy-shaped NBT
fixtures and dedicated-server GameTests. Full old-world load/save/reopen remains
mandatory before MCreator removal and requires both a fixture and a maintainer
decision; user saves must not be published.

### Remaining client boundary

`HumanModelRenderHandler` uses the canonical `Ssc14RenderStateModifiers` and
`Ssc14HumanoidModels`. These registries do not participate in server-side logic,
SavedData, network payloads, or the registry-ID baseline. They may be moved only in
one client-focused PR after render-state registration and dedicated-client startup
verification; until then they remain explicit compatibility inputs.

### Chemistry migration invariants

The completed `ModReagents` migration must preserve these observable contracts:

`ReagentService` and `ReagentContentsCodec` now own the canonical
`ssc_14_reagents` read/write format; the generated `ModReagents` alias was
removed after all repository callers moved to the canonical service.

| Contract | Required characterization |
| --- | --- |
| Reagent NBT | Preserve reagent IDs, amounts, and unknown-key round trips. |
| Capacity | Reject over-capacity additions without mutating existing contents. |
| Removal | Remove only the requested amount and keep zero/empty entries canonical. |
| Reactions | Preserve input matching order, effect ordering, and server authority. |
| Color | Preserve `ReagentColorMixer` output for empty, single, and mixed contents. |
| Compatibility | Keep `ReagentContainerRuntime` behavior while the bridge is installed. |

The characterization suite must run before and after the move; no generated
procedure may be deleted until every row has a regression test.

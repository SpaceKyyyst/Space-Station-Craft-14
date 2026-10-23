# Руководство разработчика / Developer guide

## RU

### Окружение

Для сборки нужны JDK 25 и интернет при первой загрузке зависимостей. Gradle Wrapper использует версию из `gradle/wrapper/gradle-wrapper.properties`. Проект настроен на Minecraft 26.1.2 и NeoForge 26.1.2.109.

Проверьте Java и JVM, которую использует Gradle:

```powershell
java -version
.\gradlew.bat --version
```

Если версии различаются, настройте `JAVA_HOME` на JDK 25. Resolver в `settings.gradle` может загрузить toolchain автоматически, но явно установленная JDK даёт более предсказуемую локальную среду.

### Команды

Выполняйте команды из корня проекта. В Linux/macOS замените `.\gradlew.bat` на `sh ./gradlew`.

| Команда | Назначение |
|---|---|
| `.\gradlew.bat build` | Полная сборка, проверки и JAR в `build/libs/` |
| `.\gradlew.bat runClient` | Клиент Minecraft для разработки |
| `.\gradlew.bat runServer` | Dedicated server для разработки |
| `.\gradlew.bat domainTest` | Тесты чистых игровых правил |
| `.\gradlew.bat checkstyleMain` | Статические проверки Java-кода |
| `.\gradlew.bat pmdMain methodMetrics` | PMD и отчёт сложности методов |
| `.\gradlew.bat runGameTestServer` | Серверные сценарии из `src/gameTest/` |
| `.\gradlew.bat clean` | Удаление результатов предыдущей сборки |

### Карта репозитория

| Путь | Назначение |
|---|---|
| `src/main/java/net/mcreator/ssc/` | Код мода, адаптеры и игровые подсистемы |
| `src/main/resources/` | Ресурсы, data и метаданные мода |
| `src/domainTest/java/` | Тесты правил без Minecraft runtime |
| `src/gameTest/java/` | Серверные NeoForge/Minecraft GameTests |
| `elements/`, `models/`, `ssc_14.mcreator` | Исходные данные MCreator на переходный период |
| `config/checkstyle/` | Правила статического анализа |
| `.github/` | CI, metadata checks и release automation |
| `docs/` | Публичная техническая документация |

### Изменение контента и MCreator

Блоки, предметы и сущности зарегистрированы через `init/*`. Их идентификаторы связаны с resources и сохранениями мира. Переименование registry ID, NBT-ключей, SavedData ID или изменение порядка `GasType` требует согласованной миграции. Изменение network payload требует согласованного обновления клиента и сервера.

Зависимости мода объявлены в `build.gradle`, `Dependency.gradle` и `neoforge.mods.toml`. При их изменении проверяйте development runtime и готовый JAR в обычном профиле игры.

MCreator regeneration может заменить Java-файлы. Перед генерацией сохраните изменения, проверьте статус ручного кода, а после неё просмотрите полный diff и выполните сборку.

### Процесс вклада

Следуйте [публичной политике разработки](docs/DEVELOPMENT_POLICY.md). Команды проверки и описание CI находятся в [руководстве по проверкам](docs/VERIFICATION.md). При ошибке сборки см. [руководство по устранению проблем](docs/TROUBLESHOOTING.md).

## EN

### Environment

The build requires JDK 25 and an internet connection for the first dependency download. The Gradle Wrapper version is defined in `gradle/wrapper/gradle-wrapper.properties`. The project targets Minecraft 26.1.2 and NeoForge 26.1.2.109.

Check Java and the JVM used by Gradle:

```powershell
java -version
.\gradlew.bat --version
```

If the versions differ, point `JAVA_HOME` to JDK 25. The resolver in `settings.gradle` can provision the toolchain automatically, but an explicitly installed JDK makes the local environment more predictable.

### Commands

Run commands from the project root. On Linux or macOS, replace `.\gradlew.bat` with `sh ./gradlew`.

| Command | Purpose |
|---|---|
| `.\gradlew.bat build` | Full build, checks, and JAR under `build/libs/` |
| `.\gradlew.bat runClient` | Minecraft development client |
| `.\gradlew.bat runServer` | Development dedicated server |
| `.\gradlew.bat domainTest` | Pure game-rule tests without the Minecraft runtime |
| `.\gradlew.bat checkstyleMain` | Static checks for Java code |
| `.\gradlew.bat pmdMain methodMetrics` | PMD and method-complexity report |
| `.\gradlew.bat runGameTestServer` | Server scenarios from `src/gameTest/` |
| `.\gradlew.bat clean` | Remove previous build outputs |

### Repository map

| Path | Purpose |
|---|---|
| `src/main/java/net/mcreator/ssc/` | Mod code, adapters, and game subsystems |
| `src/main/resources/` | Mod assets, data, and metadata |
| `src/domainTest/java/` | Rule tests without the Minecraft runtime |
| `src/gameTest/java/` | Server-side NeoForge/Minecraft GameTests |
| `elements/`, `models/`, `ssc_14.mcreator` | MCreator source data during the transition |
| `config/checkstyle/` | Static-analysis rules |
| `.github/` | CI, metadata checks, and release automation |
| `docs/` | Public technical documentation |

### Content changes and MCreator

Blocks, items, and entities are registered through `init/*`. Their identifiers are tied to assets and world saves. Renaming a registry ID, NBT key, or SavedData ID, or changing the `GasType` order requires an agreed migration. Changing a network payload requires a coordinated client and server update.

Mod dependencies are declared in `build.gradle`, `Dependency.gradle`, and `neoforge.mods.toml`. When changing them, verify both the development runtime and the built JAR in a regular game profile.

MCreator regeneration can replace Java files. Before generation, preserve current changes and verify the status of manually maintained code. After generation, inspect the complete diff and run the build.

### Contribution workflow

Follow the [public development policy](docs/DEVELOPMENT_POLICY.md). Verification commands and CI behavior are documented in the [verification guide](docs/VERIFICATION.md). For build failures, see the [troubleshooting guide](docs/TROUBLESHOOTING.md).

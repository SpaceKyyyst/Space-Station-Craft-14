# Разработка

## Проверки

```powershell
.\gradlew.bat build
```

Быстрые тесты домена без запуска Minecraft: `.\gradlew.bat domainTest`.
Они компилируют те же исходники правил, что и мод, но без Minecraft-зависимостей.
Тесты находятся в `src/domainTest/java`, HTML-отчёт —
`build/reports/tests/domainTest/index.html`. Задача включена в `check` и `build`.

`checkstyleMain` проверяет чистый домен (газовые правила и `combat`); новые
извлекаемые подсистемы нужно добавлять в `sourceSets.domain`. Компиляция проверяет
весь мод. Правила Checkstyle находятся в `config/checkstyle/checkstyle.xml`.
CI выводит ошибки javac, Checkstyle и JUnit в GitHub Checks и сохраняет отчёты.
Проверка метаданных: `python .github/scripts/check_project.py` (Python 3.11+).
Она проверяет согласованность версии мода и наличие файлов сборки.

Linux/macOS: `sh ./gradlew build`. Использовать JDK 25 для полной сборки.
`settings.gradle` содержит resolver Java toolchains; автоматическая загрузка JDK
требует сети. Gradle Wrapper тоже загружает дистрибутив при первом запуске.

Автоматические тесты не заменяют проверку в игре: клиент, dedicated server,
сохранение/загрузка мира, несколько игроков. Для изменений энергетики проверять
соединение и разрыв кабелей, несколько источников, выгрузку чанков. Для атмосферы
проверять пустые ячейки, разные температуры и сохранение количества вещества.

Не коммитить `run/`, `build/`, `.gradle/`, локальные журналы и настройки IDE.
Зависимости и Wrapper должны оставаться в Git; не добавлять абсолютные локальные пути.
Архитектура и технический долг: [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md).

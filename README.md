---
Space-Station-Craft-14 - это мод на Minecraft, реализовывающий механики игры [Space Station 14](https://github.com/space-wizards/space-station-14).

## Ссылки

<div class="header" align="center">

Если вам вдруг нужны именно скомпилированный в готовый .jar файл мод, то я сохраняю их тут во вкладке "Releases"

Дискорд проекта: 
[Discord](https://discord.gg/dCtBN6EBAv)

</div>

## Сборка

Требуются JDK 25 и интернет для первой загрузки зависимостей. Проект рассчитан
на Minecraft 26.1.2 и NeoForge 26.1.2.71. Gradle устанавливать отдельно не нужно.

В PowerShell из папки проекта:

```powershell
$env:JAVA_HOME = 'C:\путь\к\jdk-25'
.\gradlew.bat build
```

`JAVA_HOME` должен указывать на каталог JDK 25, содержащий папку `bin`.
Если переменная уже настроена на JDK 25, достаточно второй команды.
На Linux/macOS: `sh ./gradlew build` с `JAVA_HOME`, указывающим на JDK 25.

Готовый мод: `build/libs/ssc_14-0.1.27.jar`. Запуск клиента для разработки:
`.\gradlew.bat runClient`.

Curios подключается через `Dependency.gradle`; этот файл, `gradlew`,
`gradlew.bat` и папку `gradle/wrapper` нужно сохранять в Git вместе с исходниками.
ItemPhysic, CreativeCore и Fusion подключаются в `build.gradle`.
Для установки готового JAR в игру эти зависимости также должны быть установлены
в версиях для Minecraft 26.1.2 / NeoForge.

---
## Лицензия
<p>Содержимое репозитория лицензируется под AGPLv3. </p>
<p>MCreator лицензируется под GPL-3.0 (Подробнее: https://github.com/MCreator/MCreator?tab=readme-ov-file). </p>

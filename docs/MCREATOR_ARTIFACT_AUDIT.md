# MCreator artifact audit / Аудит артефактов MCreator

## RU

Этот документ фиксирует границу удаления исходных артефактов MCreator.

- `src/main/java/net/ssc14` не импортирует `net.mcreator.ssc`.
- Gradle и CI не запускают MCreator и не читают `ssc_14.mcreator`.
- `src/main/resources` является runtime-источником ресурсов и не удаляется по
  признаку исторического происхождения.
- `elements/`, корневой `models/` и `ssc_14.mcreator` являются исходными
  материалами baseline. Их удаление допускается только после проверки, что
  каждый runtime-ресурс имеет эквивалент в `src/main/resources`, а registry ID
  и сохранения покрыты compatibility-тестами.

## EN

This document records the deletion boundary for MCreator source artifacts.

- `src/main/java/net/ssc14` does not import `net.mcreator.ssc`.
- Gradle and CI do not invoke MCreator or read `ssc_14.mcreator`.
- `src/main/resources` is the runtime resource source and must not be removed
  based only on historical provenance.
- `elements/`, the root `models/`, and `ssc_14.mcreator` are baseline source
  materials. They may be deleted only after every runtime resource has an
  equivalent under `src/main/resources` and registry IDs and saves are covered
  by compatibility tests.

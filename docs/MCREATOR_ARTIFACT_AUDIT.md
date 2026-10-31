# MCreator artifact audit / Аудит артефактов MCreator

## RU

Этот документ фиксирует завершение удаления исходных артефактов MCreator.

- `src/main/java/net/ssc14` не импортирует `net.mcreator.ssc`.
- Gradle и CI не запускают MCreator и не читают `ssc_14.mcreator`.
- `src/main/resources` является runtime-источником ресурсов и не удаляется по
  признаку исторического происхождения.
- `elements/`, корневой `models/` и `ssc_14.mcreator` удалены после проверки,
  что runtime-ресурсы находятся в `src/main/resources`, а registry ID и
  сохранения покрыты compatibility-тестами.

## EN

This document records completion of the MCreator source-artifact deletion.

- `src/main/java/net/ssc14` does not import `net.mcreator.ssc`.
- Gradle and CI do not invoke MCreator or read `ssc_14.mcreator`.
- `src/main/resources` is the runtime resource source and must not be removed
  based only on historical provenance.
- `elements/`, the root `models/`, and `ssc_14.mcreator` were deleted after
  verifying that runtime resources are under `src/main/resources` and registry
  IDs and saves are covered by compatibility tests.

<!-- Synchronization marker for PR metadata validation. -->

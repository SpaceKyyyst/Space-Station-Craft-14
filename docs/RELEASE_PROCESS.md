# SSC14: версии и публикация / Versions and releases

## RU

### Форматы версий

Версия хранится в `build.gradle` и должна совпадать с версией мода в
`src/main/resources/META-INF/neoforge.mods.toml`.

Поддерживаются следующие типы выпусков:

- стабильный: `0.1.27`;
- alpha: `0.1.28-alpha.1`;
- beta: `0.1.28-beta.1`;
- исправляющий fix/patch: `0.1.27.1`.

`RC` не является обязательным типом. Если проект его не использует, версии
`-rc.N` не создаются.

### Правила CI

- На Pull Request выполняются только тесты и governance-проверки.
- Сборка и публикация запускаются после изменения версии в `main`.
- Если версия не изменилась или такая версия уже опубликована, job завершает
  работу как `skip_duplicate` без сборки и отправки уведомлений.
- Build number является метаданными конкретной сборки и не заменяет версию.

### Описание обновления

Release summary должен содержать все пользовательские изменения начиная с
предыдущего опубликованного тега. В него входят стабильные, alpha, beta и fix
выпуски; описание не должно ограничиваться только последним коммитом.

### Discord

После успешной публикации отправляется сообщение по согласованному JSON-шаблону:

- версия, build number и статус;
- полный список изменений;
- ссылка на GitHub Release;
- прикреплённый JAR;
- безопасный `allowed_mentions` без случайных упоминаний.

Webhook хранится только в секретах CI. Dry-run и повторная проверка версии не
собирают JAR и не отправляют Discord-сообщение.

### Локальная проверка

```text
python .github/scripts/release_dry_run.py
```

Команда выводит версию, тип выпуска, summary и решение `skip_duplicate` или
`build_release`, не изменяя Git и не выполняя публикацию.

## EN

### Version formats

The version is stored in `build.gradle` and must match the mod version in
`src/main/resources/META-INF/neoforge.mods.toml`.

Supported release types are:

- stable: `0.1.27`;
- alpha: `0.1.28-alpha.1`;
- beta: `0.1.28-beta.1`;
- fix/patch: `0.1.27.1`.

`RC` is optional. When the project does not use release candidates, no `-rc.N`
versions are created.

### CI rules

- Pull requests run tests and governance checks only.
- Build and publication run after the version changes on `main`.
- If the version is unchanged or already published, the job exits as
  `skip_duplicate` without building or sending notifications.
- The build number identifies one build and does not replace the release version.

### Release description

The release summary must include every user-visible change since the previous
published tag. It covers stable, alpha, beta and fix releases and must not be
limited to the latest commit.

### Discord

After a successful publication, CI sends the agreed JSON payload containing:

- version, build number and status;
- the complete change list;
- a GitHub Release link;
- the attached JAR;
- safe `allowed_mentions` settings with no accidental mentions.

The webhook is stored only in CI secrets. Dry-runs and duplicate-version checks
do not build a JAR or send a Discord message.

### Local verification

```text
python .github/scripts/release_dry_run.py
```

The command prints the version, release type, summary and either
`skip_duplicate` or `build_release` without changing Git or publishing anything.

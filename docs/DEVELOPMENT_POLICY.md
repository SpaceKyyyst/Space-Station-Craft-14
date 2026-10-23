# Правила разработки / Development policy

## RU

SSC14 — открытый проект для русскоязычного и англоязычного сообщества. Каждая задача должна иметь ограниченный scope. Одна подсистема соответствует одной короткоживущей ветке и одному pull request. Коммиты внутри PR представляют завершённые логические изменения. Не смешивайте несвязанные изменения.

Создавайте ветку от актуального `main`. Для веток в fork используйте формат `<GitHub login>/<task-name>`. Открывайте из fork Draft PR в `SpaceKyyyst/Space-Station-Craft-14:main`. Переводите PR в Ready for review только после заполнения шаблона, проверки итогового diff и успешного выполнения всех применимых проверок. Не делайте прямые коммиты в `main`, force-push опубликованной истории или слияние без проверок и review.

Формат заголовка коммита: `type(scope): Русское описание / English description`. Тело коммита содержит секции `RU:` и `EN:` с фактическими изменениями, причиной и выполненными проверками. Pull request, issue и публичная документация должны содержать технически эквивалентные разделы RU и EN. Java API, идентификаторы, имена веток и labels остаются на английском. Применяйте labels по [отдельной политике](LABELS.md).

Перед PR:

1. Проверьте итоговый diff и список коммитов.
2. Запустите применимые тесты и сборку. Явно укажите, что не запускалось и почему.
3. Проверьте registry IDs, resources, serialization, save data и network contracts, если PR их затрагивает.
4. Проверьте client/server boundary и server-side authorization для network/UI changes.
5. Убедитесь, что diff не содержит private-файлы, secrets, логи, кэш, build outputs и локальные отчёты.

### Автоматические quality gates

Для pull request в `main` policy job запускается также на Draft PR и повторяется для событий `opened`, `synchronize`, `reopened`, `edited`, `labeled`, `unlabeled` и `ready_for_review`. Сборка Gradle, unit-тесты, GameTest, Checkstyle, PMD и Windows path checks выполняются после перевода PR в Ready; `ready_for_review` гарантированно запускает их повторно.

Policy job проверяет:

- Conventional Commits в формате `type(scope): Русское описание / English description` и непустые секции `RU:`/`EN:` в теле каждого коммита;
- непустые и расположенные в порядке `## RU`, затем `## EN` секции PR после удаления HTML-комментариев шаблона;
- ровно один `type:*` и минимум один `area:*` label;
- отсутствие private-файлов, локальных инструкций, кэша, build outputs, логов и случайных бинарных артефактов;
- только явно разрешённые бинарные resources и Gradle wrapper JAR.

Исключение ограничено PR от Dependabot (`dependabot[bot]`): для них не требуется RU/EN в commit messages и PR body. Conventional Commits, labels, проверка путей и бинарных файлов, сборка, тесты и статический анализ остаются обязательными. Событие, отправленное ботом или сопровождающим, не меняет автора PR и не расширяет исключение.

Не меняйте registry IDs, порядок сериализуемых enum, NBT/SavedData и network contracts без согласованной миграции и регрессионных тестов. Не удаляйте resources только из-за их расположения. Не добавляйте architecture layers, если они не решают конкретную задачу.

Никогда не публикуйте private prompts, agent instructions, internal plans, notes, reports, secrets или personal data. Ignore-правила не защищают уже tracked content: всегда проверяйте index и diff.

## EN

SSC14 is an open-source project for Russian- and English-speaking communities. Keep every task narrowly scoped. One subsystem maps to one short-lived branch and one pull request. Commits inside a PR represent complete logical changes. Do not mix unrelated changes.

Create a branch from the current `main`. Use `<GitHub login>/<task-name>` for branches in a fork. Open a Draft PR from the fork into `SpaceKyyyst/Space-Station-Craft-14:main`. Mark it Ready for review only after completing the template, reviewing the final diff, and passing every applicable check. Do not commit directly to `main`, force-push published history, or merge without required checks and review.

Commit title format: `type(scope): Russian description / English description`. The commit body contains `RU:` and `EN:` sections with the actual changes, reason, and completed checks. Pull requests, issues, and public documentation must contain technically equivalent RU and EN sections. Java APIs, identifiers, branch names, and labels remain in English. Apply labels according to the [label policy](LABELS.md).

Before opening a PR:

1. Inspect the final diff and commit list.
2. Run the applicable tests and build. Explicitly state what was not run and why.
3. Verify registry IDs, resources, serialization, save data, and network contracts when the PR touches them.
4. Verify the client/server boundary and server-side authorization for network or UI changes.
5. Ensure the diff contains no private files, secrets, logs, caches, build outputs, or local reports.

### Automated quality gates

For pull requests into `main`, the policy job also runs on Draft PRs and reruns for `opened`, `synchronize`, `reopened`, `edited`, `labeled`, `unlabeled`, and `ready_for_review` events. The Gradle build, unit tests, GameTest, Checkstyle, PMD, and Windows path checks run after the PR becomes Ready; `ready_for_review` always triggers them again.

The policy job enforces:

- Conventional Commits in `type(scope): Russian description / English description` format and non-empty `RU:`/`EN:` sections in every commit body;
- non-empty PR sections ordered as `## RU` followed by `## EN`, after template HTML comments are removed;
- exactly one `type:*` label and at least one `area:*` label;
- no private files, local instructions, caches, build outputs, logs, or accidental binary artifacts;
- only explicitly allowed binary resources and the Gradle wrapper JAR.

The exception is limited to PRs authored by Dependabot (`dependabot[bot]`): RU/EN text is not required in commit messages or the PR body. Conventional Commits, labels, path and binary checks, build, tests, and static analysis remain mandatory. Whether a bot or maintainer sent the current event does not change the PR author or broaden the exception.

Do not change registry IDs, the order of serialized enums, NBT/SavedData, or network contracts without an agreed migration and regression tests. Do not delete resources only because of their location. Do not add architecture layers unless they solve a concrete task.

Never publish private prompts, agent instructions, internal plans, notes, reports, secrets, or personal data. Ignore rules do not protect content that is already tracked: always inspect the index and diff.

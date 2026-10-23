# Правила labels / Label policy

## RU

Labels описывают фактический scope PR или issue. Не назначайте label только из-за его популярности и не дублируйте GitHub Draft/Ready state меткой `status:draft`.

Сейчас в репозитории можно использовать:

| Label | Когда назначать |
|---|---|
| `bug` | Исправление воспроизводимого дефекта |
| `documentation` | Только документация или шаблоны |
| `enhancement` | Новая или расширенная возможность |
| `dependencies` | Изменение dependency file или dependency version |
| `java` | Изменение Java-кода |
| `question`, `help wanted`, `good first issue` | Фактическое состояние issue, если оно подходит |
| `duplicate`, `invalid`, `wontfix` | Только после triage-решения |

После создания системной таксономии назначайте ровно один `type:*` и все фактически затронутые `area:*`. `risk:compatibility` и priority нужны только при реальном риске или срочности.

Минимальный предлагаемый набор:

- type: `type:bug`, `type:docs`, `type:feature`, `type:refactor`, `type:test`, `type:ci`;
- area: `area:governance`, `area:build`, `area:network`, `area:ui`, `area:atmosphere`, `area:energy`, `area:access`, `area:content`;
- risk: `risk:compatibility`;
- workflow: `status:blocked` только при внешнем блокере.

Новый label должен иметь английское имя, двуязычное описание и явную границу с соседними labels. Не создавайте label для одного единичного PR.

## EN

Labels describe the actual scope of a PR or issue. Do not assign a label only because it is popular, and do not duplicate GitHub's Draft/Ready state with a `status:draft` label.

Labels currently available in the repository:

| Label | When to apply it |
|---|---|
| `bug` | Fixes a reproducible defect |
| `documentation` | Documentation or templates only |
| `enhancement` | Adds or extends a capability |
| `dependencies` | Changes a dependency file or dependency version |
| `java` | Changes Java code |
| `question`, `help wanted`, `good first issue` | The issue actually has that state |
| `duplicate`, `invalid`, `wontfix` | Only after a triage decision |

After the systematic taxonomy is created, assign exactly one `type:*` label and every factually affected `area:*` label. Use `risk:compatibility` and priority only for a real compatibility risk or urgency.

Proposed minimal set:

- type: `type:bug`, `type:docs`, `type:feature`, `type:refactor`, `type:test`, `type:ci`;
- area: `area:governance`, `area:build`, `area:network`, `area:ui`, `area:atmosphere`, `area:energy`, `area:access`, `area:content`;
- risk: `risk:compatibility`;
- workflow: `status:blocked` only for an external blocker.

A new label must have an English name, a bilingual description, and a clear boundary from neighboring labels. Do not create a label for a single one-off PR.

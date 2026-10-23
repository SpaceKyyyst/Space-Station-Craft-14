# Правила labels / Label policy

## RU

Labels описывают фактический scope PR или issue. Не назначайте label только из-за его популярности и не дублируйте GitHub Draft/Ready state меткой `status:draft`.

Таксономия активна. Для каждого PR назначайте ровно один `type:*` и все фактически затронутые `area:*` labels. `risk:compatibility` нужен только при реальном риске нарушения публичного контракта, а `status:blocked` — только при внешнем блокере.

Обязательные группы PR:

- type: `type:bug`, `type:docs`, `type:feature`, `type:refactor`, `type:test`, `type:ci`;
- area: `area:governance`, `area:build`, `area:network`, `area:ui`, `area:atmosphere`, `area:energy`, `area:access`, `area:content`;
- risk: `risk:compatibility`;
- workflow: `status:blocked` только при внешнем блокере.

Существующие общие labels остаются для issue triage и переходного периода:

| Label | Когда назначать |
|---|---|
| `bug` | Исправление воспроизводимого дефекта |
| `documentation` | Только документация или шаблоны |
| `enhancement` | Новая или расширенная возможность |
| `dependencies` | Изменение dependency file или dependency version |
| `java` | Изменение Java-кода |
| `question`, `help wanted`, `good first issue` | Фактическое состояние issue, если оно подходит |
| `duplicate`, `invalid`, `wontfix` | Только после triage-решения |

Новый label должен иметь английское имя, двуязычное описание и явную границу с соседними labels. Не создавайте label для одного единичного PR.

## EN

Labels describe the actual scope of a PR or issue. Do not assign a label only because it is popular, and do not duplicate GitHub's Draft/Ready state with a `status:draft` label.

The taxonomy is active. Assign exactly one `type:*` label and every factually affected `area:*` label to each PR. Use `risk:compatibility` only for a real public-contract risk and `status:blocked` only for an external blocker.

Required PR groups:

- type: `type:bug`, `type:docs`, `type:feature`, `type:refactor`, `type:test`, `type:ci`;
- area: `area:governance`, `area:build`, `area:network`, `area:ui`, `area:atmosphere`, `area:energy`, `area:access`, `area:content`;
- risk: `risk:compatibility`;
- workflow: `status:blocked` only for an external blocker.

Existing general-purpose labels remain available for issue triage and the transition period:

| Label | When to apply it |
|---|---|
| `bug` | Fixes a reproducible defect |
| `documentation` | Documentation or templates only |
| `enhancement` | Adds or extends a capability |
| `dependencies` | Changes a dependency file or dependency version |
| `java` | Changes Java code |
| `question`, `help wanted`, `good first issue` | The issue actually has that state |
| `duplicate`, `invalid`, `wontfix` | Only after a triage decision |

A new label must have an English name, a bilingual description, and a clear boundary from neighboring labels. Do not create a label for a single one-off PR.

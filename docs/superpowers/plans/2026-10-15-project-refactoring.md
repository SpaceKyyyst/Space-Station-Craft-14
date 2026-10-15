# Project foundation implementation plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Establish reviewable development, release automation and tested domain boundaries.
**Architecture:** Keep compatibility facades and registry identifiers. Extract pure rules from framework adapters; validate mutable reaction definitions at their boundary.
**Tech Stack:** Java 25, NeoForge 26.1.2.71, Gradle 9.3.0, GitHub Actions.
**Spec:** User request in this chat; `docs/ARCHITECTURE.md` describes migration constraints.

## Global Constraints

- One task per `ShinozakiAichi/...` branch; small completed changes per commit.
- Preserve world and network formats and valid gameplay calculations.
- Publish through a fork: authenticated account has no upstream push permission.
- Main pushes must produce a release only after successful build and tests.

## Review Focus

- Re-running a release job must not overwrite an unrelated commit's artifact.
- Pull requests must never receive release write permissions.
- Resistance registration must not allow NaN to poison damage calculations.
- Reaction arrays must not be mutable through constructor arguments/accessors.
- Rejected reactions must not partially mutate cell state.

## Tasks

1. `project-foundation`: document architecture, contributor workflow and risks;
   validate file links and actual build versions, commit documentation.
2. `main-release`: add PR build and main release workflows; add focused domain
   test harness and wire it into `check`; verify scripts and build. Commit each
   deliverable separately. Publish this branch and inspect Actions results.
3. `armor-domain`: add `combat/ResistanceRegistry.java` and tests for defaults,
   clamping, stacking, key isolation and invalid numbers; observe failure then
   implement. Keep `ArmorResistanceHelper` API, replace stdout with debug logging.
4. `gas-reaction`: test defensive copies, constructor validation, temperature
   boundaries, insufficient reagents and no mutation on rejection. Observe failures,
   implement validation and immutable definitions, run all domain tests and build.
5. Integrate task branches, review diff, publish PR, record actual verification
   results and the remaining runtime checks. This is the first migration increment,
   not a claim that all generated content has been rewritten.

## Decisions

- Execute autonomously as requested; no additional plan approval round.
- Keep generated registries and resource identifiers intact: save compatibility.
- Full gameplay migration requires a later measured iteration; no speculative
  rewrites of energy graphs or simulation balance in this increment.

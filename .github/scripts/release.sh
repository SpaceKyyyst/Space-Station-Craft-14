#!/usr/bin/env bash
set -euo pipefail

: "${GITHUB_SHA:?Missing commit SHA}"
: "${GITHUB_RUN_NUMBER:?Missing run number}"
: "${GH_REPO:?Missing repository}"

shopt -s nullglob
jars=()
for file in build/libs/*.jar; do
  case "$file" in
    *-sources.jar|*-javadoc.jar) continue ;;
  esac
  jars+=("$file")
done
if (( ${#jars[@]} != 1 )); then
  echo "Expected exactly one mod JAR, found ${#jars[@]}" >&2
  exit 1
fi

jar="${jars[0]}"
artifact_name="$(basename "$jar" .jar)"
tag="${artifact_name}-build.${GITHUB_RUN_NUMBER}-${GITHUB_SHA:0:12}"
(cd build/libs && sha256sum "$(basename "$jar")" > SHA256SUMS.txt)

# Validate the tag even when its release does not exist yet. Creating the ref
# first also makes an interrupted draft publication recoverable on the next run.
if target="$(gh api "repos/${GH_REPO}/git/ref/tags/${tag}" --jq '.object.sha' 2>/dev/null)"; then
  if [[ "$target" != "$GITHUB_SHA" ]]; then
    echo "Existing tag points to a different commit" >&2
    exit 1
  fi
else
  gh api --method POST "repos/${GH_REPO}/git/refs" \
    -f "ref=refs/tags/${tag}" -f "sha=${GITHUB_SHA}" > /dev/null
fi

if is_draft="$(gh release view "$tag" --json isDraft --jq '.isDraft' 2>/dev/null)"; then
  gh release upload "$tag" "$jar" build/libs/SHA256SUMS.txt --clobber
  if [[ "$is_draft" == true ]]; then
    gh release edit "$tag" --draft=false
  fi
else
  gh release create "$tag" "$jar" build/libs/SHA256SUMS.txt \
    --verify-tag --title "$artifact_name (build $GITHUB_RUN_NUMBER)" \
    --generate-notes
fi

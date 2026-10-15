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

if gh release view "$tag" > /dev/null 2>&1; then
  # Our releases use lightweight tags. Refuse to overwrite any other target.
  target="$(gh api "repos/${GH_REPO}/git/ref/tags/${tag}" --jq '.object.sha')"
  if [[ "$target" != "$GITHUB_SHA" ]]; then
    echo "Existing release tag points to a different commit" >&2
    exit 1
  fi
  gh release upload "$tag" "$jar" build/libs/SHA256SUMS.txt --clobber
else
  gh release create "$tag" "$jar" build/libs/SHA256SUMS.txt \
    --target "$GITHUB_SHA" --title "$artifact_name (build $GITHUB_RUN_NUMBER)" \
    --generate-notes
fi

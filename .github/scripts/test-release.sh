#!/usr/bin/env bash
set -euo pipefail

script="$(cd "$(dirname "$0")" && pwd)/release.sh"
sandbox="$(mktemp -d)"
cd "$sandbox"
mkdir -p bin build/libs
export GITHUB_SHA=1234567890abcdef1234567890abcdef12345678
export GITHUB_RUN_NUMBER=42 GH_REPO=test/repo
export MOCK_LOG="$sandbox/gh.log" MOCK_EXISTS=false MOCK_TARGET="$GITHUB_SHA"
export MOCK_DRAFT=false MOCK_TAG_EXISTS=true
export PATH="$sandbox/bin:$PATH"
cat > bin/gh <<'MOCK'
#!/usr/bin/env bash
set -eu
case "$1 $2" in
  'release view') [[ "$MOCK_EXISTS" == true ]] && printf '%s\n' "$MOCK_DRAFT" ;;
  'api --method') printf '%s\n' "$*" >> "$MOCK_LOG" ;;
  'api '*) [[ "$MOCK_TAG_EXISTS" == true ]] && printf '%s\n' "$MOCK_TARGET" ;;
  'release create'|'release upload'|'release edit') printf '%s\n' "$*" >> "$MOCK_LOG" ;;
  *) echo "Unexpected gh invocation" >&2; exit 1 ;;
esac
MOCK
chmod +x bin/gh

if bash "$script" > output.log 2>&1; then
  echo 'Missing JAR must fail' >&2; exit 1
fi
grep -q 'Expected exactly one mod JAR' output.log
printf 'test jar' > build/libs/ssc_14-0.1.27.jar
printf 'sources' > build/libs/ssc_14-0.1.27-sources.jar
bash "$script"
grep -q 'release create ssc_14-0.1.27-build.42-1234567890ab' "$MOCK_LOG"
(cd build/libs && sha256sum --check SHA256SUMS.txt)

export MOCK_EXISTS=true
bash "$script"
grep -q 'release upload .*--clobber' "$MOCK_LOG"

export MOCK_TARGET=another-commit
if bash "$script" > output.log 2>&1; then
  echo 'Mismatched tag must fail' >&2; exit 1
fi
grep -q 'different commit' output.log

export MOCK_EXISTS=false
if bash "$script" > output.log 2>&1; then
  echo 'Existing mismatched tag without a release must fail' >&2; exit 1
fi
grep -q 'different commit' output.log

export MOCK_TARGET="$GITHUB_SHA" MOCK_EXISTS=true MOCK_DRAFT=true
bash "$script"
grep -q 'release edit .*--draft=false' "$MOCK_LOG"

export MOCK_EXISTS=false MOCK_TAG_EXISTS=false
bash "$script"
grep -q 'api --method POST .*git/refs' "$MOCK_LOG"

printf 'second jar' > build/libs/another.jar
if bash "$script" > output.log 2>&1; then
  echo 'Ambiguous JAR selection must fail' >&2; exit 1
fi
grep -q 'Expected exactly one mod JAR' output.log
echo "Release script checks passed (fixtures: $sandbox)"

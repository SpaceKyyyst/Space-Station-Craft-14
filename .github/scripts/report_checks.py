"""Convert javac, Checkstyle, PMD and JUnit failures into GitHub annotations."""
import re
import xml.etree.ElementTree as ET
from pathlib import Path


def escape(value):
    return str(value).replace("%", "%25").replace("\r", "%0D").replace("\n", "%0A")


def error(message, path=None, line=None):
    properties = []
    if path:
        properties.append("file=" + escape(path).replace(",", "%2C").replace(":", "%3A"))
    if line:
        properties.append("line=" + str(line))
    suffix = " " + ",".join(properties) if properties else ""
    print(f"::error{suffix}::{escape(message)}")


def report(root):
    log = root / "build-output.log"
    if log.exists():
        for line in log.read_text(encoding="utf-8", errors="replace").splitlines():
            match = re.match(r"(.+\.java):(\d+): error: (.*)", line)
            if match:
                error(match[3], match[1], match[2])
    for report_file in (root / "build/reports/checkstyle").glob("*.xml"):
        for file in ET.parse(report_file).getroot().findall("file"):
            for violation in file.findall("error"):
                error(violation.get("message"), file.get("name"), violation.get("line"))
    for report_file in (root / "build/reports/pmd").glob("*.xml"):
        if report_file.name == "method-metrics.xml":
            continue  # Metrics are an inventory, not failed correctness checks.
        tree = ET.parse(report_file).getroot()
        for file in tree.findall("{*}file"):
            for violation in file.findall("{*}violation"):
                message = f"{violation.get('rule')}: {(violation.text or '').strip()}"
                error(message, file.get("name"), violation.get("beginline"))
        for failure in tree.findall("{*}error"):
            error(failure.get("msg"), failure.get("filename"))
    for report_file in (root / "build/test-results").glob("**/TEST-*.xml"):
        for case in ET.parse(report_file).getroot().iter("testcase"):
            for failure in list(case.findall("failure")) + list(case.findall("error")):
                error(f"{case.get('classname')}.{case.get('name')}: {failure.get('message')}")


if __name__ == "__main__":
    report(Path.cwd())

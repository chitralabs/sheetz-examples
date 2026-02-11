# Sheetz Examples

Runnable examples demonstrating the [Sheetz](https://github.com/chitralabs/sheetz) library — high-performance Excel and CSV processing for Java.

[![Maven Central](https://img.shields.io/maven-central/v/io.github.chitralabs.sheetz/sheetz-core)](https://central.sonatype.com/artifact/io.github.chitralabs.sheetz/sheetz-core)

## Prerequisites

- Java 11+
- Maven 3.6+

## Quick Start

```bash
git clone https://github.com/chitralabs/sheetz-examples.git
cd sheetz-examples
mvn compile
```

## Examples

| # | Example | Description |
|---|---------|-------------|
| 01 | [Basic Read & Write](src/main/java/io/github/chitralabs/sheetz/examples/E01_BasicReadWrite.java) | Read/write Excel & CSV in one line, readMaps(), readRaw() |
| 02 | [Annotation Mapping](src/main/java/io/github/chitralabs/sheetz/examples/E02_AnnotationMapping.java) | @Column with custom headers, required, defaultValue, format, ignore, width |
| 03 | [Streaming Large Files](src/main/java/io/github/chitralabs/sheetz/examples/E03_StreamingLargeFiles.java) | Stream 100K+ rows with constant memory, batch processing |
| 04 | [Data Validation](src/main/java/io/github/chitralabs/sheetz/examples/E04_DataValidation.java) | Validate data and report row-level errors with statistics |
| 05 | [Builder API](src/main/java/io/github/chitralabs/sheetz/examples/E05_BuilderApi.java) | ReaderBuilder/WriterBuilder with sheet selection, autoSize, freezeHeader, delimiters |
| 06 | [Multi-Sheet Workbook](src/main/java/io/github/chitralabs/sheetz/examples/E06_MultiSheetWorkbook.java) | Create multi-sheet Excel reports with different model types |
| 07 | [Custom Converter](src/main/java/io/github/chitralabs/sheetz/examples/E07_CustomConverter.java) | Custom MoneyConverter for BigDecimal, global converter registration |
| 08 | [Format Conversion](src/main/java/io/github/chitralabs/sheetz/examples/E08_FormatConversion.java) | Convert between XLSX, XLS, and CSV formats |

## Running an Example

```bash
# Run any example by its fully-qualified class name
mvn compile exec:java -Dexec.mainClass="io.github.chitralabs.sheetz.examples.E01_BasicReadWrite"

# Run the streaming example
mvn compile exec:java -Dexec.mainClass="io.github.chitralabs.sheetz.examples.E03_StreamingLargeFiles"

# Run all examples in sequence
for i in 01 02 03 04 05 06 07 08; do
  echo "--- Running E${i} ---"
  mvn -q compile exec:java -Dexec.mainClass="io.github.chitralabs.sheetz.examples.E${i}_$(ls src/main/java/io/github/chitralabs/sheetz/examples/E${i}_*.java | xargs basename | sed 's/.java//')"
done
```

Output files are written to the `output/` directory (git-ignored).

## Maven Dependency

To use Sheetz in your own project:

```xml
<dependency>
    <groupId>io.github.chitralabs.sheetz</groupId>
    <artifactId>sheetz-core</artifactId>
    <version>1.0.1</version>
</dependency>
```

## License

These examples are released under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).

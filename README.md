# Sheetz Examples — Learn Excel & CSV Processing in Java by Example

[![Maven Central](https://img.shields.io/maven-central/v/io.github.chitralabs.sheetz/sheetz-core)](https://central.sonatype.com/artifact/io.github.chitralabs.sheetz/sheetz-core)
[![Java 11+](https://img.shields.io/badge/Java-11%2B-blue.svg)](https://openjdk.java.net/)
[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![GitHub stars](https://img.shields.io/github/stars/chitralabs/sheetz?style=social)](https://github.com/chitralabs/sheetz)

**8 runnable examples** demonstrating every feature of the [Sheetz](https://github.com/chitralabs/sheetz) library — read, write, stream, and validate Excel and CSV files in Java with minimal code.

```java
// This is all it takes to read an Excel file into Java objects
List<Product> products = Sheetz.read("products.xlsx", Product.class);

// And this is all it takes to write them back
Sheetz.write(products, "products.xlsx");
```

Each example is a standalone `main()` you can run immediately. No setup beyond Java 11 and Maven.

> **Looking for performance data?** See [sheetz-benchmarks](https://github.com/chitralabs/sheetz-benchmarks) for JMH benchmarks and code comparisons against Apache POI, EasyExcel, FastExcel, and Poiji.

---

## Quick Start

```bash
git clone https://github.com/chitralabs/sheetz-examples.git
cd sheetz-examples
mvn compile exec:java -Dexec.mainClass="io.github.chitralabs.sheetz.examples.E01_BasicReadWrite"
```

Output files are written to the `output/` directory.

---

## Examples

### 01 — Basic Read & Write

Read and write Excel and CSV files in one line. Also covers `readMaps()` for schema-free access and `readRaw()` for raw string arrays.

```java
Sheetz.write(products, "products.xlsx");
List<Product> fromExcel = Sheetz.read("products.xlsx", Product.class);

List<Map<String, Object>> maps = Sheetz.readMaps("products.xlsx");
List<String[]> raw = Sheetz.readRaw("products.xlsx");
```

[View source](src/main/java/io/github/chitralabs/sheetz/examples/E01_BasicReadWrite.java)

---

### 02 — Annotation Mapping

Customize how fields map to spreadsheet columns using `@Column` — custom headers, required fields, default values, date formats, column widths, and ignored fields.

```java
@Column(value = "Invoice #", index = 0, required = true, width = 15)
private String invoiceNumber;

@Column(value = "Due Date", format = "MM/dd/yyyy", width = 14)
private LocalDate dueDate;

@Column(value = "Status", defaultValue = "Pending")
private String status;

@Column(ignore = true)
private String secretKey;  // Won't appear in file
```

[View source](src/main/java/io/github/chitralabs/sheetz/examples/E02_AnnotationMapping.java)

---

### 03 — Streaming Large Files

Process 100K+ rows with constant ~10MB memory. Demonstrates row-by-row iteration, batch processing for bulk database inserts, and Java Streams API integration.

```java
// Row by row — constant memory
try (StreamingReader<Product> reader = Sheetz.stream("huge.csv", Product.class)) {
    for (Product p : reader) { process(p); }
}

// Batch processing
reader.batch(1000).forEach(batch -> database.bulkInsert(batch));

// Java Streams
long count = reader.stream()
    .filter(p -> "Electronics".equals(p.getCategory()))
    .count();
```

[View source](src/main/java/io/github/chitralabs/sheetz/examples/E03_StreamingLargeFiles.java)

---

### 04 — Data Validation

Validate spreadsheet data before import. Get per-row error details with column names, invalid values, and root causes. Includes success rate statistics.

```java
ValidationResult<StrictProduct> result = Sheetz.validate("data.csv", StrictProduct.class);

System.out.printf("Valid: %d | Errors: %d | Success: %.1f%%%n",
    result.validCount(), result.errorCount(), result.successRate());

for (ValidationResult.RowError error : result.errors()) {
    System.out.printf("Row %d [%s]: %s (value: %s)%n",
        error.row(), error.column(), error.message(), error.value());
}
```

[View source](src/main/java/io/github/chitralabs/sheetz/examples/E04_DataValidation.java)

---

### 05 — Builder API

Fine-grained control with `ReaderBuilder` and `WriterBuilder` — sheet selection by name or index, auto-sized columns, frozen headers, custom CSV delimiters, and global configuration.

```java
Sheetz.writer(Product.class)
    .data(products)
    .file("output.xlsx")
    .sheet("Product Catalog")
    .autoSize(true)
    .freezeHeader(true)
    .write();

List<Product> products = Sheetz.reader(Product.class)
    .file("output.xlsx")
    .sheet("Product Catalog")
    .read();
```

[View source](src/main/java/io/github/chitralabs/sheetz/examples/E05_BuilderApi.java)

---

### 06 — Multi-Sheet Workbook

Create Excel workbooks with multiple sheets, each containing a different model type. Read back from individual sheets by name or index.

```java
Sheetz.workbook()
    .sheet("Products", products)
    .sheet("Employees", employees)
    .write("report.xlsx");

List<Product> prods = Sheetz.reader(Product.class)
    .file("report.xlsx")
    .sheet("Products")
    .read();
```

[View source](src/main/java/io/github/chitralabs/sheetz/examples/E06_MultiSheetWorkbook.java)

---

### 07 — Custom Converter

Build custom converters for specialized types like money formatting. Register converters per-field with `@Column(converter = ...)` or globally with `Sheetz.register()`.

```java
public class MoneyConverter implements Converter<BigDecimal> {
    public BigDecimal fromCell(Object value, ConvertContext ctx) {
        String str = value.toString().replace("$", "").replace(",", "").trim();
        return new BigDecimal(str);
    }
    public Object toCell(BigDecimal value) {
        return "$" + value.setScale(2, RoundingMode.HALF_UP);
    }
}

// Register globally — all BigDecimal fields use this converter
Sheetz.register(BigDecimal.class, new MoneyConverter());
```

[View source](src/main/java/io/github/chitralabs/sheetz/examples/E07_CustomConverter.java)

---

### 08 — Format Conversion

Convert between XLSX, XLS, and CSV formats. Sheetz auto-detects the format from the file extension — converting is just read + write.

```java
// XLSX → CSV
List<Product> data = Sheetz.read("products.xlsx", Product.class);
Sheetz.write(data, "products.csv");

// CSV → XLS (legacy)
List<Product> data = Sheetz.read("products.csv", Product.class);
Sheetz.write(data, "products.xls");
```

[View source](src/main/java/io/github/chitralabs/sheetz/examples/E08_FormatConversion.java)

---

## Running Examples

```bash
# Run any example by class name
mvn compile exec:java -Dexec.mainClass="io.github.chitralabs.sheetz.examples.E01_BasicReadWrite"

# Run the streaming example
mvn compile exec:java -Dexec.mainClass="io.github.chitralabs.sheetz.examples.E03_StreamingLargeFiles"

# Run all examples in sequence
for i in 01 02 03 04 05 06 07 08; do
  echo "--- Running E${i} ---"
  mvn -q compile exec:java -Dexec.mainClass="io.github.chitralabs.sheetz.examples.E${i}_$(ls src/main/java/io/github/chitralabs/sheetz/examples/E${i}_*.java | xargs basename | sed 's/.java//')"
done
```

---

## Prerequisites

- Java 11 or higher
- Maven 3.6+

## Using Sheetz in Your Project

**Maven:**
```xml
<dependency>
    <groupId>io.github.chitralabs.sheetz</groupId>
    <artifactId>sheetz-core</artifactId>
    <version>1.0.1</version>
</dependency>
```

**Gradle:**
```groovy
implementation 'io.github.chitralabs.sheetz:sheetz-core:1.0.1'
```

## Links

- [Sheetz — main library](https://github.com/chitralabs/sheetz)
- [Sheetz Benchmarks — performance comparisons](https://github.com/chitralabs/sheetz-benchmarks)
- [Sheetz on Maven Central](https://central.sonatype.com/artifact/io.github.chitralabs.sheetz/sheetz-core)

## License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0) — free for commercial and personal use.

---

If Sheetz saved you time, consider giving the [main repo](https://github.com/chitralabs/sheetz) a star. It helps other developers find the project.

[![Star Sheetz](https://img.shields.io/github/stars/chitralabs/sheetz?style=social)](https://github.com/chitralabs/sheetz)

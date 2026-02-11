package io.github.chitralabs.sheetz.examples;

import io.github.chitralabs.sheetz.Sheetz;
import io.github.chitralabs.sheetz.ValidationResult;
import io.github.chitralabs.sheetz.annotation.Column;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * E04 — Data Validation
 *
 * Demonstrates validation of spreadsheet data:
 * - Sheetz.validate() to get a ValidationResult
 * - Inspecting valid rows and row-level errors
 * - Statistics: totalRows, validCount, errorCount, successRate
 * - Using ReaderBuilder.validate() as an alternative
 */
public class E04_DataValidation {

    /**
     * Strict model where some fields are required.
     */
    public static class StrictProduct {

        @Column(value = "Product Name", required = true)
        private String name;

        @Column(value = "Price", required = true)
        private Double price;

        @Column(value = "Release Date", format = "yyyy-MM-dd")
        private LocalDate releaseDate;

        @Column(value = "Category", required = true)
        private String category;

        public StrictProduct() {}

        public StrictProduct(String name, Double price, LocalDate releaseDate, String category) {
            this.name = name;
            this.price = price;
            this.releaseDate = releaseDate;
            this.category = category;
        }

        @Override
        public String toString() {
            return String.format("StrictProduct{name='%s', price=%s, date=%s, category='%s'}",
                    name, price, releaseDate, category);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== E04: Data Validation ===\n");

        // Create a file with some intentional problems
        String path = "output/products_with_errors.xlsx";
        createTestFileWithErrors(path);
        System.out.println("Created test file with intentional errors at " + path + "\n");

        // --- Validate using Sheetz.validate() ---
        System.out.println("--- Validating data ---");
        ValidationResult<StrictProduct> result = Sheetz.validate(path, StrictProduct.class);

        System.out.printf("Total rows:   %d%n", result.totalRows());
        System.out.printf("Valid rows:   %d%n", result.validCount());
        System.out.printf("Error rows:   %d%n", result.errorCount());
        System.out.printf("Success rate: %.1f%%%n", result.successRate());
        System.out.printf("Duration:     %d ms%n%n", result.durationMs());

        // --- Print valid rows ---
        System.out.println("--- Valid rows ---");
        for (StrictProduct p : result.validRows()) {
            System.out.println("  " + p);
        }

        // --- Print errors ---
        if (result.hasErrors()) {
            System.out.println("\n--- Errors ---");
            for (ValidationResult.RowError error : result.errors()) {
                System.out.printf("  Row %d: [%s] %s (value: %s)%n",
                        error.row(), error.column(), error.message(), error.value());
            }
        }

        // --- Alternative: use ReaderBuilder.validate() ---
        System.out.println("\n--- Using ReaderBuilder.validate() ---");
        ValidationResult<StrictProduct> result2 = Sheetz.reader(StrictProduct.class)
                .file(path)
                .validate();
        System.out.printf("isValid: %s  |  %d valid  |  %d errors%n",
                result2.isValid(), result2.validCount(), result2.errorCount());

        System.out.println("\nDone!");
    }

    /**
     * Creates a test Excel file with some rows that will fail validation.
     * We write raw data to include intentional errors (missing required fields, bad types).
     */
    private static void createTestFileWithErrors(String path) {
        // Good rows
        List<StrictProduct> products = Arrays.asList(
            new StrictProduct("Laptop",     999.99, LocalDate.of(2024, 3, 15), "Electronics"),
            new StrictProduct("Headphones",  79.50, LocalDate.of(2024, 1, 10), "Electronics"),
            new StrictProduct(null,          34.99, LocalDate.of(2023, 11, 1), "Home"),       // Missing required name
            new StrictProduct("Notebook",     5.99, LocalDate.of(2024, 6, 20), null),         // Missing required category
            new StrictProduct("Water Bottle", null, LocalDate.of(2024, 2, 28), "Sports")      // Missing required price
        );
        Sheetz.write(products, path);
    }
}

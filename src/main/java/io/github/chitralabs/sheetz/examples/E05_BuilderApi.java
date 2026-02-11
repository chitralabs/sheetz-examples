package io.github.chitralabs.sheetz.examples;

import io.github.chitralabs.sheetz.Sheetz;
import io.github.chitralabs.sheetz.SheetzConfig;
import io.github.chitralabs.sheetz.examples.model.Product;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * E05 — Builder API
 *
 * Demonstrates fine-grained control with ReaderBuilder and WriterBuilder:
 * - WriterBuilder: sheet name, autoSize, freezeHeader, streaming, delimiter
 * - ReaderBuilder: sheet selection (by index/name), headerRow, delimiter
 * - Global configuration with SheetzConfig
 */
public class E05_BuilderApi {

    public static void main(String[] args) {
        System.out.println("=== E05: Builder API ===\n");

        List<Product> products = Arrays.asList(
            new Product("Laptop",       999.99,  true,  LocalDate.of(2024, 3, 15), "Electronics"),
            new Product("Headphones",    79.50,  true,  LocalDate.of(2024, 1, 10), "Electronics"),
            new Product("Desk Lamp",     34.99,  false, LocalDate.of(2023, 11, 1), "Home"),
            new Product("Notebook",       5.99,  true,  LocalDate.of(2024, 6, 20), "Office"),
            new Product("Water Bottle",  12.00,  true,  LocalDate.of(2024, 2, 28), "Sports")
        );

        // --- WriterBuilder: Excel with all options ---
        System.out.println("--- WriterBuilder: Excel with all options ---");
        String xlsxPath = "output/builder_products.xlsx";
        Sheetz.writer(Product.class)
              .data(products)
              .file(xlsxPath)
              .sheet("Product Catalog")
              .autoSize(true)
              .freezeHeader(true)
              .write();
        System.out.println("Wrote " + xlsxPath + " (autoSize, freezeHeader, custom sheet name)");

        // --- WriterBuilder: CSV with custom delimiter ---
        System.out.println("\n--- WriterBuilder: CSV with pipe delimiter ---");
        String csvPath = "output/builder_products_pipe.csv";
        Sheetz.writer(Product.class)
              .data(products)
              .file(csvPath)
              .delimiter('|')
              .write();
        System.out.println("Wrote " + csvPath);

        // --- ReaderBuilder: Read from specific sheet by name ---
        System.out.println("\n--- ReaderBuilder: Read from named sheet ---");
        List<Product> fromSheet = Sheetz.reader(Product.class)
                .file(xlsxPath)
                .sheet("Product Catalog")
                .read();
        System.out.println("Read " + fromSheet.size() + " products from sheet 'Product Catalog':");
        fromSheet.forEach(p -> System.out.println("  " + p));

        // --- ReaderBuilder: Read from sheet by index ---
        System.out.println("\n--- ReaderBuilder: Read from sheet index 0 ---");
        List<Product> fromIndex = Sheetz.reader(Product.class)
                .file(xlsxPath)
                .sheet(0)
                .read();
        System.out.println("Read " + fromIndex.size() + " products from sheet index 0");

        // --- ReaderBuilder: CSV with custom delimiter ---
        System.out.println("\n--- ReaderBuilder: CSV with pipe delimiter ---");
        List<Product> fromPipeCsv = Sheetz.reader(Product.class)
                .file(csvPath)
                .delimiter('|')
                .read();
        System.out.println("Read " + fromPipeCsv.size() + " products from pipe-delimited CSV:");
        fromPipeCsv.forEach(p -> System.out.println("  " + p));

        // --- Global configuration ---
        System.out.println("\n--- SheetzConfig: Global configuration ---");
        SheetzConfig config = SheetzConfig.builder()
                .dateFormat("dd/MM/yyyy")
                .trimValues(true)
                .skipEmptyRows(true)
                .build();
        Sheetz.configure(config);
        System.out.println("Configured: dateFormat=dd/MM/yyyy, trimValues=true, skipEmptyRows=true");

        // Reset to defaults
        Sheetz.reset();
        System.out.println("Reset to defaults");

        System.out.println("\nDone!");
    }
}

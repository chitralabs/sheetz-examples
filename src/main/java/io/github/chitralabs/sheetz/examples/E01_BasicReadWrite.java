package io.github.chitralabs.sheetz.examples;

import io.github.chitralabs.sheetz.Sheetz;
import io.github.chitralabs.sheetz.examples.model.Product;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * E01 — Basic Read & Write
 *
 * Demonstrates the simplest Sheetz operations:
 * - Writing a list of objects to Excel and CSV
 * - Reading typed objects back
 * - Reading as Maps (header → value)
 * - Reading raw string arrays
 */
public class E01_BasicReadWrite {

    public static void main(String[] args) {
        System.out.println("=== E01: Basic Read & Write ===\n");

        // --- Create sample data ---
        List<Product> products = Arrays.asList(
            new Product("Laptop",       999.99,  true,  LocalDate.of(2024, 3, 15), "Electronics"),
            new Product("Headphones",    79.50,  true,  LocalDate.of(2024, 1, 10), "Electronics"),
            new Product("Desk Lamp",     34.99,  false, LocalDate.of(2023, 11, 1), "Home"),
            new Product("Notebook",       5.99,  true,  LocalDate.of(2024, 6, 20), "Office"),
            new Product("Water Bottle",  12.00,  true,  LocalDate.of(2024, 2, 28), "Sports")
        );

        // --- Write to Excel ---
        String xlsxPath = "output/products.xlsx";
        Sheetz.write(products, xlsxPath);
        System.out.println("Wrote " + products.size() + " products to " + xlsxPath);

        // --- Write to CSV ---
        String csvPath = "output/products.csv";
        Sheetz.write(products, csvPath);
        System.out.println("Wrote " + products.size() + " products to " + csvPath);

        // --- Read typed objects from Excel ---
        System.out.println("\n--- Read as typed objects (XLSX) ---");
        List<Product> fromExcel = Sheetz.read(xlsxPath, Product.class);
        fromExcel.forEach(System.out::println);

        // --- Read typed objects from CSV ---
        System.out.println("\n--- Read as typed objects (CSV) ---");
        List<Product> fromCsv = Sheetz.read(csvPath, Product.class);
        fromCsv.forEach(System.out::println);

        // --- Read as Maps ---
        System.out.println("\n--- Read as Maps ---");
        List<Map<String, Object>> maps = Sheetz.readMaps(xlsxPath);
        for (Map<String, Object> row : maps) {
            System.out.println(row);
        }

        // --- Read raw string arrays ---
        System.out.println("\n--- Read raw string arrays ---");
        List<String[]> raw = Sheetz.readRaw(xlsxPath);
        for (String[] row : raw) {
            System.out.println(Arrays.toString(row));
        }

        System.out.println("\nDone!");
    }
}

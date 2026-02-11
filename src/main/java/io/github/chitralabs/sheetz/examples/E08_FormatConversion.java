package io.github.chitralabs.sheetz.examples;

import io.github.chitralabs.sheetz.Sheetz;
import io.github.chitralabs.sheetz.examples.model.Product;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * E08 — Format Conversion
 *
 * Demonstrates converting between Excel and CSV formats:
 * - XLSX → CSV
 * - CSV → XLSX
 * - XLSX → XLS (legacy format)
 * - Read and write with format auto-detection from file extension
 */
public class E08_FormatConversion {

    public static void main(String[] args) {
        System.out.println("=== E08: Format Conversion ===\n");

        // --- Create source data ---
        List<Product> products = Arrays.asList(
            new Product("Laptop",       999.99,  true,  LocalDate.of(2024, 3, 15), "Electronics"),
            new Product("Headphones",    79.50,  true,  LocalDate.of(2024, 1, 10), "Electronics"),
            new Product("Desk Lamp",     34.99,  false, LocalDate.of(2023, 11, 1), "Home"),
            new Product("Notebook",       5.99,  true,  LocalDate.of(2024, 6, 20), "Office"),
            new Product("Water Bottle",  12.00,  true,  LocalDate.of(2024, 2, 28), "Sports")
        );

        // Write the source XLSX
        String xlsxPath = "output/source.xlsx";
        Sheetz.write(products, xlsxPath);
        System.out.println("Created source: " + xlsxPath + " (" + products.size() + " rows)");

        // --- XLSX → CSV ---
        System.out.println("\n--- XLSX → CSV ---");
        List<Product> fromXlsx = Sheetz.read(xlsxPath, Product.class);
        String csvPath = "output/converted.csv";
        Sheetz.write(fromXlsx, csvPath);
        System.out.println("Converted " + xlsxPath + " → " + csvPath);

        // --- CSV → XLSX ---
        System.out.println("\n--- CSV → XLSX ---");
        List<Product> fromCsv = Sheetz.read(csvPath, Product.class);
        String newXlsxPath = "output/from_csv.xlsx";
        Sheetz.write(fromCsv, newXlsxPath);
        System.out.println("Converted " + csvPath + " → " + newXlsxPath);

        // --- XLSX → XLS ---
        System.out.println("\n--- XLSX → XLS (legacy format) ---");
        String xlsPath = "output/legacy.xls";
        Sheetz.write(fromXlsx, xlsPath);
        System.out.println("Converted " + xlsxPath + " → " + xlsPath);

        // --- Verify all formats contain the same data ---
        System.out.println("\n--- Verification: all formats contain same data ---");
        List<Product> verifyXlsx = Sheetz.read(newXlsxPath, Product.class);
        List<Product> verifyCsv  = Sheetz.read(csvPath, Product.class);
        List<Product> verifyXls  = Sheetz.read(xlsPath, Product.class);

        System.out.println("XLSX rows: " + verifyXlsx.size());
        System.out.println("CSV  rows: " + verifyCsv.size());
        System.out.println("XLS  rows: " + verifyXls.size());

        System.out.println("\nFirst product from each format:");
        System.out.println("  XLSX: " + verifyXlsx.get(0));
        System.out.println("  CSV:  " + verifyCsv.get(0));
        System.out.println("  XLS:  " + verifyXls.get(0));

        System.out.println("\nDone!");
    }
}

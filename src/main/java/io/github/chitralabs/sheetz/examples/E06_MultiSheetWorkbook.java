package io.github.chitralabs.sheetz.examples;

import io.github.chitralabs.sheetz.Sheetz;
import io.github.chitralabs.sheetz.examples.model.Employee;
import io.github.chitralabs.sheetz.examples.model.Product;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * E06 — Multi-Sheet Workbook
 *
 * Demonstrates creating Excel workbooks with multiple sheets using WorkbookBuilder:
 * - Sheetz.workbook().sheet("name", data).sheet("name2", data2).write(path)
 * - Different model types in each sheet
 * - Reading back from a specific sheet
 */
public class E06_MultiSheetWorkbook {

    public static void main(String[] args) {
        System.out.println("=== E06: Multi-Sheet Workbook ===\n");

        // --- Prepare data for two sheets ---
        List<Product> products = Arrays.asList(
            new Product("Laptop",       999.99, true,  LocalDate.of(2024, 3, 15), "Electronics"),
            new Product("Headphones",    79.50, true,  LocalDate.of(2024, 1, 10), "Electronics"),
            new Product("Desk Lamp",     34.99, false, LocalDate.of(2023, 11, 1), "Home"),
            new Product("Notebook",       5.99, true,  LocalDate.of(2024, 6, 20), "Office")
        );

        List<Employee> employees = Arrays.asList(
            new Employee("Alice Johnson", "Engineering", 95000.0, LocalDate.of(2020, 6, 15)),
            new Employee("Bob Smith",     "Marketing",   72000.0, LocalDate.of(2021, 3, 1)),
            new Employee("Carol White",   "Engineering", 88000.0, LocalDate.of(2019, 9, 10)),
            new Employee("Dave Brown",    "Sales",       65000.0, LocalDate.of(2022, 1, 20)),
            new Employee("Eve Davis",     "Engineering", 102000.0, LocalDate.of(2018, 4, 5))
        );

        // --- Create multi-sheet workbook ---
        String path = "output/report.xlsx";
        Sheetz.workbook()
              .sheet("Products", products)
              .sheet("Employees", employees)
              .write(path);
        System.out.println("Created multi-sheet workbook: " + path);
        System.out.println("  Sheet 1: 'Products'  (" + products.size() + " rows)");
        System.out.println("  Sheet 2: 'Employees' (" + employees.size() + " rows)");

        // --- Read back from each sheet ---
        System.out.println("\n--- Reading 'Products' sheet ---");
        List<Product> readProducts = Sheetz.reader(Product.class)
                .file(path)
                .sheet("Products")
                .read();
        readProducts.forEach(p -> System.out.println("  " + p));

        System.out.println("\n--- Reading 'Employees' sheet ---");
        List<Employee> readEmployees = Sheetz.reader(Employee.class)
                .file(path)
                .sheet("Employees")
                .read();
        readEmployees.forEach(e -> System.out.println("  " + e));

        // --- Read by sheet index ---
        System.out.println("\n--- Reading sheet at index 1 (Employees) ---");
        List<Employee> byIndex = Sheetz.reader(Employee.class)
                .file(path)
                .sheet(1)
                .read();
        System.out.println("Read " + byIndex.size() + " employees from sheet index 1");

        System.out.println("\nDone!");
    }
}

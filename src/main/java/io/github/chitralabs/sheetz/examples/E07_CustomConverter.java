package io.github.chitralabs.sheetz.examples;

import io.github.chitralabs.sheetz.Sheetz;
import io.github.chitralabs.sheetz.examples.converter.MoneyConverter;
import io.github.chitralabs.sheetz.examples.model.Order;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * E07 — Custom Converter
 *
 * Demonstrates custom type converters:
 * - Using @Column(converter = MoneyConverter.class) for per-field conversion
 * - Registering a converter globally with Sheetz.register()
 * - Round-tripping data through write and read with custom formatting
 */
public class E07_CustomConverter {

    public static void main(String[] args) {
        System.out.println("=== E07: Custom Converter ===\n");

        // --- Create orders with monetary amounts ---
        List<Order> orders = Arrays.asList(
            new Order("ORD-001", "Laptop",       1, new BigDecimal("999.99")),
            new Order("ORD-002", "Headphones",   3, new BigDecimal("238.50")),
            new Order("ORD-003", "Desk Lamp",    2, new BigDecimal("69.98")),
            new Order("ORD-004", "Office Chair", 1, new BigDecimal("1249.00")),
            new Order("ORD-005", "Monitor",      2, new BigDecimal("598.00"))
        );

        // --- Write (MoneyConverter formats BigDecimal as "$1,249.00") ---
        String path = "output/orders.xlsx";
        Sheetz.write(orders, path);
        System.out.println("Wrote orders to " + path);
        System.out.println("The 'Amount' column uses MoneyConverter to write values like '$1,249.00'\n");

        // --- Read back (MoneyConverter parses "$1,249.00" back to BigDecimal) ---
        System.out.println("--- Reading back orders ---");
        List<Order> loaded = Sheetz.read(path, Order.class);
        for (Order o : loaded) {
            System.out.println("  " + o);
        }

        // --- Show the MoneyConverter in action ---
        System.out.println("\n--- MoneyConverter details ---");
        MoneyConverter converter = new MoneyConverter();
        System.out.println("  toCell(999.99)     → " + converter.toCell(new BigDecimal("999.99")));
        System.out.println("  toCell(1249.00)    → " + converter.toCell(new BigDecimal("1249.00")));
        System.out.println("  fromCell(\"$1,249.00\") → " + converter.fromCell("$1,249.00", null));
        System.out.println("  fromCell(\"999.99\")    → " + converter.fromCell("999.99", null));

        // --- Global registration alternative ---
        System.out.println("\n--- Global converter registration ---");
        Sheetz.register(BigDecimal.class, new MoneyConverter());
        System.out.println("Registered MoneyConverter globally for BigDecimal");
        System.out.println("Now ALL BigDecimal fields will use MoneyConverter, not just @Column(converter=...) ones");

        // Reset to defaults
        Sheetz.reset();
        System.out.println("Reset converters to defaults");

        System.out.println("\nDone!");
    }
}

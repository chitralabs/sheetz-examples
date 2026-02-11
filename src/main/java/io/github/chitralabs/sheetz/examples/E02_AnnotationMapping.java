package io.github.chitralabs.sheetz.examples;

import io.github.chitralabs.sheetz.Sheetz;
import io.github.chitralabs.sheetz.annotation.Column;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * E02 — Annotation Mapping
 *
 * Demonstrates all @Column annotation attributes:
 * - value:        custom header name
 * - index:        explicit column index (0-based)
 * - required:     fail validation if empty
 * - defaultValue: fallback for empty cells
 * - format:       date/number format pattern
 * - ignore:       skip field during read/write
 * - width:        column width in characters (Excel)
 */
public class E02_AnnotationMapping {

    /**
     * Annotated model showing every @Column attribute.
     */
    public static class Invoice {

        @Column(value = "Invoice #", index = 0, required = true, width = 15)
        private String invoiceNumber;

        @Column(value = "Client", required = true, width = 25)
        private String client;

        @Column(value = "Amount", width = 12)
        private Double amount;

        @Column(value = "Due Date", format = "MM/dd/yyyy", width = 14)
        private LocalDate dueDate;

        @Column(value = "Status", defaultValue = "Pending", width = 12)
        private String status;

        @Column(value = "Priority", index = 5, defaultValue = "Normal")
        private String priority;

        @Column(ignore = true)
        private String secretKey;

        public Invoice() {}

        public Invoice(String invoiceNumber, String client, Double amount,
                       LocalDate dueDate, String status, String priority) {
            this.invoiceNumber = invoiceNumber;
            this.client = client;
            this.amount = amount;
            this.dueDate = dueDate;
            this.status = status;
            this.priority = priority;
            this.secretKey = "INTERNAL-" + invoiceNumber;
        }

        @Override
        public String toString() {
            return String.format("Invoice{#%s, client='%s', amount=%.2f, due=%s, status='%s', priority='%s', secret=%s}",
                    invoiceNumber, client, amount, dueDate, status, priority, secretKey);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== E02: Annotation Mapping ===\n");

        List<Invoice> invoices = Arrays.asList(
            new Invoice("INV-001", "Acme Corp",   1500.00, LocalDate.of(2024, 6, 15), "Paid",    "High"),
            new Invoice("INV-002", "Globex Inc",   750.50, LocalDate.of(2024, 7, 1),  "Pending", "Normal"),
            new Invoice("INV-003", "Initech",     3200.00, LocalDate.of(2024, 7, 30), null,      null)
        );

        String path = "output/invoices.xlsx";
        Sheetz.write(invoices, path);
        System.out.println("Wrote invoices to " + path);
        System.out.println("Note: 'secretKey' field is @Column(ignore=true) — it won't appear in the file.\n");

        // Read back
        System.out.println("--- Reading back from file ---");
        List<Invoice> loaded = Sheetz.read(path, Invoice.class);
        for (Invoice inv : loaded) {
            System.out.println(inv);
        }

        System.out.println("\nNotice:");
        System.out.println("  - 'secretKey' is null (ignored during read/write)");
        System.out.println("  - INV-003 status = 'Pending' (from defaultValue)");
        System.out.println("  - INV-003 priority = 'Normal' (from defaultValue)");

        System.out.println("\nDone!");
    }
}

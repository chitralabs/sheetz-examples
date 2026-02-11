package io.github.chitralabs.sheetz.examples;

import io.github.chitralabs.sheetz.Sheetz;
import io.github.chitralabs.sheetz.examples.model.Product;
import io.github.chitralabs.sheetz.reader.StreamingReader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * E03 — Streaming Large Files
 *
 * Demonstrates memory-efficient processing of large files:
 * - Generate a large CSV file (100K+ rows)
 * - Stream-read with constant memory using Sheetz.stream()
 * - Batch processing with StreamingReader.batch()
 * - Memory statistics before and after
 */
public class E03_StreamingLargeFiles {

    private static final int ROW_COUNT = 100_000;
    private static final String[] CATEGORIES = {"Electronics", "Home", "Office", "Sports", "Books"};

    public static void main(String[] args) {
        System.out.println("=== E03: Streaming Large Files ===\n");

        String csvPath = "output/large_products.csv";

        // --- Step 1: Generate a large CSV file ---
        System.out.println("Generating " + ROW_COUNT + " rows...");
        long genStart = System.currentTimeMillis();

        List<Product> data = new ArrayList<>(ROW_COUNT);
        for (int i = 1; i <= ROW_COUNT; i++) {
            data.add(new Product(
                "Product-" + i,
                10.0 + (i % 500),
                i % 3 != 0,
                LocalDate.of(2024, 1, 1).plusDays(i % 365),
                CATEGORIES[i % CATEGORIES.length]
            ));
        }

        Sheetz.write(data, csvPath);

        long genTime = System.currentTimeMillis() - genStart;
        System.out.printf("Generated %s in %,d ms%n%n", csvPath, genTime);

        // Free the generation list
        data = null;
        System.gc();

        // --- Step 2: Stream-read with constant memory ---
        System.out.println("--- Streaming read (row-by-row) ---");
        printMemory("Before streaming");

        long readStart = System.currentTimeMillis();
        long count = 0;
        double totalPrice = 0;

        try (StreamingReader<Product> reader = Sheetz.stream(csvPath, Product.class)) {
            for (Product p : reader) {
                count++;
                totalPrice += p.getPrice();
            }
        }

        long readTime = System.currentTimeMillis() - readStart;
        printMemory("After streaming");
        System.out.printf("Processed %,d rows in %,d ms%n", count, readTime);
        System.out.printf("Average price: $%.2f%n%n", totalPrice / count);

        // --- Step 3: Batch processing ---
        System.out.println("--- Batch processing (1,000 rows at a time) ---");
        long batchStart = System.currentTimeMillis();
        long[] batchCount = {0};
        long[] batchRows = {0};

        try (StreamingReader<Product> reader = Sheetz.stream(csvPath, Product.class)) {
            reader.batch(1000).forEach(batch -> {
                batchCount[0]++;
                batchRows[0] += batch.size();
                // Simulate processing each batch (e.g., database insert)
            });
        }

        long batchTime = System.currentTimeMillis() - batchStart;
        System.out.printf("Processed %,d rows in %,d batches (%,d ms)%n",
                batchRows[0], batchCount[0], batchTime);

        // --- Step 4: Stream API (Java Streams) ---
        System.out.println("\n--- Java Stream API ---");
        try (StreamingReader<Product> reader = Sheetz.stream(csvPath, Product.class)) {
            long electronicsCount = reader.stream()
                    .filter(p -> "Electronics".equals(p.getCategory()))
                    .count();
            System.out.printf("Electronics products: %,d%n", electronicsCount);
        }

        System.out.println("\nDone!");
    }

    private static void printMemory(String label) {
        Runtime rt = Runtime.getRuntime();
        long used = (rt.totalMemory() - rt.freeMemory()) / (1024 * 1024);
        long max = rt.maxMemory() / (1024 * 1024);
        System.out.printf("  [%s] Memory: %,d MB used / %,d MB max%n", label, used, max);
    }
}

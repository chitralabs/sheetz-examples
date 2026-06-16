package io.github.chitralabs.sheetz.examples;

import io.github.chitralabs.sheetz.Sheetz;
import io.github.chitralabs.sheetz.annotation.Column;
import io.github.chitralabs.sheetz.annotation.Style;
import io.github.chitralabs.sheetz.style.CellStyleBuilder;
import io.github.chitralabs.sheetz.style.CellStyleDef;
import io.github.chitralabs.sheetz.style.HyperlinkValue;

import java.util.List;

/**
 * E09: Cell Styling — @Style annotation, programmatic styles, and hyperlinks.
 *
 * Demonstrates the cell formatting API added in Sheetz 1.0.2.
 */
public class E09_CellStyling {

    // --- Model with @Style annotation ---
    public static class StyledProduct {
        @Column("Product Name")
        @Style(bold = true, fontColor = "#0000FF")
        public String name;

        @Column("Price")
        @Style(backgroundColor = "#FFFF00", horizontalAlignment = "CENTER", dataFormat = "#,##0.00")
        public Double price;

        @Column("Website")
        @Style(hyperlink = true)
        public HyperlinkValue website;

        public StyledProduct() {}

        public StyledProduct(String name, Double price, String url) {
            this.name = name;
            this.price = price;
            this.website = new HyperlinkValue(name + " site", url);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== E09: Cell Styling ===\n");

        // 1. Write with @Style annotations
        List<StyledProduct> products = List.of(
            new StyledProduct("Widget", 29.99, "https://example.com/widget"),
            new StyledProduct("Gadget", 49.99, "https://example.com/gadget"),
            new StyledProduct("Gizmo", 19.99, "https://example.com/gizmo")
        );

        Sheetz.write(products, "styled-products.xlsx");
        System.out.println("Wrote styled-products.xlsx with @Style annotations");

        // 2. Programmatic header style with CellStyleBuilder
        CellStyleDef headerStyle = CellStyleBuilder.create()
            .bold(true)
            .backgroundColor("#003366")
            .fontColor("#FFFFFF")
            .horizontalAlignment("CENTER")
            .build();

        Sheetz.writer(StyledProduct.class)
            .data(products)
            .file("styled-with-header.xlsx")
            .headerStyle(headerStyle)
            .autoFilter(true)
            .write();

        System.out.println("Wrote styled-with-header.xlsx with programmatic header style and auto-filter");

        // 3. Read back hyperlinks
        List<StyledProduct> readBack = Sheetz.read("styled-products.xlsx", StyledProduct.class);
        for (StyledProduct p : readBack) {
            System.out.printf("  %s — $%.2f%n", p.name, p.price);
        }

        System.out.println("\nDone!");
    }
}

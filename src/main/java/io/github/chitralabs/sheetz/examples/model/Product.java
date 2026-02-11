package io.github.chitralabs.sheetz.examples.model;

import io.github.chitralabs.sheetz.annotation.Column;

import java.time.LocalDate;

/**
 * Product model demonstrating @Column annotation features.
 */
public class Product {

    @Column(value = "Product Name", required = true, width = 25)
    private String name;

    @Column(value = "Price", format = "#,##0.00")
    private Double price;

    @Column(value = "In Stock")
    private Boolean inStock;

    @Column(value = "Release Date", format = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @Column(value = "Category", defaultValue = "General")
    private String category;

    @Column(ignore = true)
    private String internalNotes;

    public Product() {}

    public Product(String name, Double price, Boolean inStock, LocalDate releaseDate, String category) {
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.releaseDate = releaseDate;
        this.category = category;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Boolean getInStock() { return inStock; }
    public void setInStock(Boolean inStock) { this.inStock = inStock; }

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getInternalNotes() { return internalNotes; }
    public void setInternalNotes(String internalNotes) { this.internalNotes = internalNotes; }

    @Override
    public String toString() {
        return String.format("Product{name='%s', price=%.2f, inStock=%s, releaseDate=%s, category='%s'}",
                name, price, inStock, releaseDate, category);
    }
}

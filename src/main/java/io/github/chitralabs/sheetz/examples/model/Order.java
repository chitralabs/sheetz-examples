package io.github.chitralabs.sheetz.examples.model;

import io.github.chitralabs.sheetz.annotation.Column;
import io.github.chitralabs.sheetz.examples.converter.MoneyConverter;

import java.math.BigDecimal;

/**
 * Order model demonstrating custom converter usage.
 */
public class Order {

    @Column(value = "Order ID", required = true)
    private String orderId;

    @Column(value = "Product")
    private String product;

    @Column(value = "Quantity")
    private Integer quantity;

    @Column(value = "Amount", converter = MoneyConverter.class)
    private BigDecimal amount;

    public Order() {}

    public Order(String orderId, String product, Integer quantity, BigDecimal amount) {
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.amount = amount;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    @Override
    public String toString() {
        return String.format("Order{orderId='%s', product='%s', quantity=%d, amount=$%s}",
                orderId, product, quantity, amount);
    }
}

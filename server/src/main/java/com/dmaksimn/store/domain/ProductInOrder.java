package com.dmaksimn.store.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "product_in_order")
@Getter
@Setter
@RequiredArgsConstructor
public class ProductInOrder implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private long user_id;

    @NotNull
    @Column(name = "product_id")
    private long product_id;

    @Column(name = "form_pay")
    private int form_pay;

    @Column(name = "status")
    private int status;

    @NotNull
    @Column(name = "count")
    private Integer count;

    @NotNull
    @Column(name = "order_main_id")
    private Long order_main_id;

    @NotNull
    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    @ToString.Exclude
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_main_id", insertable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    private OrderMain orderMain;

    public ProductInOrder(ProductInfo productInfo, Integer quantity) {
        this.id = productInfo.getId();
        this.status = productInfo.getStatus();
        this.count = quantity;
        this.price = productInfo.getPrice();
    }

    @Override
    public String toString() {
        return "ProductInOrder{" +
                "id=" + id +
                ", status=" + status +
                ", count=" + count +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInOrder that = (ProductInOrder) o;
        return product_id == that.product_id
                && id.equals(that.id) && price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, product_id, price, form_pay, status, count, user_id, order_main_id);
    }
}

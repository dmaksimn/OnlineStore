package com.dmaksimn.store.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * OrderMain contains User info and products in the order
 */
@Entity
@Table(name = "order_main")
@Data
@ToString
@RequiredArgsConstructor
@DynamicUpdate
public class OrderMain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private long user_id;

    @NotNull
    @Column(name = "product_in_order_id")
    private long product_in_order;

    /**
     * default 0: new order.
     */
    @NotNull
    @ColumnDefault("0")
    private Integer status;

    @NotNull
    @Column(name = "create")
    @CreationTimestamp
    private LocalDateTime create;

    @NotNull
    @Column(name = "update")
    @UpdateTimestamp
    private LocalDateTime update;

    /**
     * Total Amount
     */
    @NotNull
    @Column(name = "order_amount")
    private BigDecimal amount;

    @NotNull
    @Column(name = "address_delivery")
    private String addressDelivery;

    @NotNull
    @Column(name = "client_email")
    private String email;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "orderMain")
    @ToString.Exclude
    private Set<ProductInOrder> products = new HashSet<>();

    public OrderMain(User client) {
        this.id = client.getId();
        this.email = client.getEmail();
        this.addressDelivery = client.getAddress();
        this.amount = client.getCart().getProducts()
                .stream().map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getCount())))
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal(0));
        this.status = 0;
    }
}

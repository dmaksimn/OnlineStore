package com.dmaksimn.store.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cart")
@Getter
@Setter
@RequiredArgsConstructor
public class Cart implements Serializable {

    @Id
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true,
            mappedBy = "cart")
    @ToString.Exclude
    private Set<ProductInOrder> products = new HashSet<>();

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column(name = "product_id")
    private long product_id;

    @NotNull
    @Column(name = "count")
    private int count;

    @NotNull
    @Column(name = "create")
    @CreationTimestamp
    private LocalDateTime create;

    @NotNull
    @Column(name = "update")
    @CreationTimestamp
    private LocalDateTime update;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "status")
    private Integer status;

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", products=" + products +
                '}';
    }

    public Cart(User user) {
        this.user  = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Cart cart = (Cart) o;
        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

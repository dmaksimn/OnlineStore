package com.dmaksimn.store.domain;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "product_info")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@DynamicUpdate
public class ProductInfo implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "category_id")
    private Integer categoryType;

    @NotNull
    @Min(0)
    @Column(name = "stock")
    private Integer stock;

    @Column(name = "description")
    private String description;

    @Column(name = "icon")
    private String icon;
    /**
     * 0: on-sale 1: off-sale
     */
    @Column(name = "status")
    @ColumnDefault("0")
    private Integer status;

    @Column(name = "create")
    @CreationTimestamp
    private Date create;

    @Column(name = "update")
    @UpdateTimestamp
    private Date update;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProductInfo that = (ProductInfo) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

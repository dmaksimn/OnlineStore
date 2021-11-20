package com.dmaksimn.store.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "product_category")
@Data
@DynamicUpdate
public class ProductCategory implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NaturalId
    @Column(name = "type")
    private Integer type;

    @NotNull
    @Column(name = "create")
    private Date create;

    @NotNull
    @Column(name = "update")
    private Date update;

    public ProductCategory(String name, Integer type) {
        this.name = name;
        this.type = type;
    }

    public ProductCategory() {

    }
}

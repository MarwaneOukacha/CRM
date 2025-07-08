package com.crm.product.entities;

import com.crm.product.entities.model.AbstractEntity;
import com.crm.product.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "designer")
@AttributeOverride(name = "id", column = @Column(name = "designer_id"))
@Getter
@Setter
public class Designer extends AbstractEntity {

    private String name;
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "designer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductDesigner> productDesigner = new ArrayList<>();

    // Getters and setters
}

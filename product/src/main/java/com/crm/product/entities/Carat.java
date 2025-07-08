package com.crm.product.entities;

import com.crm.product.entities.model.AbstractEntity;
import com.crm.product.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "carat")
@Data
@AttributeOverride(name = "id", column = @Column(name = "carat_id"))
public class Carat extends AbstractEntity {

    private String name;
    @Enumerated(EnumType.STRING)
    private Status status;

}


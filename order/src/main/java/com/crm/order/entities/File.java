package com.crm.order.entities;

import com.crm.order.entities.model.AbstractEntity;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class File extends AbstractEntity {
    private String name;
    private Double size;
    private String type;
    private String url;
}

package com.crm.product.entities;


import com.crm.product.entities.dto.AbstractDTO;
import com.crm.product.entities.model.AbstractEntity;
import com.crm.product.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AttributeOverride(name = "id", column = @Column(name = "DailySales_id"))
@Data
@Table(name = "DailySales")
public class DailySales extends AbstractEntity {
    private int days;
    private int percent;
    @Enumerated(EnumType.STRING)
    private Status status;

}

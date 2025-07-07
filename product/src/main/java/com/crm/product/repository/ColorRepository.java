package com.crm.product.repository;
import com.crm.product.entities.Color;
import com.crm.product.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ColorRepository extends JpaRepository<Color, UUID>, JpaSpecificationExecutor<Color> {
}

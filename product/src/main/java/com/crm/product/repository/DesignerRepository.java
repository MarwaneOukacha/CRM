package com.crm.product.repository;
import com.crm.product.entities.Designer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DesignerRepository extends JpaRepository<Designer, UUID>, JpaSpecificationExecutor<Designer> {
}

package com.crm.partner.repository;

import com.crm.partner.entities.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PartnerRepository extends JpaRepository<Partner, UUID>, JpaSpecificationExecutor<Partner> {

    Optional<Partner> findByEmail(String contactEmail);

    Optional<Partner> findByCode(String code);
    boolean existsByEmail(String contactEmail);
}


package com.crm.partner.repository;

import com.crm.partner.entities.Payout;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface PayoutRepository extends JpaRepository<Payout, UUID> {

    List<Payout> findByPartnerId(UUID partnerId);
}

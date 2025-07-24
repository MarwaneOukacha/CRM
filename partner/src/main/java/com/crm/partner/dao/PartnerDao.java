package com.crm.partner.dao;

import com.crm.partner.entities.Partner;
import com.crm.partner.entities.dto.SearchPartnerCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PartnerDao {

    Partner save(Partner partner);

    Optional<Partner> findById(String id);

    Optional<Partner> findByContactEmail(String email);

    boolean existsByContactEmail(String email);

    Page<Partner> findAllWithCriteria(SearchPartnerCriteria criteria, Pageable pageable);


    void deleteById(String id);

    Optional<Partner> findByPartnerCode(String partnerCode);

}


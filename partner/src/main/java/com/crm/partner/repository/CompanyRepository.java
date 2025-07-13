package com.crm.partner.repository;

import com.crm.partner.entities.Company;
import com.crm.partner.entities.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CompanyRepository  extends JpaRepository<Company, UUID>, JpaSpecificationExecutor<Company> {
}

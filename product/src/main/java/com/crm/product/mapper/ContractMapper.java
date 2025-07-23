package com.crm.product.mapper;

import com.crm.product.entities.Contract;
import com.crm.product.entities.dto.ContractDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContractMapper {
     Contract toContract(ContractDto contractDto);
}

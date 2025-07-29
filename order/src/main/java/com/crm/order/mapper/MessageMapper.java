package com.crm.order.mapper;

import com.crm.order.entities.Message;
import com.crm.order.entities.dto.MessageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Mapping(source = "order.id", target = "orderId")
    MessageDto toDto(Message message);
}
package com.system.readycrudop.dto;

import com.system.readycrudop.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponseDto toProductResponsedto(ProductEntity entity);

    List<ProductResponseDto> toProductResponsedtoList(List<ProductEntity> product);

    @Mapping(source = "name", target = "ProductName")
    ProductEntity toProductEntity(ProductRequestDto requestDto);
}

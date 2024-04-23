package com.auto.inventory.autoinventory.mappers;

import com.auto.inventory.autoinventory.models.dtos.ProductDTO;
import com.auto.inventory.autoinventory.models.entities.Product;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = { UserMapper.class })
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "entryDate", source = "entryDate")
    @Mapping(target = "registeredBy", source = "registeredBy")
    @Mapping(target = "lastModifiedBy", source = "lastModifiedBy")
    @Mapping(target = "lastModifiedDate", source = "lastModifiedDate")
    ProductDTO productToProductDTO(Product product);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "entryDate", source = "entryDate")
    @Mapping(target = "registeredBy", source = "registeredBy")
    @Mapping(target = "lastModifiedBy", source = "lastModifiedBy")
    @Mapping(target = "lastModifiedDate", source = "lastModifiedDate")
    Product productDTOToProduct(ProductDTO productDTO);

    @IterableMapping(elementTargetType = ProductDTO.class)
    List<ProductDTO> productsToProductsDTO(List<Product> products);

    default Optional<ProductDTO> mapToDtoOptional(Optional<Product> entityOptional) {
        return entityOptional.map(this::productToProductDTO);
    }

}

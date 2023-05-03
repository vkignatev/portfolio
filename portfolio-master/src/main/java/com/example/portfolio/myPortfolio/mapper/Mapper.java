package com.example.portfolio.myPortfolio.mapper;

import com.example.portfolio.myPortfolio.dto.GenericDTO;
import com.example.portfolio.myPortfolio.model.GenericModel;

import java.util.List;

public interface Mapper<E extends GenericModel, D extends GenericDTO> {
    E toEntity(D dto);

    List<E> toEntities(List<D> dtos);

    D toDto(E entity);

    List<D> toDTOs(List<E> entities);

}


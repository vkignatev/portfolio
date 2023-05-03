package com.example.portfolio.myPortfolio.mapper;

import com.example.portfolio.myPortfolio.dto.GenericDTO;
import com.example.portfolio.myPortfolio.model.GenericModel;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Абстрактный маппер, который реализует основные операции конвертации ИЗ СУЩНОСТИ В ДТО
 * и обратно. С помощью этого класса мы фиксируем основные методы по работе с маппером,
 * а так-же определили абстрактные методы, которые описывают правила формирования различающихся полей
 *
 * @param <E> - Сущность с которой мы работаем
 * @param <D> - DTO, которую мы будем отдавать/принимать дальше
 */

@Configuration
public abstract class GenericMapper<E extends GenericModel, D extends GenericDTO>
        implements Mapper<E, D> {
    protected final ModelMapper modelMapper;
    private final Class<E> entityClass;
    private final Class<D> dtoClass;

    protected GenericMapper(ModelMapper mapper,
                            Class<E> entityClass,
                            Class<D> dtoClass) {
        this.modelMapper = mapper;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    @Override
    public E toEntity(D dto) {
        return Objects.isNull(dto)
                ? null
                : modelMapper.map(dto, entityClass);
    }

    @Override
    public List<E> toEntities(List<D> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }

    @Override
    public D toDto(E entity) {
        return Objects.isNull(entity)
                ? null
                : modelMapper.map(entity, dtoClass);
    }

    @Override
    public List<D> toDTOs(List<E> entities) {
        return entities.stream().map(this::toDto).toList();
    }


    Converter<D, E> toEntityConverter() {
        return context -> {
            D source = context.getSource();
            E destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    Converter<E, D> toDtoConverter() {
        return context -> {
            E source = context.getSource();
            D destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    protected abstract void mapSpecificFields(D source, E destination);

    protected abstract void mapSpecificFields(E source, D destination);

    protected abstract Set<Long> getIds(E entity);
}

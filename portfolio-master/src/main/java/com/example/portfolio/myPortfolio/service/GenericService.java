package com.example.portfolio.myPortfolio.service;

import com.example.portfolio.myPortfolio.dto.GenericDTO;
import com.example.portfolio.myPortfolio.mapper.GenericMapper;
import com.example.portfolio.myPortfolio.model.GenericModel;
import com.example.portfolio.myPortfolio.repository.GenericRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Абстрактный сервис который хранит в себе реализацию CRUD операций по умолчанию
 * Если реализация отличная от того что представлено в этом классе,
 * то она переопределяется в сервисе для конкретной сущности
 *
 * @param <T> - Сущность с которой мы работаем
 * @param <N> - DTO, которую мы будем отдавать/принимать дальше
 */

@Service
public abstract class GenericService<T extends GenericModel, N extends GenericDTO> {

    //Инжектим абстрактный репозиторий для работы с базой данных
    protected final GenericRepository<T> repository;
    protected final GenericMapper<T, N> mapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected GenericService(GenericRepository<T> repository,
                             GenericMapper<T, N> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<N> listAll() {
        return mapper.toDTOs(repository.findAll());
    }

    public Page<N> listAll(Pageable pageable) {
        Page<T> objects = repository.findAll(pageable);
        List<N> result = mapper.toDTOs(objects.getContent());
        return new PageImpl<>(result, pageable, objects.getTotalElements());
    }

    public N getOne(Long id) throws ChangeSetPersister.NotFoundException {
        return mapper.toDto(repository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    public N create(N object) {
        return mapper.toDto(repository.save(mapper.toEntity(object)));
    }

    public N update(N object) {
        return mapper.toDto(repository.save(mapper.toEntity(object)));
    }

}

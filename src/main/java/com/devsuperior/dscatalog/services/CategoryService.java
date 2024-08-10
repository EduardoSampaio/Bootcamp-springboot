package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.CategoryDto;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(final CategoryRepository repository) {
        this.repository = repository;
    }

    public Page<CategoryDto> findAllPaged(PageRequest pageRequest) {
        var list = repository.findAll(pageRequest);
        return list.map(x -> new CategoryDto(x.getId(), x.getName()));
    }

    public CategoryDto findById(final Long id) throws EntityNotFoundException {
        Optional<Category> objOpt = repository.findById(id);
        Category entity = objOpt.orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        return new CategoryDto(entity.getId(), entity.getName());
    }

    public CategoryDto save(final CategoryDto categoryDto) {
        Category entity = new Category();
        entity.setName(categoryDto.getName());
        entity = repository.save(entity);
        return new CategoryDto(entity.getId(), entity.getName());
    }

    public void update(final CategoryDto categoryDto) throws EntityNotFoundException {
        Optional<Category> objOpt = repository.findById(categoryDto.getId());
        Category entity = objOpt.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        entity.setName(categoryDto.getName());
        repository.save(entity);
    }

    public void deleteById(final Long id) throws EntityNotFoundException {
        Optional<Category> objOpt = repository.findById(id);
        objOpt.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        repository.deleteById(id);
    }
}

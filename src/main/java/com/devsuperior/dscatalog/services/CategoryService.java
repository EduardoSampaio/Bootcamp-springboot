package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.CategoryDto;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(final CategoryRepository repository) {
        this.repository = repository;
    }

    public List<CategoryDto> findAll() {
        var list = repository.findAll();
        return list.stream().map(x -> new CategoryDto(x.getId(), x.getName()))
                            .toList();
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
}

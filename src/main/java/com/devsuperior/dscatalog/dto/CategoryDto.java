package com.devsuperior.dscatalog.dto;


import com.devsuperior.dscatalog.entities.Category;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class CategoryDto implements Serializable {
    private Long id;
    private String name;

    public CategoryDto() {
    }

    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDto(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}

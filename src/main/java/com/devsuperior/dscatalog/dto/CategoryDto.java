package com.devsuperior.dscatalog.dto;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto implements Serializable {
    private Long id;
    private String name;
}

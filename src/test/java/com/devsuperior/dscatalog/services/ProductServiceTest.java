package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository repository;

    private long existingId;
    private long nonExistingId;

    private PageImpl<Product> page;
    private Product product;

    @BeforeEach
    void setUp()  {
        existingId = 1L;
        nonExistingId = -1000L;
        product = new Product();
        page = new PageImpl<>(List.of(product));
        when(repository.findAll((Pageable)any())).thenReturn(page);


        doNothing().when(repository).deleteById(existingId);
        when(repository.existsById(nonExistingId)).thenReturn(false);
        when(repository.existsById(existingId)).thenReturn(true);

        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
        when(repository.findById(existingId)).thenReturn(Optional.of(new Product()));
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        assertDoesNotThrow(() ->{
            productService.delete(existingId);
        });

        verify(repository, times(1)).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowExceptionWhenNotIdExists(){
        assertThrows(ResourceNotFoundException.class, () ->{
            productService.delete(nonExistingId);
        });

        verify(repository, times(1)).existsById(nonExistingId);
    }

    @Test
    public void findByIdShouldThrowExceptionWhenNotIdExists(){
        assertThrows(ResourceNotFoundException.class, () ->{
            productService.findById(nonExistingId);
        });

        verify(repository, times(1)).findById(nonExistingId);
    }

    @Test
    public void findByIdShouldWhenIdExists(){
        assertDoesNotThrow(() ->{
            productService.findById(existingId);
        });
    }
}

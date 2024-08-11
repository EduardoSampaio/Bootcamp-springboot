package com.devsuperior.dscatalog.repositories;

import com.devsuperior.dscatalog.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Profile("test")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Test
    public void deleteShouldObjectWhenIdExists() {
        repository.deleteById(1L);

        Optional<Product> productOpt = repository.findById(1L);
        assertFalse(productOpt.isPresent());
    }

    @Test
    public void deleteShould_ThrowInvalidDataAccessApiUsageException_When_IdNotExists() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            repository.deleteById(null);
        });
    }

    @Test
    public void shouldSaveProductAndReturnIdNotNull(){
        Product product = new Product();
        product.setName("Teste");
        product.setDate(Instant.now());
        product.setPrice(55.50);
        product.setImgUrl("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/18-big.jpg");

        product = repository.save(product);
        assertNotNull(product.getId());
    }
}

package com.auto.inventory.autoinventory.repositories;

import com.auto.inventory.autoinventory.models.entities.Product;
import com.auto.inventory.autoinventory.models.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);

    @Query("SELECT p FROM Product p ORDER BY "
            + "CASE WHEN :orderBy = 'name' THEN p.name "
            + "WHEN :orderBy = 'userName' THEN p.registeredBy.name "
            + "ELSE NULL END "
            + "DESC NULLS LAST")
    List<Product> findAll(@Param("orderBy") String orderBy);



    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.registeredBy.id = :userId")
    Optional<Product> findByIdAndRegisteredBy(@Param("id") Long id, @Param("userId") Long userId);

    Product save(Product product);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.name = :name, p.quantity = :quantity, p.entryDate = :entryDate, p.registeredBy = :registeredBy, p.lastModifiedBy = :lastModifiedBy, p.lastModifiedDate = :lastModifiedDate WHERE p.id = :id")
    void updateProduct(@Param("id") Long id,
                       @Param("name") String name,
                       @Param("quantity") int quantity,
                       @Param("entryDate") LocalDate entryDate,
                       @Param("registeredBy") User registeredBy,
                       @Param("lastModifiedBy") User lastModifiedBy,
                       @Param("lastModifiedDate") LocalDate lastModifiedDate);

    void deleteById(Long id);

}

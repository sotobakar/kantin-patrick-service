package com.sotobakar.kantinpatrickservice.repository;

import com.sotobakar.kantinpatrickservice.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("select c from Cart c where c.user.id = ?1")
    Cart findByUserId(@Param(":userId") Long id);
}

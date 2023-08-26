package com.ecomm.repository;

import com.ecomm.model.Cart;
import com.ecomm.model.CartItem;
import com.ecomm.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    @Query("select ci from CartItem ci where ci.cart=:cart AND ci.product=:product AND ci.size=:size AND ci.userId=:userId")
    public CartItem isCartItemExist(@Param("cart") Cart cart, @Param("product")Product product,
                                    @Param("size") String size, @Param("userId") Long userId);
}

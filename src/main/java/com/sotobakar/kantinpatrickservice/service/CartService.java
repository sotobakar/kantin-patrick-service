package com.sotobakar.kantinpatrickservice.service;

import com.sotobakar.kantinpatrickservice.dto.request.UpdateItemInCartRequest;
import com.sotobakar.kantinpatrickservice.exception.NotFoundException;
import com.sotobakar.kantinpatrickservice.model.Cart;
import com.sotobakar.kantinpatrickservice.model.CartItem;
import com.sotobakar.kantinpatrickservice.model.Menu;
import com.sotobakar.kantinpatrickservice.model.User;
import com.sotobakar.kantinpatrickservice.repository.CartRepository;
import com.sotobakar.kantinpatrickservice.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CartService {

    private final CartRepository cartRepository;

    private final MenuRepository menuRepository;

    @Autowired
    public CartService(CartRepository cartRepository, MenuRepository menuRepository) {
        this.cartRepository = cartRepository;
        this.menuRepository = menuRepository;
    }

    public Cart get(User user) {
        var cart = this.cartRepository.findByUserId(user.getId());

        if (cart != null) {
            return cart;
        } else {
            cart = new Cart();
            cart.setUser(user);
            return this.cartRepository.save(cart);
        }
    }

    public Cart updateItem(UpdateItemInCartRequest cartData, Cart cart) {
        // Check if menu exist in cart
        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getMenu().getId().equals(cartData.getMenuId()))
                .findFirst()
                .orElse(null);

        // If it does not exist then add as cart item with quantity
        if (cartItem == null) {
            Menu menu = this.menuRepository.findById(cartData.getMenuId()).orElse(null);
            if (menu == null) {
                throw new NotFoundException("Menu");
            }

            if (cartData.getQuantity() > 0) {
                CartItem newCartItem = new CartItem();
                newCartItem.setMenu(menu);
                newCartItem.setQuantity(cartData.getQuantity());
                newCartItem.setCart(cart);
                cart.getCartItems().add(newCartItem);
            }
        } else {
            // If it exists, calculate quantity equal to 0 then remove from cart
            if (cartData.getQuantity() == 0) {
                cart.getCartItems().remove(cartItem);
            } else {
                cartItem.setQuantity(cartData.getQuantity());
            }
        }

        return this.cartRepository.save(cart);
    }
}

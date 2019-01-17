package com.shoppingcart.app.service;


import com.shoppingcart.app.entity.Cart;
import com.shoppingcart.app.entity.Product;
import com.shoppingcart.app.exception.CartNotFoundException;
import com.shoppingcart.app.exception.ProductNotFoundException;
import org.springframework.http.ResponseEntity;

public interface ICartService {


    /**
     *
     * @param id
     * @return
     */
    Cart retrieveCartById(Long id)  throws CartNotFoundException;


    /**
     *
     * @param cart
     */
    Cart createCart(Cart cart);


    /**
     *
     * @param cartId
     * @param product
     */
    Cart addProduct(String cartId, Product product) throws ProductNotFoundException, CartNotFoundException;


}

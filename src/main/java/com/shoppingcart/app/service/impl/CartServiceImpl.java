package com.shoppingcart.app.service.impl;

import com.shoppingcart.app.constants.ShoppingCartConstants;
import com.shoppingcart.app.entity.Cart;
import com.shoppingcart.app.entity.Product;
import com.shoppingcart.app.exception.CartNotFoundException;
import com.shoppingcart.app.exception.EmptyCartException;
import com.shoppingcart.app.exception.ProductNotFoundException;
import com.shoppingcart.app.repository.CartRepository;
import com.shoppingcart.app.repository.ProductRepository;
import com.shoppingcart.app.service.ICartService;
import com.shoppingcart.app.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IProductService productService;

    @Override
    public Cart retrieveCartById(Long id) throws CartNotFoundException{
        return cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(ShoppingCartConstants.CART_NOT_FOUND_ERROR_MESSAGE));
    }

    @Override
    public Cart createCart() {
        Cart newCart = new Cart();
        return cartRepository.save(newCart);
    }

    @Override
    @Transactional
    public Cart addProductToCart(String cartId, Product product) throws ProductNotFoundException, CartNotFoundException{

        Product searchedProduct = productService.getProductById(product.getId());
        Cart cart = retrieveCartById(Long.parseLong(cartId));

        if (searchedProduct != null) {
            cart.getProducts().add(searchedProduct);
            cartRepository.save(cart);
        } else {
            throw new ProductNotFoundException(ShoppingCartConstants.PRODUCT_NOT_FOUND_ERROR_MESSAGE);
        }

        return cart;
    }


    @Override
    @Transactional
    public Cart removeProductFromCart(String cartId, Product product) throws ProductNotFoundException, CartNotFoundException{

        Cart cart = retrieveCartById(Long.parseLong(cartId));
        productRepository.delete(product);
        cart.getProducts().removeIf(product1 -> product.equals(product));

        return cart;
    }



}
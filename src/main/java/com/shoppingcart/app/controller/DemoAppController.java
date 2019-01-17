package com.shoppingcart.app.controller;


import com.shoppingcart.app.entity.Cart;
import com.shoppingcart.app.entity.Product;
import com.shoppingcart.app.exception.CartNotFoundException;
import com.shoppingcart.app.exception.ProductNotFoundException;
import com.shoppingcart.app.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class DemoAppController {

    @Autowired
    private ICartService cartService;

    @RequestMapping(method=RequestMethod.GET,value="/carts/{cartId}")
    public ResponseEntity<Cart> retrieveCartById(@PathVariable Long cartId){

        try {

            Cart cart = cartService.retrieveCartById(cartId);
            return new ResponseEntity<>(cart, HttpStatus.OK);

        }catch(CartNotFoundException cartEx){
            System.err.println(cartEx.getMessage());
            return new ResponseEntity<> (getDummyCart(String.valueOf(cartId)),HttpStatus.BAD_REQUEST);
        }


    }

    @RequestMapping(method=RequestMethod.POST, value="/carts")
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        Cart persistedCart = cartService.createCart(cart);

        if(null != persistedCart.getId()){
            return new ResponseEntity<> (persistedCart,HttpStatus.OK);
        }else{
            return new ResponseEntity<> (persistedCart,HttpStatus.BAD_REQUEST);
        }


    }


    @RequestMapping(method=RequestMethod.POST, value="/carts/{cartId}/products",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cart> addProductsToCart(@PathVariable(name="cartId") String cartId, @RequestBody Product product) {
        try {
            Cart persistedCart = cartService.addProduct(cartId, product);
            return new ResponseEntity<> (persistedCart,HttpStatus.OK);
        } catch (ProductNotFoundException prodEx) {
            System.err.println(prodEx.getMessage());
            return new ResponseEntity<>(getDummyCart(cartId), HttpStatus.BAD_REQUEST);
        }
        catch(CartNotFoundException cartEx){
            System.err.println(cartEx.getMessage());
            return new ResponseEntity<>(getDummyCart(cartId), HttpStatus.BAD_REQUEST);
        }

    }

    private Cart getDummyCart(String cartId){

        Cart cart = new Cart();
        cart.setId(Long.parseLong(cartId));
        cart.setProducts(new ArrayList<>());
        return cart;
    }

}

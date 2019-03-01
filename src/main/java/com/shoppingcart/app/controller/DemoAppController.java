package com.shoppingcart.app.controller;


import com.shoppingcart.app.entity.Cart;
import com.shoppingcart.app.entity.Category;
import com.shoppingcart.app.entity.Product;
import com.shoppingcart.app.exception.CartNotFoundException;
import com.shoppingcart.app.exception.CategoryNotFoundException;
import com.shoppingcart.app.exception.EmptyCartException;
import com.shoppingcart.app.exception.ProductNotFoundException;
import com.shoppingcart.app.service.ICartService;
import com.shoppingcart.app.service.ICategoryService;
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
import java.util.List;

@RestController
public class DemoAppController {

    @Autowired
    private ICartService cartService;

    @Autowired
    private ICategoryService categoryService;

    @RequestMapping(method=RequestMethod.GET,value="/carts/{cartId}")
    public ResponseEntity<Cart> retrieveCartById(@PathVariable Long cartId){

        try {
            Cart cart = cartService.retrieveCartById(cartId);
            return new ResponseEntity<>(cart, HttpStatus.OK);

        }catch(CartNotFoundException cartEx){
            return new ResponseEntity<> (getDummyCart(String.valueOf(cartId)),HttpStatus.BAD_REQUEST);
        }


    }

    @RequestMapping(method=RequestMethod.POST, value="/carts")
    public ResponseEntity<Cart> createCart() {
        Cart persistedCart = cartService.createCart();

        if(null != persistedCart.getId()){
            return new ResponseEntity<> (persistedCart,HttpStatus.OK);
        }else{
            return new ResponseEntity<> (persistedCart,HttpStatus.BAD_REQUEST);
        }


    }

    @RequestMapping(method=RequestMethod.POST, value="/carts/{cartId}/products",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cart> addProductsToCart(@PathVariable(name="cartId") String cartId, @RequestBody Product product) {
        try {
            Cart persistedCart = cartService.addProductToCart(cartId, product);
            return new ResponseEntity<> (persistedCart,HttpStatus.OK);
        } catch (ProductNotFoundException prodEx) {
            return new ResponseEntity<>(getDummyCart(cartId), HttpStatus.BAD_REQUEST);
        }
        catch(CartNotFoundException cartEx){
            return new ResponseEntity<>(getDummyCart(cartId), HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(method=RequestMethod.DELETE, value="/carts/{cartId}/products",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cart> removeProductsFromCart(@PathVariable(name="cartId") String cartId, @RequestBody Product product) {

        try {
            Cart persistedCart = cartService.removeProductFromCart(cartId, product);
            return new ResponseEntity<> (persistedCart,HttpStatus.OK);
        } catch (ProductNotFoundException prodEx) {
            return new ResponseEntity<>(getDummyCart(cartId), HttpStatus.BAD_REQUEST);
        }
        catch(CartNotFoundException cartEx){
            return new ResponseEntity<>(getDummyCart(cartId), HttpStatus.BAD_REQUEST);
        }

    }











    @RequestMapping(method=RequestMethod.POST, value="/api/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category persistedCategory = categoryService.createCategory(category);

        if(null != persistedCategory.getCategoryId()){
            return new ResponseEntity<> (persistedCategory,HttpStatus.OK);
        }else{
            return new ResponseEntity<> (persistedCategory,HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(method=RequestMethod.POST, value="/api/category/{categoryId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> addProductsToCategory(@PathVariable(name="categoryId") Long categoryId, @RequestBody Product product) {
        try {
            Category persistedCategory = categoryService.addProductToCategory(categoryId, product);
            return new ResponseEntity<> (persistedCategory,HttpStatus.OK);
        } catch (ProductNotFoundException | CategoryNotFoundException ex) {
            return new ResponseEntity<>(getDummyCategory(categoryId), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/api/category/{categoryId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> removeProductsFromCategory(@PathVariable(name="categoryId") Long categoryId, @RequestBody Product product) {

        try {
            Category persistedCategory = categoryService.removeProductFromCategory(categoryId, product);
            return new ResponseEntity<> (persistedCategory,HttpStatus.OK);
        } catch (ProductNotFoundException | CategoryNotFoundException exception) {
            return new ResponseEntity<>(getDummyCategory(categoryId), HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "api/category/listAll")
    public List<Category> findAll() throws CategoryNotFoundException, ProductNotFoundException {
        return categoryService.findAll();
    }

    @RequestMapping(value ="/api/category/{letter}/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> findCategoryNameByLetterOccurrence(@PathVariable(name="letter") char letter) throws CategoryNotFoundException, ProductNotFoundException {
        return categoryService.findCategoryNameByLetterOccurrence(letter);
    }


    @RequestMapping(method=RequestMethod.GET,value="api/product/listByCategory/{categoryId}")
    public ResponseEntity<Category> findByCategoryId(@PathVariable Long categoryId){

        try {
            Category category = categoryService.retrieveCategoryById(categoryId);
            return new ResponseEntity<>(category, HttpStatus.OK);

        }catch(CategoryNotFoundException catEx){
            return new ResponseEntity<> (getDummyCategory(categoryId),HttpStatus.BAD_REQUEST);
        }


    }





    private Cart getDummyCart(String cartId){

        Cart cart = new Cart();
        cart.setId(Long.parseLong(cartId));
        cart.setProducts(new ArrayList<>());
        return cart;
    }

    private Category getDummyCategory(Long categoryId){
        Category category = new Category();
        category.setId(categoryId);
        category.setProducts(new ArrayList<>());
        return category;
    }


}

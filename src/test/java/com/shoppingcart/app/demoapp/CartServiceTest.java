package com.shoppingcart.app.demoapp;

import com.shoppingcart.app.entity.Cart;
import com.shoppingcart.app.entity.Product;
import com.shoppingcart.app.exception.CartNotFoundException;
import com.shoppingcart.app.exception.ProductNotFoundException;
import com.shoppingcart.app.repository.CartRepository;
import com.shoppingcart.app.service.ICartService;
import com.shoppingcart.app.service.IProductService;
import com.shoppingcart.app.service.impl.CartServiceImpl;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private IProductService productService;

    @InjectMocks
    private ICartService cartService = new CartServiceImpl();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createCart_shouldReturnEmptyCart_whenCreateCartServiceIsInvoked() {

        //When
        when(cartRepository.save(any(Cart.class))).thenReturn(new Cart());

        Cart newCart = cartService.createCart();

        //Then
        assertThat(newCart, is(notNullValue()));
    }

    @Test
    public void retrieveCartById_shouldReturnCartNotFoundException_whenCartIdIsNotFound() throws CartNotFoundException {

        Cart mockCart = createEmptyCart(1000L, true);

        //When
        when(cartRepository.findById(eq(10L))).thenReturn(Optional.of(mockCart));
        thrown.expect(CartNotFoundException.class);

        Cart newCart = cartService.retrieveCartById(1L);

        //Then
        assertThat(newCart, is(nullValue()));
    }

    @Test
    public void retrieveCartById_shouldReturnCartEmpty_whenNoProductWasAddedToTheCart() throws CartNotFoundException {

        Cart mockCart = createEmptyCart(1000L, true);
        Long expectedId = 1000L;

        //When
        when(cartRepository.findById(eq(expectedId))).thenReturn(Optional.of(mockCart));
        Cart newCart = cartService.retrieveCartById(expectedId);

        verify(cartRepository, times(1)).findById(eq(expectedId));

        //Then
        assertThat(newCart, is(notNullValue()));
        assertThat(newCart, hasProperty("products", is(nullValue())));
    }

    @Test
    public void retrieveCartById_shouldReturnWithProducts_whenProductsWereAddedToTheCart() throws CartNotFoundException {

        Cart mockCart = createEmptyCart(1000L, false);
        Long expectedId = 1000L;

        //When
        when(cartRepository.findById(eq(expectedId))).thenReturn(Optional.of(mockCart));
        Cart newCart = cartService.retrieveCartById(expectedId);

        verify(cartRepository, times(1)).findById(eq(expectedId));

        //Then
        assertThat(newCart, is(notNullValue()));
        assertThat(newCart, hasProperty("products", is(notNullValue())));
    }

    @Test
    public void addProductToCart_shouldReturnProductNotFoundException_whenNonExistentProductWasAddedToCart() throws CartNotFoundException, ProductNotFoundException {

        Cart mockCart = createEmptyCart(1000L, false);
        Product productMock = mockCart.getProducts().get(0);
        Long cartExpectedId = 1000L;
        Long expectedProductId = 10L;

        //When
        when(cartRepository.findById(eq(cartExpectedId))).thenReturn(Optional.of(mockCart));
        when(productService.getProductById(eq(expectedProductId))).thenReturn(productMock);
        thrown.expect(ProductNotFoundException.class);

        Cart newCart = cartService.addProduct(String.valueOf(mockCart.getId()),productMock);

        assertThat(newCart, hasProperty("products", nullValue()));

    }


    @Test
    public void addProductToCart_shouldReturnCartWithProducts_whenProductsWereAddedToTheCart() throws CartNotFoundException, ProductNotFoundException {

        Cart mockCart = createEmptyCart(1000L, false);
        Product productMock = mockCart.getProducts().get(0);
        Long cartExpectedId = 1000L;
        Long expectedProductId = 1L;

        //When
        when(cartRepository.findById(eq(cartExpectedId))).thenReturn(Optional.of(mockCart));
        when(productService.getProductById(eq(expectedProductId))).thenReturn(productMock);

        Cart newCart = cartService.addProduct(String.valueOf(mockCart.getId()),productMock);
        assertThat(newCart, hasProperty("products", hasItem(Matchers.<Product>hasProperty("id", is(notNullValue())))));
        assertThat(newCart, hasProperty("products", hasItem(Matchers.<Product>hasProperty("id", is(equalTo(expectedProductId))))));
    }


    private Cart createEmptyCart(Long cartId, boolean emptyCart){

        Cart newCart = new Cart();

        if(!emptyCart){
            List<Product> products = createProductList();
            newCart.setId(cartId);
            newCart.setProducts(products);
        }
        return newCart;
    }

    private List<Product> createProductList(){
        Product product = new Product();
        product.setCost(20.00);
        product.setDescription("Product 1");
        product.setId(1L);
        product.setProductId(10L);
        product.setName("Nike shoes");
        List<Product> products = new ArrayList<>();
        products.add(product);

        return products;

    }


}
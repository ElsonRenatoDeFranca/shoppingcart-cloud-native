package com.shoppingcart.app.demoapp;

import com.shoppingcart.app.entity.Cart;
import com.shoppingcart.app.exception.CartNotFoundException;
import com.shoppingcart.app.repository.CartRepository;
import com.shoppingcart.app.service.ICartService;
import com.shoppingcart.app.service.impl.CartServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
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

        Cart mockCar = new Cart();
        mockCar.setId(1000L);

        //When
        when(cartRepository.findById(eq(10L))).thenReturn(Optional.of(mockCar));
        thrown.expect(CartNotFoundException.class);

        Cart newCart = cartService.retrieveCartById(1L);

        //Then
        assertThat(newCart, is(nullValue()));
    }


}
package com.shoppingcart.app.demoapp;

import com.shoppingcart.app.entity.Product;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



import static io.restassured.RestAssured.when;


/**
 * Created by e068635 on 2/8/2019.
 */
public class CartServiceControllerTest {


    @Test
    public void retrieveCartById_shouldReturn200_whenCartIsFound() {

        when().get("/carts/{cartId}",1).
                then().
                body("cart.cartId",equalTo(1));

    }

    @Test
    public void retrieveCartById_shouldReturn400_whenCartIdIsNotFound() {

        when().
                get("/carts/{cartId}", 1).
                then().
                statusCode(400).
                body("cart.id", equalTo(1),
                        "cart.products.id", hasItems(1));

    }


}

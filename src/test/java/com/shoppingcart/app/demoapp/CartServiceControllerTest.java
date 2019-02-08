package com.shoppingcart.app.demoapp;

import org.junit.Test;


import static io.restassured.RestAssured.when;


/**
 * Created by e068635 on 2/8/2019.
 */
public class CartServiceControllerTest {


    @Test
    public void whenCartIsCreated_thenOK() {
        when().request("HEAD", "/carts").then().statusCode(200);

        /*
        assertThat(responseVO, hasProperty("propertyName1e  ", nullValue()));
        assertThat(responseVO,  hasProperty("mylist",
        org.hamcrest.Matchers.<ObjectInsideTheList>hasItems(allOf(hasProperty("propertyName1", notNullValue()), hasProperty("propertyName2", notNullValue()) ))));


        assertThat(responseVO, hasProperty("mylist",
                org.hamcrest.Matchers.<ObjectInsideTheList>hasItems(
                        allOf(
                                hasProperty("propertyName1", equalTo(getPropertyFromObjectInsideTheList())),
                                hasProperty("propertyName2", equalTo(expectedResult))
                        )
                )
        ));

         */


    }

}

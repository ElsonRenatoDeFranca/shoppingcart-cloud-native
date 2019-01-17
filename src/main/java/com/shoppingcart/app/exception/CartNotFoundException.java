package com.shoppingcart.app.exception;

/**
 * Created by e068635 on 1/15/2019.
 */
public class CartNotFoundException extends Exception  {

    private static final long serialVersionUID = 7718828512143245128L;

    public CartNotFoundException(String message) {
        super(message);
    }

}

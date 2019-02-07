DROP TABLE IF EXISTS PUBLIC.CART;
DROP TABLE IF EXISTS PUBLIC.PRODUCT;
DROP TABLE IF EXISTS PUBLIC.CART_PRODUCT;

CREATE SEQUENCE IF NOT EXISTS PUBLIC.CART_SEQ;
CREATE SEQUENCE IF NOT EXISTS PUBLIC.PRODUCT_SEQ;

CREATE TABLE PUBLIC.CART(
   CART_ID BIGINT DEFAULT CART_SEQ.nextval,
   PRIMARY KEY (CART_ID)
);

CREATE TABLE PUBLIC.PRODUCT(
    PRODUCT_ID BIGINT DEFAULT PRODUCT_SEQ.nextval,
    ID BIGINT,
    PRODUCT_NAME VARCHAR(50),
    PRODUCT_DESCRIPTION VARCHAR(80),
    PRODUCT_COST VARCHAR(30),
    CART_ID BIGINT,
    PRIMARY KEY (PRODUCT_ID),
    FOREIGN KEY (CART_ID) REFERENCES PUBLIC.CART
);

CREATE TABLE CART_PRODUCT (
    CART_ID BIGINT NOT NULL,
    PRODUCT_ID BIGINT NOT NULL,
    FOREIGN KEY (CART_ID) REFERENCES PUBLIC.CART,
    FOREIGN KEY (PRODUCT_ID) REFERENCES PUBLIC.PRODUCT
)



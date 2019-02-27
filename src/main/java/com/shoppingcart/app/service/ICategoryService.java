package com.shoppingcart.app.service;

import com.shoppingcart.app.entity.Category;
import com.shoppingcart.app.entity.Product;
import com.shoppingcart.app.exception.CategoryNotFoundException;
import com.shoppingcart.app.exception.ProductNotFoundException;

import java.util.List;

public interface ICategoryService {



    List<Category> listAll()  throws CategoryNotFoundException;

    /**
     *
     * @param id
     * @return
     * @throws CategoryNotFoundException
     */
    Category retrieveCategoryById(Long id)  throws CategoryNotFoundException;


    /**
     *
     *
     */
    Category createCategory();


    /**
     *
     * @param categoryId
     * @param product
     * @return
     * @throws ProductNotFoundException
     * @throws CategoryNotFoundException
     */
    Category addProductToCategory(Long categoryId, Product product) throws ProductNotFoundException, CategoryNotFoundException;

    /**
     *
     * @param categoryId
     * @param product
     * @return
     * @throws ProductNotFoundException
     * @throws CategoryNotFoundException
     */
    Category removeProductFromCategory(Long categoryId, Product product) throws ProductNotFoundException, CategoryNotFoundException;

}

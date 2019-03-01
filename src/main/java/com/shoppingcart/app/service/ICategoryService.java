package com.shoppingcart.app.service;

import com.shoppingcart.app.entity.Category;
import com.shoppingcart.app.entity.Product;
import com.shoppingcart.app.exception.CategoryNotFoundException;
import com.shoppingcart.app.exception.ProductNotFoundException;

import java.util.List;

public interface ICategoryService {


    /**
     *
     * @return
     * @throws CategoryNotFoundException
     */
    List<Category> findAll()  throws CategoryNotFoundException;

    /**
     *
     * @param id
     * @return
     * @throws CategoryNotFoundException
     */
    Category retrieveCategoryById(Long id)  throws CategoryNotFoundException;


    /**
     *
     * @param letter
     * @return
     * @throws CategoryNotFoundException
     */
    List<String> findCategoryNameByLetterOccurrence(char letter ) throws CategoryNotFoundException;


    /**
     *
     *
     */
    Category createCategory(Category category);


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

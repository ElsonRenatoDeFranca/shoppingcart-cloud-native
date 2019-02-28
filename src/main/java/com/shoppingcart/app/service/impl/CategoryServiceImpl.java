package com.shoppingcart.app.service.impl;

import com.shoppingcart.app.constants.ShoppingCartConstants;
import com.shoppingcart.app.entity.Category;
import com.shoppingcart.app.entity.Product;
import com.shoppingcart.app.exception.CategoryNotFoundException;
import com.shoppingcart.app.exception.ProductNotFoundException;
import com.shoppingcart.app.repository.CategoryRepository;
import com.shoppingcart.app.repository.ProductRepository;
import com.shoppingcart.app.service.ICategoryService;
import com.shoppingcart.app.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private IProductService productService;



    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    @Override
    public Category retrieveCategoryById(Long id) throws CategoryNotFoundException {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(ShoppingCartConstants.CATEGORY_NOT_FOUND_ERROR_MESSAGE));
    }

    private Long getMaxNumberOfLetters(TreeMap<String, Long> map){

        return map.entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getValue();
    }

    private List<Category> getListOfMaxOccurrencesOfOneletter(TreeMap<String, Long> map){

        Long maxNumberOfOccurrence = getMaxNumberOfLetters(map);


        List listOfMaxNumberOfOccurrence = map.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == maxNumberOfOccurrence)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return listOfMaxNumberOfOccurrence;

    }

    @Override
    public List<Category> retrieveCategoryByLetterOccurrence(char letter) throws CategoryNotFoundException {

        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        TreeMap<String, Long> map = new TreeMap<>();

        for(Category category : categories){
            Long letterCounter = category.getName().codePoints().filter(character -> character == letter).count();
            map.put(category.getName(),letterCounter);
        }

        List listOfMax = getListOfMaxOccurrencesOfOneletter(map);

        return listOfMax;
    }




    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category addProductToCategory(Long categoryId, Product product) throws ProductNotFoundException, CategoryNotFoundException {
        Product searchedProduct = productService.getProductById(product.getId());
        Category category = retrieveCategoryById(categoryId);

        if (searchedProduct != null) {
            category.getProducts().add(searchedProduct);
            categoryRepository.save(category);
        } else {
            throw new ProductNotFoundException(ShoppingCartConstants.PRODUCT_NOT_FOUND_ERROR_MESSAGE);
        }

        return category;

    }

    @Override
    public Category removeProductFromCategory(Long categoryId, Product product) throws ProductNotFoundException, CategoryNotFoundException {

        Category category = retrieveCategoryById(categoryId);
        productRepository.delete(product);
        category.getProducts().removeIf(product1 -> product.equals(product));

        return category;
    }

}

package com.shoppingcart.app.demoapp;

import com.shoppingcart.app.entity.Cart;
import com.shoppingcart.app.entity.Category;
import com.shoppingcart.app.entity.Product;
import com.shoppingcart.app.exception.CartNotFoundException;
import com.shoppingcart.app.exception.CategoryNotFoundException;
import com.shoppingcart.app.repository.CartRepository;
import com.shoppingcart.app.repository.CategoryRepository;
import com.shoppingcart.app.service.ICartService;
import com.shoppingcart.app.service.ICategoryService;
import com.shoppingcart.app.service.IProductService;
import com.shoppingcart.app.service.impl.CartServiceImpl;
import com.shoppingcart.app.service.impl.CategoryServiceImpl;
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

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by e068635 on 2/27/2019.
 */

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {


    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private IProductService productService;

    @InjectMocks
    private ICategoryService categoryService = new CategoryServiceImpl();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createCategory_shouldReturnEmptyCategory_whenCreateCategoryServiceIsInvoked() {

        //When
        when(categoryRepository.save(any(Category.class))).thenReturn(new Category());

        Category category = new Category();

        Category newCategory = categoryService.createCategory(category);

        //Then
        assertThat(newCategory, is(notNullValue()));
    }

    @Test
    public void createCategory_shouldReturnCategory_whenCreateCategoryServiceIsInvoked() throws CategoryNotFoundException {


        Category mockCategory = createEmptyCategory(1000L, "categoryName02", false);
        Long expectedId = 1000L;

        //When
        when(categoryRepository.findById(eq(expectedId))).thenReturn(Optional.of(mockCategory));
        Category newCategory = categoryService.retrieveCategoryById(expectedId);

        verify(categoryRepository, times(1)).findById(eq(expectedId));

        //Then
        assertThat(newCategory, is(notNullValue()));
        assertThat(newCategory, hasProperty("products", is(notNullValue())));

    }

    @Test
    public void createCategory_shouldReturnCategory_whenCategoryNameHasMoreLetters() throws CategoryNotFoundException {


        Category mockCategory1 = createEmptyCategory(1000L, "Alimentos", false);
        Category mockCategory2 = createEmptyCategory(1001L, "Vestuario", false);
        Category mockCategory3 = createEmptyCategory(1002L, "Bebidas", false);

        List<Category> categories = new ArrayList<>();
        categories.add(mockCategory1);
        categories.add(mockCategory2);
        categories.add(mockCategory3);

        //When
        when(categoryRepository.findAll()).thenReturn(categories);

        Category newCategory = categoryService.retrieveCategoryByLetterOccurrence("i");
        //verify(categoryRepository, times(1)).findById(eq(expectedId));

        //Then
        assertThat(newCategory, is(notNullValue()));
        assertThat(newCategory, hasProperty("products", is(notNullValue())));

    }

    private Category createEmptyCategory(Long categoryId, String categoryName, boolean emptyCategory){

        Category newCategory = new Category();

        if(!emptyCategory){
            List<Product> products = createProductList();
            newCategory.setCategoryId(categoryId);
            newCategory.setName(categoryName);
            newCategory.setProducts(products);
        }
        return newCategory;
    }

    private Product createProduct(){
        Product product = new Product();
        product.setCost(20.00);
        product.setDescription("Product 1");
        product.setId(1L);
        product.setProductId(10L);
        product.setName("Nike shoes");

        return product;

    }
    private List<Product> createProductList(){
        List<Product> products = new ArrayList<>();
        products.add(createProduct());

        return products;

    }




}

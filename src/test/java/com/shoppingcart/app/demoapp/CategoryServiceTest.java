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
import org.hamcrest.Matchers;
import org.junit.Before;
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

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasItems;
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

    private  List<Category> categories;


    @Before
    public void setup(){
        categories = new ArrayList<>();
        Category mockCategory1 = createCategory(1000L, "Sabao", false);
        Category mockCategory2 = createCategory(1001L, "Samba", false);
        Category mockCategory3 = createCategory(1002L, "Carnaval", false);

        categories.add(mockCategory1);
        categories.add(mockCategory2);
        categories.add(mockCategory3);
    }

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


        Category mockCategory = createCategory(1000L, "categoryName02", false);
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
    public void createCategory_shouldReturnCategory_whenCategoryNameDoesNotHaveAnyOccurrenceOfLetter() throws CategoryNotFoundException {

        //Given
        char letter = 'e';

        //When
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> categoryList = categoryService.findCategoryByLetterOccurrence(letter);

        //Then
        assertThat(categoryList, is(notNullValue()));
        assertThat(categoryList, hasSize(0));
    }


    @Test
    public void createCategory_shouldReturnCategory_whenCategoryNameHasAtLeastOccurrenceOfLetter() throws CategoryNotFoundException {

        //Given
        categories.add(createCategory(1000L, "alimento", false));
        categories.add(createCategory(1001L, "perfume", false));
        categories.add(createCategory(1002L, "elefante", false));
        categories.add(createCategory(1003L, "elegante", false));

        char letter = 'e';

        //When
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> categoryList = categoryService.findCategoryByLetterOccurrence(letter);

        //Then
        assertThat(categoryList, is(notNullValue()));
        assertThat(categoryList, hasSize(2));
        //assertThat(categoryList, hasItems("elefante", "elegante"));
    }

    private Category createCategory(Long categoryId, String categoryName, boolean emptyCategory){

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

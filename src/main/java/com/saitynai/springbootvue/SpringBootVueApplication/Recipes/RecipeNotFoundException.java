package com.saitynai.springbootvue.SpringBootVueApplication.Recipes;

public class RecipeNotFoundException extends RuntimeException {
    RecipeNotFoundException(Long id) {
        super("Could not find recipe " + id);
    }
}

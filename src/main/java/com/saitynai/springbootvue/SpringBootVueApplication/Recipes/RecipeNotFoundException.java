package com.saitynai.springbootvue.SpringBootVueApplication.Recipes;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(Long id) {
        super("Could not find recipe " + id);
    }
}

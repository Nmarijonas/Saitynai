package com.saitynai.springbootvue.SpringBootVueApplication.Recipes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}

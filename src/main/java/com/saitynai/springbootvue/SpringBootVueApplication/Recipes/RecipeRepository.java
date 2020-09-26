package com.saitynai.springbootvue.SpringBootVueApplication.Recipes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public
interface RecipeRepository extends JpaRepository<Recipe,Long> {
}

package com.saitynai.springbootvue.SpringBootVueApplication;


import com.saitynai.springbootvue.SpringBootVueApplication.Recipes.Recipe;
import com.saitynai.springbootvue.SpringBootVueApplication.Recipes.RecipeRepository;
import com.saitynai.springbootvue.SpringBootVueApplication.Comments.Comment;
import com.saitynai.springbootvue.SpringBootVueApplication.Comments.CommentRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@SpringBootApplication
public class SpringBootVueApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootVueApplication.class, args);
	}

	@Bean
	ApplicationRunner init(RecipeRepository repository){
		String[][] r = {
				{"title1","ingredients1","desc1","recipe1"},
				{"title2","ingredients2","desc2","recipe2"},
				{"title3","ingredients3","desc3","recipe3"},
				{"title4","ingredients4","desc4","recipe4"}
		};
		return  args -> {
			for (var val:r) {
				Recipe recipe = new Recipe();
				recipe.setTitle(val[0]);
				recipe.setIngredients(val[1]);
				recipe.setDescription(val[2]);
				recipe.setRecipe(val[3]);
				repository.save(recipe);
			}
			repository.findAll().forEach(System.out::println);
		};
	}
	@Bean
	ApplicationRunner init(CommentRepository repository){
		String [][]c = {
				{"comment1"},
				{"comment2"},
				{"comment3"}
		};
		return  args -> {
			for (var val:c) {
				Comment comment = new Comment();
				comment.setComment(val[0]);
				repository.save(comment);
			}
			repository.findAll().forEach(System.out::println);
		};
	}

	@Bean
	public FilterRegistrationBean simpleCorsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		// *** URL below needs to match the Vue client URL and port ***
		config.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
		config.setAllowedMethods(Collections.singletonList("*"));
		config.setAllowedHeaders(Collections.singletonList("*"));
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean<>(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

}

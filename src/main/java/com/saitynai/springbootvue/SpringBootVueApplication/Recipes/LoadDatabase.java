package com.saitynai.springbootvue.SpringBootVueApplication.Recipes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(com.saitynai.springbootvue.SpringBootVueApplication.Recipes.LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(RecipeRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Recipe("title1","ingredient1","desc1","recipe1")));
            log.info("Preloading " + repository.save(new Recipe("title2","ingredient2","desc2","recipe2")));
            log.info("Preloading " + repository.save(new Recipe("title3","ingredient3","desc3","recipe3")));};
    }
}

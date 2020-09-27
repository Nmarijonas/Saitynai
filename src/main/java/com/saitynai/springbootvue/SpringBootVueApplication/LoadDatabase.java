package com.saitynai.springbootvue.SpringBootVueApplication;

import com.saitynai.springbootvue.SpringBootVueApplication.Comments.Comment;
import com.saitynai.springbootvue.SpringBootVueApplication.Comments.CommentRepository;
import com.saitynai.springbootvue.SpringBootVueApplication.Recipes.Recipe;
import com.saitynai.springbootvue.SpringBootVueApplication.Recipes.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CommentRepository com_repository, RecipeRepository rec_repository) {

        return args -> {
            log.info("Preloading " + com_repository.save(new Comment("title1","comment1")));
            log.info("Preloading " + com_repository.save(new Comment("title2","comment2")));
            log.info("Preloading " + com_repository.save(new Comment("title3","comment3")));

            log.info("Preloading " + rec_repository.save(new Recipe("title1","ingredient1","desc1","recipe1")));
            log.info("Preloading " + rec_repository.save(new Recipe("title2","ingredient2","desc2","recipe2")));
            log.info("Preloading " + rec_repository.save(new Recipe("title3","ingredient3","desc3","recipe3")));
        };
    }
}

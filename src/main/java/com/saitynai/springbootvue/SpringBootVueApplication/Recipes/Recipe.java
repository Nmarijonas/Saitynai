package com.saitynai.springbootvue.SpringBootVueApplication.Recipes;

import lombok.*;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
public class Recipe {
    @Id @GeneratedValue
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String ingredients;
    @NonNull
    private String description;
    @NonNull
    private String recipe;


    private Long user_id= null;
    private Long[] category = null;
    private Boolean completed = false;
}

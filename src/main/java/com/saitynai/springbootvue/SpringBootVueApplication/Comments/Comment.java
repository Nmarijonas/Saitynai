package com.saitynai.springbootvue.SpringBootVueApplication.Comments;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Comment {
    @Id @GeneratedValue
    private Long id;
    @NonNull
    private String comment;

    private Long recipe_id= null;
    private Long user_id = null;
    private Boolean completed = false;
}
package com.saitynai.springbootvue.SpringBootVueApplication.Comments;

public class CommentNotFoundException extends RuntimeException {
    CommentNotFoundException(Long id) {
        super("Could not find comment " + id);
    }
}

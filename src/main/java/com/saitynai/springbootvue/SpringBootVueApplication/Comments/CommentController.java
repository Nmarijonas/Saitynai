package com.saitynai.springbootvue.SpringBootVueApplication.Comments;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class CommentController {

    private final CommentRepository repository;

    CommentController(CommentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/comments")
    List<Comment> all() {
        return repository.findAll();
    }

    @PostMapping("/comments")
    Comment newRecipe(@RequestBody Comment newComment) {
        return repository.save(newComment);
    }

    // Single item
    @GetMapping("/comments/{id}")
    Comment one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    @PutMapping("/comments/{id}")
    Comment replaceComment(@RequestBody Comment newComment, @PathVariable Long id) {
        return repository.findById(id)
                .map(recipe -> {
                    recipe.setTitle(newComment.getTitle());
                    recipe.setComment(newComment.getComment());
                    return repository.save(recipe);
                })
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    @DeleteMapping("/comments/{id}")
    void deleteComment(@PathVariable Long id) {
        try{
            repository.deleteById(id);
        }
        catch (Exception e){
            throw  new CommentNotFoundException(id);

        }
    }
}

package com.saitynai.springbootvue.SpringBootVueApplication.Comments;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import com.saitynai.springbootvue.SpringBootVueApplication.Recipes.Recipe;
import com.saitynai.springbootvue.SpringBootVueApplication.Recipes.RecipeRepository;
import com.saitynai.springbootvue.SpringBootVueApplication.Recipes.RecipeNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CommentController {

    private final CommentRepository repository;
    private final RecipeRepository recipeRepository;
    private final CommentModelAssembler assembler;

    CommentController(CommentRepository repository,
                      RecipeRepository recipeRepository,
                      CommentModelAssembler assembler) {
        this.repository = repository;
        this.recipeRepository = recipeRepository;
        this.assembler = assembler;
    }

    @GetMapping("/recipes/{id}/comments")
    CollectionModel<EntityModel<Comment>> all(@PathVariable Long id) {
        Recipe recipe = recipeRepository.findById(id) //
                .orElseThrow(() -> new RecipeNotFoundException(id));
        List<EntityModel<Comment>> comments = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(comments, linkTo(methodOn(CommentController.class).all()).withSelfRel());
    }

    @GetMapping("/comments")
    CollectionModel<EntityModel<Comment>> all() {

        List<EntityModel<Comment>> comments = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(comments, linkTo(methodOn(CommentController.class).all()).withSelfRel());
    }

    @PostMapping("/comments")
    ResponseEntity<?> newComment(@RequestBody Comment newComment) {
        EntityModel<Comment> commentEntityModel = assembler.toModel(repository.save(newComment));
        return  ResponseEntity.created(commentEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                body(commentEntityModel);
    }

    @GetMapping("/comments/{id}")
    EntityModel<Comment> one(@PathVariable Long id) {
        Comment comment = repository.findById(id) //
                .orElseThrow(() -> new CommentNotFoundException(id));

        return assembler.toModel(comment);
    }

    @PutMapping("/comments/{id}")
    ResponseEntity<?> replaceComment(@RequestBody Comment newComment, @PathVariable Long id) {
        Comment updatedComment = repository.findById(id).
                map(comment -> {
                    comment.setTitle(newComment.getTitle());
                    comment.setComment(newComment.getComment());
                    return repository.save(comment);
                }).orElseThrow(() -> new CommentNotFoundException(id));
        EntityModel<Comment> entityModel = assembler.toModel(updatedComment);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                body(entityModel);
    }

    @DeleteMapping("/comments/{id}")
    ResponseEntity<?> deleteComment(@PathVariable Long id) {
        try{
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e){
            throw new CommentNotFoundException(id);
        }
    }
}

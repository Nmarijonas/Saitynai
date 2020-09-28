package com.saitynai.springbootvue.SpringBootVueApplication.Recipes;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

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
class RecipeController {

    private final RecipeRepository repository;
    private final RecipeModelAssembler assembler;

    RecipeController(RecipeRepository repository, RecipeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/recipes")
    CollectionModel<EntityModel<Recipe>> all() {

        List<EntityModel<Recipe>> employees = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(RecipeController.class).all()).withSelfRel());
    }

    @PostMapping("/recipes")
    ResponseEntity<?> newRecipe(@RequestBody Recipe newRecipe) {
        EntityModel<Recipe> recipeEntityModel = assembler.toModel(repository.save(newRecipe));
        return  ResponseEntity.created(recipeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                body(recipeEntityModel);
    }

    @GetMapping("/recipes/{id}")
    EntityModel<Recipe> one(@PathVariable Long id) {
        Recipe recipe = repository.findById(id) //
                .orElseThrow(() -> new RecipeNotFoundException(id));

        return assembler.toModel(recipe);
    }

    @PutMapping("/recipes/{id}")
    ResponseEntity<?> replaceRecipe(@RequestBody Recipe newRecipe, @PathVariable Long id) {
        Recipe updatedRecipe = repository.findById(id).
                map(recipe -> {
                    recipe.setTitle(newRecipe.getTitle());
                    recipe.setIngredients(newRecipe.getIngredients());
                    recipe.setDescription(newRecipe.getIngredients());
                    recipe.setRecipe(newRecipe.getRecipe());
                    return repository.save(recipe);
                }).orElseThrow(() -> new RecipeNotFoundException(id));
        EntityModel<Recipe> entityModel = assembler.toModel(updatedRecipe);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                body(entityModel);
    }

    @DeleteMapping("/recipes/{id}")
    ResponseEntity<?> deleteRecipe(@PathVariable Long id) {
        try{
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e){
            throw new RecipeNotFoundException(id);
        }
    }
}

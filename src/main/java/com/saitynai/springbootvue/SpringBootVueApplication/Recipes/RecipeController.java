package com.saitynai.springbootvue.SpringBootVueApplication.Recipes;
import java.util.List;

import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
class RecipeController {

    private final RecipeRepository repository;

    RecipeController(RecipeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/recipes")
    List<Recipe> all() {
        return repository.findAll();
    }

    @PostMapping("/recipes")
    Recipe newRecipe(@RequestBody Recipe newRecipe) {
        return repository.save(newRecipe);
    }

    // Single item
    @GetMapping("/recipes/{id}")
    Recipe one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
    }

    @PutMapping("/recipes/{id}")
    Recipe replaceRecipe(@RequestBody Recipe newRecipe, @PathVariable Long id) {
        return repository.findById(id)
                .map(recipe -> {
                    recipe.setTitle(newRecipe.getTitle());
                    recipe.setIngredients(newRecipe.getIngredients());
                    recipe.setDescription(newRecipe.getIngredients());
                    recipe.setRecipe(newRecipe.getRecipe());
                    return repository.save(recipe);
                })
                .orElseThrow(() -> new RecipeNotFoundException(id));
    }

    @DeleteMapping("/recipes/{id}")
    void deleteRecipe(@PathVariable Long id) {
        try{
            repository.deleteById(id);
        }
        catch (Exception e){
            throw  new RecipeNotFoundException(id);

        }
        //repository.deleteById(id);
//        repository.findById(id).map(recipe -> {
//            repository.deleteById(id);
//            return null;
//        }).orElseThrow(() -> new RecipeNotFoundException(id));
    }
}

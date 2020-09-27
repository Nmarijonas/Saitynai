package com.saitynai.springbootvue.SpringBootVueApplication.Recipes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Recipe {
    @Id @GeneratedValue private Long id;
    private String title;
    private String ingredients;
    private String description;
    private String recipe;

    Recipe(){}
    Recipe(String title, String ingredients, String description, String recipe){
        this.title = title;
        this.ingredients = ingredients;
        this.description = description;
        this.recipe = recipe;
    }
    // getters
    public Long getId(){
        return this.id;
    }
    public String getTitle(){return this.title;}
    public String getIngredients(){return this.ingredients;}
    public String getDescription(){return this.description;}
    public String getRecipe(){return this.recipe;}

    //setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setTitle(String title){this.title = title;}
    public void setIngredients(String ingredients){this.ingredients = ingredients;}
    public void setDescription(String description){this.description = description;}
    public  void setRecipe(String recipe){this.recipe=recipe;}
    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Recipe))
            return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(this.id, recipe.id)
                && Objects.equals(this.title, recipe.title)
                && Objects.equals(this.ingredients, recipe.ingredients)
                && Objects.equals(this.description,recipe.description)
                && Objects.equals(this.recipe,recipe.recipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.title, this.ingredients,this.description,this.recipe);
    }

    @Override
    public String toString() {
        return "Recipe{" + "" +
                "id=" + this.id + ", "+
                "title='" + this.title + '\'' + ", "+
                "ingredients='" + this.ingredients + '\'' + ", "+
                "description='" + this.description + '\'' + ", "+
                "recipe='" + this.recipe + '\'' + '}';
    }
}

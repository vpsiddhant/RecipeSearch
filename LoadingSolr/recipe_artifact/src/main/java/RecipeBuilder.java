public class RecipeBuilder {

  private String name;
  private String id;
  private Double minutes;
  private String[] tags;
  private Double calories;
  private Nutrients nutrients;
  private Integer numberOfSteps;
  private String[] steps;
  private String description;
  private String[] ingredients;
  private Integer numberOfIngredients;

  RecipeBuilder name(String name) {
    this.name = name;
    return this;
  }

  RecipeBuilder id(String id) {
    this.id = id;
    return this;
  }

  RecipeBuilder minutes(Double minutes) {
    this.minutes = minutes;
    return this;
  }

  RecipeBuilder tags(String[] tags) {
    this.tags = tags;
    return this;
  }

  RecipeBuilder calories(Double calories) {
    this.calories = calories;
    return this;
  }

  RecipeBuilder nutrients(String[] nutrients) {
    this.nutrients = new Nutrients(nutrients);
    return this;
  }

  RecipeBuilder numberOfSteps(Integer n) {
    this.numberOfSteps = n;
    return this;
  }

  RecipeBuilder steps(String[] steps) {
    this.steps = steps;
    return this;
  }

  RecipeBuilder description(String description) {
    this.description = description;
    return this;
  }

  RecipeBuilder ingredients(String[] ingredients) {
    this.ingredients = ingredients;
    return this;
  }


  RecipeBuilder numberOfIngredients(Integer n) {
    this.numberOfIngredients = n;
    return this;
  }

  Recipe build() {
    return new Recipe(name, id, minutes, tags, calories, nutrients, numberOfSteps, steps, description, ingredients, numberOfIngredients);
  }


}

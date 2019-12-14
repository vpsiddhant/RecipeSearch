public class Recipe {
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
  private Double dietaryScore;

  Recipe(String name, String id, Double minutes, String[] tags, Double calories, Nutrients nutrients, Integer numberOfSteps, String[] steps, String description, String[] ingredients, Integer numberOfIngredients) {
    this.name = name;
    this.id = id;
    this.minutes = minutes;
    this.tags = tags;
    this.calories = calories;
    this.nutrients = nutrients;
    this.numberOfSteps = numberOfSteps;
    this.steps = steps;
    this.description = description;
    this.ingredients = ingredients;
    this.numberOfIngredients = numberOfIngredients;
    this.dietaryScore = this.nutrients.calculateRatioFormula();
  }


  public static RecipeBuilder RecipeBuilder() {
    return new RecipeBuilder();
  }

  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }

  public Double getMinutes() {
    return minutes;
  }

  public String[] getTags() {
    return tags;
  }

  public Double getCalories() {
    return calories;
  }

  public Nutrients getNutrients() {
    return nutrients;
  }

  public Integer getNumberOfSteps() {
    return numberOfSteps;
  }

  public String[] getSteps() {
    return steps;
  }

  public String getDescription() {
    return description;
  }

  public String[] getIngredients() {
    return ingredients;
  }

  public Integer getNumberOfIngredients() {
    return numberOfIngredients;
  }

  public Double getDietaryScore() {
    return dietaryScore;
  }

  public String allText() {
    return this.name + " " + this.description + " " + convertToText(steps) + convertToText(ingredients) + convertToText(tags);
  }

  private String convertToText(String[] stringArray) {
    StringBuilder result = new StringBuilder();
    for (String s : stringArray) {
      result.append(" ").append(s);
    }
    return result.toString();
  }
}

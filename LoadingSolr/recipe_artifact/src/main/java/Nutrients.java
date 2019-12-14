public class Nutrients {

  private double percentageDVTotalFats;
  private double percentageDVSugar;
  private double percentageDVSodium;
  private double percentageDVProtein;
  private double percentageDVSaturatedFats;
  private double percentageDVCarbohydrates;
  private double percentageDVCalories;
  private double calories;
  private double weightTotalFats;
  private double weightSugar;
  private double weightSodium;
  private double weightProtein;
  private double weightSaturatedFats;
  private double weightCarbohydrates;

  public final static double MAX_FAT = 65;
  public final static double MAX_SATURATED_FAT = 20;
  public final static double MAX_CALORIES = 2000;
  public final static double MAX_SODIUM = 2.4;
  public final static double MIN_CARBOHYDRATES = 300;
  public final static double MAX_SUGAR = 25;
  public final static double MIN_PROTEIN = 50.3;

  public final static int calorieIndex = 0;
  public final static int totalFatIndex = 1;
  public final static int sugarIndex = 2;
  public final static int sodiumIndex = 3;
  public final static int proteinIndex = 4;
  public final static int saturatedFatIndex = 5;
  public final static int totalCarbsIndex = 6;


  Nutrients(String[] values) {
    this.calories = Double.parseDouble(values[calorieIndex]);
    this.percentageDVCalories = (this.calories / MAX_CALORIES) * 100;
    this.percentageDVProtein = Double.parseDouble(values[proteinIndex]);
    this.percentageDVSaturatedFats = Double.parseDouble(values[saturatedFatIndex]);
    this.percentageDVCarbohydrates = Double.parseDouble(values[totalCarbsIndex]);
    this.percentageDVSugar = Double.parseDouble(values[sugarIndex]);
    this.percentageDVSodium = Double.parseDouble(values[sodiumIndex]);
    this.percentageDVTotalFats = Double.parseDouble(values[totalFatIndex]);

    this.weightTotalFats = (percentageDVTotalFats / 100) * MAX_FAT;
    this.weightSaturatedFats = (percentageDVSaturatedFats / 100) * MAX_SATURATED_FAT;
    this.weightCarbohydrates = (percentageDVCarbohydrates / 100) * MIN_CARBOHYDRATES;
    this.weightSugar = (percentageDVSugar / 100) * MAX_SUGAR;
    this.weightSodium = (percentageDVSodium / 100) * MAX_SODIUM;
    this.weightProtein = (percentageDVProtein / 100) * MIN_PROTEIN;
  }


  public double getPercentageDVTotalFats() {
    return percentageDVTotalFats;
  }


  public double getPercentageDVSugar() {
    return percentageDVSugar;
  }


  public double getPercentageDVSodium() {
    return percentageDVSodium;
  }


  public double getPercentageDVProtein() {
    return percentageDVProtein;
  }

  public double getPercentageDVCarbohydrates() {
    return percentageDVCarbohydrates;
  }

  public double getPercentageDVSaturatedFats() {
    return percentageDVSaturatedFats;
  }

  public double getPercentageDVCalories() {
    return percentageDVCalories;
  }

  public double getCalories() {
    return calories;
  }

  public double getWeightTotalFats() {
    return weightTotalFats;
  }

  public double getWeightSugar() {
    return weightSugar;
  }

  public double getWeightSodium() {
    return weightSodium;
  }

  public double getWeightProtein() {
    return weightProtein;
  }

  public double getWeightSaturatedFats() {
    return weightSaturatedFats;
  }

  public double getWeightCarbohydrates() {
    return weightCarbohydrates;
  }

  public Double calculateRatioFormula() {
    return (percentageDVProtein + percentageDVTotalFats) / (percentageDVSaturatedFats + 1 + percentageDVSodium + percentageDVSugar + percentageDVCarbohydrates);
  }
}

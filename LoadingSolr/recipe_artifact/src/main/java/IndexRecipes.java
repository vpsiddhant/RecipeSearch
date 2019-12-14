import com.opencsv.CSVReader;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.common.SolrInputDocument;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class IndexRecipes implements GeneralIndexer {

  HttpSolrClient c;

  final static String RECIPE_NAME = "Name";
  final static String ID = "id";
  final static String DESCRIPTION = "Description";
  final static String TIME_TO_MAKE = "TimeToMake";
  final static String INGREDIENTS = "Ingredients";
  final static String NUMBER_OF_INGREDIENTS = "NumberOfIngredients";
  final static String NUMBER_OF_STEPS = "NumberOfSteps";
  final static String STEPS = "Steps";
  final static String RECIPE_TAGS = "RecipeTags";
  final static String TOTAL_FATS_DV = "TotalFatsDV";
  final static String TOTAL_CARBOHYDRATES_DV = "CarbohydratesDV";
  final static String TOTAL_PROTEINS_DV = "ProteinsDV";
  final static String TOTAL_SATURATED_FATS_DV = "SaturatedFatsDV";
  final static String TOTAL_CALORIES = "Calories";
  final static String TOTAL_CALORIES_DV = "CaloriesDV";
  final static String TOTAL_SUGAR_DV = "SugarDV";
  final static String TOTAL_SODIUM_DV = "SodiumDV";
  final static String TOTAL_FATS_WEIGHT = "TotalFatsWeight";
  final static String TOTAL_CARBOHYDRATES_WEIGHT = "TotalCarbohydratesWeight";
  final static String TOTAL_PROTEINS_WEIGHT = "ProteinsWeight";
  final static String TOTAL_SATURATED_FATS_WEIGHT = "TotalSaturatedFatsWeight";
  final static String TOTAL_SUGAR_WEIGHT = "TotalSugarWeight";
  final static String TOTAL_SODIUM_WEIGHT = "TotalSodiumWeight";
  final static String DIETARY_SCORE = "DietaryScore";
  final static String ALL_TEXT = "AllText";


  int nameIndex = 0;
  int idIndex = 1;
  int timeToMakeIndex = 2;
  int tagIndex = 5;
  int nutrientIndex = 6;
  int numberOfStepsIndex = 7;
  int stepsIndex = 8;
  int descriptionIndex = 9;
  int ingredientsIndex = 10;
  int numberOfIngredientsIndex = 11;

  final String urlString;

  IndexRecipes(HttpSolrClient c, String url) {
    this.c = c;
    this.urlString = url;
  }


  String[] formatString(String s) {
    return s.replace("[", "")
            .replace("]", "")
            .replace("'", "")
            .split(",");
  }

  @Override
  public void index(String fileName) {
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(
              fileName));
      String[] line;
      CSVReader csvReader = new CSVReader(reader);
      csvReader.readNext();
      int i = 0;
      while ((line = csvReader.readNext()) != null) {
        try {

          Recipe r = getRecipe(line);
          c.setParser(new XMLResponseParser());
          loadInSolr(r);
          System.out.println("Indexed :" + r.getName() + "\n");
          i++;
          if (i % 10000 == 0) {
            c.commit();
          }
        } catch (Exception e) {
          continue;
        }

      }

      reader.close();
      csvReader.close();

      c.commit();
    } catch (Exception e1) {
      e1.printStackTrace();
    }

  }

  private void loadInSolr(Recipe r) throws IOException, SolrServerException {
    SolrInputDocument document = new SolrInputDocument();
    document.addField(RECIPE_NAME, r.getName());
    document.addField(ID, r.getId());
    document.addField(DESCRIPTION, r.getDescription());
    document.addField(TIME_TO_MAKE, r.getMinutes());
    document.addField(RECIPE_TAGS, r.getTags());
    document.addField(TOTAL_CALORIES, r.getNutrients().getCalories());
    document.addField(TOTAL_CALORIES_DV, r.getNutrients().getPercentageDVCalories());
    document.addField(TOTAL_FATS_DV, r.getNutrients().getPercentageDVTotalFats());
    document.addField(TOTAL_FATS_WEIGHT, r.getNutrients().getWeightTotalFats());
    document.addField(TOTAL_CARBOHYDRATES_DV, r.getNutrients().getPercentageDVCarbohydrates());
    document.addField(TOTAL_CARBOHYDRATES_WEIGHT, r.getNutrients().getWeightCarbohydrates());
    document.addField(TOTAL_PROTEINS_DV, r.getNutrients().getPercentageDVProtein());
    document.addField(TOTAL_PROTEINS_WEIGHT, r.getNutrients().getWeightProtein());
    document.addField(TOTAL_SUGAR_DV, r.getNutrients().getPercentageDVSugar());
    document.addField(TOTAL_SUGAR_WEIGHT, r.getNutrients().getWeightSugar());
    document.addField(TOTAL_SATURATED_FATS_DV, r.getNutrients().getPercentageDVSaturatedFats());
    document.addField(TOTAL_SATURATED_FATS_WEIGHT, r.getNutrients().getWeightSaturatedFats());
    document.addField(TOTAL_SODIUM_DV, r.getNutrients().getPercentageDVSodium());
    document.addField(TOTAL_SODIUM_WEIGHT, r.getNutrients().getWeightSodium());
    document.addField(NUMBER_OF_STEPS, r.getNumberOfSteps());
    document.addField(STEPS, r.getSteps());
    document.addField(DESCRIPTION, r.getDescription());
    document.addField(INGREDIENTS, r.getIngredients());
    document.addField(NUMBER_OF_INGREDIENTS, r.getNumberOfIngredients());
    document.addField(DIETARY_SCORE, r.getDietaryScore());
    document.addField(ALL_TEXT,r.allText());
    c.add(document);
  }


  Recipe getRecipe(String[] elements) {
    Recipe r = Recipe.RecipeBuilder()
            .name(elements[nameIndex])
            .id(elements[idIndex])
            .minutes(Double.parseDouble(elements[timeToMakeIndex]))
            .tags(formatString(elements[tagIndex]))
            .nutrients(formatString(elements[nutrientIndex]))
            .numberOfSteps(Integer.parseInt(elements[numberOfStepsIndex]))
            .steps(formatString(elements[stepsIndex]))
            .description(elements[descriptionIndex])
            .ingredients(formatString(elements[ingredientsIndex]))
            .numberOfIngredients(Integer.parseInt(elements[numberOfIngredientsIndex]))
            .build();
    return r;
  }


}

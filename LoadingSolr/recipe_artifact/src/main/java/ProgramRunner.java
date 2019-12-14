import com.opencsv.CSVReader;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ProgramRunner {


  public static void main(String args[]) {
    String urlString = "http://localhost:8983/solr/recipe";
    HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
    GeneralIndexer i = new IndexRecipes(solr, "http://localhost:8983/solr/recipe");
    i.index("RAW_recipes.csv");
    /*deleteAllSolrData();*/


  }


  public static void deleteAllSolrData() {
    String urlString = "http://localhost:8983/solr/recipe";
    HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
    try {
      solr.deleteByQuery("*:*");
      solr.commit();
    } catch (Exception e) {
      throw new RuntimeException("Failed to delete data in Solr. "
              + e.getMessage(), e);
    }
  }


}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Indexer {

  public static void main(String args[]) {
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(
              "RAW_recipes.csv"));
      reader.readLine();
      String line = reader.readLine();
      System.out.println(line);
      String [] elements = line.split(",");
    for(String element : elements) {
      System.out.println(element);
    }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}

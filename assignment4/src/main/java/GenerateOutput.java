import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

/**
 * This is the class used to generate output
 */
public class GenerateOutput {

  private Map<String, File> map;
  private Map<Integer, String> indexMap;

  /**
   * This is the constructor used to create GenerateOuput object
   * @param map the map with the name of the grammar file as the key and the File object as the value
   * @param indexMap the map with the index of the grammar file as the key and the grammar file name as the value
   */
  public GenerateOutput(Map<String, File> map, Map<Integer, String> indexMap) {
    this.map = map;
    this.indexMap = indexMap;
  }

  /**
   * This method is used to interact with users and generate output.
   */
  public void dealWithOutput()  {
    Scanner sc = new Scanner(System.in);
    System.out.println("The following grammars are available: ");
    for (int index : indexMap.keySet()) {
      System.out.println((1 + index) +". "+ indexMap.get(index));
    }
    System.out.println("Which grammer would you like to use? (q to quit)");
    int opt = -1;
    while (sc.hasNextLine()) {
      String input = sc.nextLine();
      input = input.toLowerCase();
      try {
        if (isValidInteger(input)) {
          opt = Integer.parseInt(input);
          getSentence(opt);
          if (!helper(sc, opt)) {
            break;
          }
        } else if (input.trim().equals("q")) {
          System.out.println("See you ;)");
          break;
        } else {
          System.out.println("Invalid command, please try again!");
          continue;
        }
      } catch (NullPointerException e) {
        System.out.println("Unable to generate sentence at this time, please try again!");
        System.out.println("Which grammar would you like to use? (q to quit)");
      }
    }
  }

  /**
   * This is the helper method to help generate output
   * @param sc the Scanner object
   * @param opt the option user used to choose
   * @return true if the loop of the generation for the option ends
   */
  private boolean helper(Scanner sc, int opt)  {
    System.out.println("Would you like another sentence in this grammar format? (y/n)");
    while (sc.hasNextLine()) {
      String input = sc.nextLine();
      if (input.trim().equals("y")) {
        try {
          getSentence(opt);
        } catch (NullPointerException e) {
          System.out.println("There is no such sentences in this grammar format, please try again!");
          System.out.println("Would you like another sentence in this grammar format? (y/n)");
          continue;
        }
        System.out.println("Would you like another sentence in this grammar format? (y/n)");
      } else if (input.trim().equals("n")) {
        dealWithOutput();
        return false;
      } else {
        System.out.println("Invalid command, we asking again: would you like another? (y/n)");
      }
    }
    return true;
  }


  private boolean isValidInteger(String s) {
    try {
      int i = Integer.parseInt(s);
      if(i>indexMap.size()||i<=0)
        return false;
    } catch(NumberFormatException e) {
      return false;
    } catch(NullPointerException e) {
      return false;
    }
    return true;
  }

  private void getSentence(int opt) throws NullPointerException{
      GenerateSentence generator = new GenerateSentence(map.get(indexMap.get(opt-1)));
      String result = generator.generate();
      result = result.replaceAll("\\s\\.",".");
      result = result.replaceAll("\\s+,",", ");
      result = result.replaceAll(",\\s+",", ");
      result = result.toLowerCase(Locale.ROOT);
      result = result.substring(0,1).toUpperCase(Locale.ROOT) + result.substring(1);
      System.out.println(result);
  }
}

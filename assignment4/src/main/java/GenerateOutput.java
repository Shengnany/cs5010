import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class GenerateOutput {

  private Map<String, File> map;
  private Map<Integer, String> indexMap;

  public GenerateOutput(Map<String, File> map, Map<Integer, String> indexMap) {
    this.map = map;
    this.indexMap = indexMap;
  }

  public void dealWithOutput()  {
    Scanner sc = new Scanner(System.in);
    System.out.println("The following grammars are available: ");
    for (int index : indexMap.keySet()) {
      System.out.println((1 + index) + indexMap.get(index));
    }
    System.out.println("Which grammer would you like to use? (q to quit)");
    int opt = -1;
    //输入为数字
    while (sc.hasNextLine()) {
      String input = sc.nextLine();
      input = input.toLowerCase();
      try {
        if (isValidInteger(input)) {
          opt = Integer.parseInt(input);
          getSentence(opt);
          //如果要求输入是数字，但输入是y/n
          if (!helper(sc, opt)) {
            break;
          }
        } else if (input.trim().equals("q")) {
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

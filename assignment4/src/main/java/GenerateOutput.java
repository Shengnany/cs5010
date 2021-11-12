import java.io.File;
import java.util.Map;
import java.util.Scanner;

public class GenerateOutput {

  private Map<String, File> map;
  private Map<Integer, String> indexMap;

  public GenerateOutput(Map<String, File> map, Map<Integer, String> indexMap) {
    this.map = map;
    this.indexMap = indexMap;
  }

  public void dealWithOutput() throws InvalidArgumentException {
    Scanner sc = new Scanner(System.in);
    System.out.println("The following grammars are available: ");

    //先展示indexMap
    for (int index : indexMap.keySet()) {
      System.out.println((1 + index) + indexMap.get(index));
    }
    System.out.println("Which would you like to use? (q to quit)");

    int opt = -1;
    while (sc.hasNextLine()) {
      //user輸入一個數字 ，即可或者相對應的產生的語句
      String input = sc.nextLine();
      try {
        // shuzi
        if (isValidInteger(input)) {
          opt = Integer.parseInt(input);
          getSentence(opt);
          if (!helper(sc, opt)) {
            break;
          }
        } else if (input.trim().equals("q")) {
          break;
        } else {
          System.out.println("Invalid command, please try again!");
          continue;
        }
        //如果選擇y，繼續產生下一條結果
//        else if (input.trim().equals("y")) {
//          getSentence(opt);
//          System.out.println("Would you like another? (y/n)");
//        }

      } catch (NullPointerException e) {
        System.out.println("There is no such sentences, please try again!");
        System.out.println("Which would you like to use? (q to quit)");
      }

//      //如果選擇n，user要重新選擇grammer
//      if (input.trim().equals("n")) {
//        dealWithOutput();
//        break;
//      }
      //如果選擇q，則結束服務

    }
  }

  private boolean helper(Scanner sc, int opt) throws InvalidArgumentException {
    System.out.println("Would you like another? (y/n)");
    while (sc.hasNextLine()) {
      String input = sc.nextLine();
      if (input.trim().equals("y")) {
        try {
          getSentence(opt);
        } catch (NullPointerException e) {
          System.out.println("There is no such sentences, please try again!");
          System.out.println("Would you like another? (y/n)");
          continue;
        }
        System.out.println("Would you like another? (y/n)");
        //如果選擇n，user要重新選擇grammer
      } else if (input.trim().equals("n")) {
        dealWithOutput();
        return false;
      } else {
        System.out.println("Invalid command, would you like another? (y/n)");
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
      System.out.println(generator.generate());
  }
}

// 输入选项是空白
// 空白
// 不存在的
// 大小写
// 句点
// 需要输入y/n的时候 输入数字
// 需要输入数字的时候 输入y/n


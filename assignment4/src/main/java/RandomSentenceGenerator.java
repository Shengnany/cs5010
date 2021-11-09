import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class RandomSentenceGenerator {
    private static Map<String, File> map;
    private static Map<Integer, String> indexMap;
//    private static  String OPTIONS = "The following grammars are available: \n" +
//            "1. Insults \n" +
//            "2. Term Paper Generator \n" +
//            "3. Dear John letter \n" +
//            "\n" +
//            "Which would you like to use? (q to quit)\n";
    public static void main(String[] args) throws IOException, InvalidArgumentException, ParseException {
        if(args == null || args.length == 0){
            throw new InvalidArgumentException("No such directory");
        }
        String dirStr = args[0].trim();
        System.out.println("Loading grammars...");
        map = ReadFile.getFileMap(dirStr);
        indexMap = ReadFile.getIndexMap(map);
        Scanner sc = new Scanner(System.in);
        System.out.println("The following grammars are available: ");
        for(int index: indexMap.keySet()){
            System.out.println((1+index)+indexMap.get(index));
        }
        String input  = sc.nextLine();
        if(isInteger(input)){
            int o = Integer.parseInt(input);
            GenerateSentence generator = new GenerateSentence(map.get(indexMap.get(o-1)));
            System.out.println(generator.generate());
        }
        else if(input.trim().equals("q")){
            return;
        }
        else{
            throw new InvalidArgumentException("Invalid input");
        }

    }
    // 空白
    // 不存在的
    // case??
    //daxiaoxie
    // 句点

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }
}

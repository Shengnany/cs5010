import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Map;

/**
 * This is class that contains the main method for generating the sentence
 */
public class RandomSentenceGenerator {
    private static Map<String, File> map;
    private static Map<Integer, String> indexMap;

    /**
     * the main method to generate the sentence
     * @param args array that includes the directory of the grammar files
     * @throws InvalidArgumentException if the argument is null or is empty
     */
    public static void main(String[] args) throws InvalidArgumentException {
        if(args == null || args.length == 0){
            throw new InvalidArgumentException("No such directory");
        }
        String dirStr = args[0].trim();
        System.out.println("Loading grammars...");
        map = ReadFile.getFileMap(dirStr);
        indexMap = ReadFile.getIndexMap(map);
        GenerateOutput go = new GenerateOutput(map,indexMap);
        go.dealWithOutput();
    }

}

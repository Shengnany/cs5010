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

    public static void main(String[] args) throws InvalidArgumentException {
        if(args == null || args.length == 0){
            throw new InvalidArgumentException("No such directory");
        }
        String dirStr = args[0].trim();
//        String dirStr = "src/main/resources";
//        System.out.println("Loading grammars...");

        //key:grammer_title value: the json file
        map = ReadFile.getFileMap(dirStr);
        //把json file的key 存成一個index從0開始的map
        indexMap = ReadFile.getIndexMap(map);
        GenerateOutput go = new GenerateOutput(map,indexMap);
        go.dealWithOutput();
    }

}

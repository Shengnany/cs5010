import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This is the class that contains static methods that read in grammar files.
 */
public class ReadFile {

    /**
     * This method is used to get the filename-to-File_Object map
     * @param dirStr the directory that contains grammar files
     * @return the hash map with grammar file name as the key and File object as the value
     */
    public static Map<String, File> getFileMap(String dirStr){
        Map<String, File> map = new HashMap<>();
        File dir = new File(dirStr);
        File[] files = dir.listFiles();
        JSONParser parser = new JSONParser();
        for(File file: files){
            if(file.isFile() && file.getName().endsWith(".json")){
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    JSONObject obj = (JSONObject)parser.parse(reader);
                    if(!obj.containsKey("grammarTitle")) continue;
                    else{
                        map.put((String)obj.get("grammarTitle"),file);
                    }
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    /**
     * This method is used to get index-to-filename map;
     * @param map the hash map with grammar file name as the key and File object as the value
     * @return the hash map with index of the file as the key and grammar file name as the value
     */
    public static Map<Integer, String> getIndexMap( Map<String, File> map){
        Map<Integer, String> indexMap = new HashMap<>();
        int index = 0;
        for(String file: map.keySet()) {
            indexMap.put(index,file);
            index++;
        }
        return indexMap;
    }

}

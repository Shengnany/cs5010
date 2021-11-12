import java.util.Iterator;
import java.util.Locale;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contains method that is used to generate sentence
 */
public class GenerateSentence {
    File file;
    Random random;
    JSONParser parser;
    BufferedReader reader;
    JSONObject obj;

    /**
     * This is the constructor of the GenerateSentence class
     * @param file the grammar file to help generate the sentence
     */
    public GenerateSentence(File file){
        this.file = file;
        random = new Random();
        try {
            parser = new JSONParser();
            reader = new BufferedReader(new FileReader(file));
            obj = (JSONObject)parser.parse(reader);
            JSONArray jsonArray = (JSONArray)obj.get("start");
            String res = recurGenerate(jsonArray);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to initiate the recurrent generation of the sentence
     * @return the result: generated string
     * @throws NullPointerException if it does not contain the key word
     */
    public String generate() throws NullPointerException {
        JSONArray jsonArray = (JSONArray)obj.get("start");
        String res = recurGenerate(jsonArray);
        return res;
    }

    /**
     * This method is used to generate a sentence recurrently
     * @param arr the json array representing the options for the recurrence search,
     *           a randomly selected key will be chosen from the array.
     * @return the output sentence for the generation
     * @throws NullPointerException if the grammar file does not exist the key.
     */
    private String recurGenerate(JSONArray arr) throws NullPointerException {
        int r = random.nextInt(arr.size());
        Object replace = arr.get(r);
        String str = (String) replace;
        String regreplace = "<\\w+.*?>";
        Pattern compile1 = Pattern.compile(regreplace);
        Matcher matcher1 = compile1.matcher((String)str);
        while (matcher1.find()) {
            String rep = matcher1.group(0).toLowerCase(Locale.ROOT);
            JSONArray ans = (JSONArray)getIgnoreCase(obj,rep.substring(1, rep.length() - 1));
                //obj.get(rep.substring(1, rep.length() - 1));
            str = str.replaceFirst(rep, recurGenerate(ans));
        }
        str.replaceAll("\\s+"," ");
        return str;
    }

    /**
     * This method is used to get the JSONArray of the non-terminal or terminal words
     * @param jobj the file read in as JSON object
     * @param key the non-terminal or terminal words
     * @return the JSONArray object
     */
    public Object getIgnoreCase(JSONObject jobj, String key) {
        Iterator<String> iter = jobj.keySet().iterator();
        while (iter.hasNext()) {
            String key1 = iter.next();
            if (key1.equalsIgnoreCase(key)) {
                return jobj.get(key1);
            }
        }
        return null;

    }
}

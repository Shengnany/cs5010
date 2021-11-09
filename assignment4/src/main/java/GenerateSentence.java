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

public class GenerateSentence {
    File file;
    Random random;
    JSONParser parser;
    BufferedReader reader;
    JSONObject obj;
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
    public String generate(){
        JSONArray jsonArray = (JSONArray)obj.get("start");
        String res = recurGenerate(jsonArray).toString();
        return res;
    }

    private String recurGenerate(JSONArray arr){
        int r = random.nextInt(arr.size());
        Object replace = arr.get(r);
        String str = (String) replace;
        String regreplace = "<\\w+.*?>";
        Pattern compile1 = Pattern.compile(regreplace);
        Matcher matcher1 = compile1.matcher((String)str);
        while (matcher1.find()) {
            String rep = matcher1.group(0);
            str = str.replaceFirst(rep, recurGenerate((JSONArray) obj.get(rep.substring(1, rep.length() - 1))));
        }
        return str;
    }
}

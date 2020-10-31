package dk.kaloyan.mingalgeleg;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dk.kaloyan.entities.Word;

public class ExtractWords {
    private JSONArray jsonArray;

    public ExtractWords(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }
    public List<Word> extract() {
        List<Word> responseWords = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = null;
            try {
                json = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Iterator<String> keys = json.keys();
            Word word = Word.Builder().build();
            while (keys.hasNext()) {
                String key = keys.next();
                try {
                    //System.out.println("Key :" + key + "  Value :" + json.get(key));
                    if(key.equalsIgnoreCase("id"))
                        word.setId((String) json.get(key));
                    else if(key.equalsIgnoreCase("val"))
                        word.setVal((String) json.get(key));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            responseWords.add(word);

        }
        return responseWords;
    }
}

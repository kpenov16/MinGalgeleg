package dk.kaloyan.async;


import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;

import dk.kaloyan.entities.Word;
import dk.kaloyan.mingalgeleg.ExtractWords;

public class LongRunningTask implements Callable<List<Word>> {
    private String url;

    public LongRunningTask(String url){

        this.url = url;
    }

    @Override
    public List<Word> call() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        StringBuilder sb = new StringBuilder();
        String linje = br.readLine();
        while (linje != null) {
            sb.append(linje);
            linje = br.readLine();
        }
        br.close();
        JSONArray jsonArray = new JSONArray(sb.toString());
        return new ExtractWords(jsonArray).extract();
    }
}

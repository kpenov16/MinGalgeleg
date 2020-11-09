package dk.hanggame.impl.downloaders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import dk.hanggame.downloaders.ProcessObservable;
import dk.hanggame.downloaders.ProcessObserver;
import dk.hanggame.downloaders.WordsDownloader;
import dk.hanggame.entities.Word;

public class CitiesWordsDownloaderImpl implements WordsDownloader {
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private List<Word> extractWords(JSONArray jsonArray) {
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

    @Override
    public void execute() {
        executor.execute( ()->{
                    observers.stream().forEach(o-> o.starting());

                    try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL("https://kpv-events.herokuapp.com/guesswords/rand/10").openStream()))) {

                        observers.stream().forEach( o-> o.pending() );

                        List<String> words = extractWords(new JSONArray( br.lines().collect(Collectors.joining()) )).stream().map(w->w.getVal()).collect(Collectors.toList());

                        observers.stream().forEach( o-> o.processed(new ArrayList<String>(words)) );
                    } catch (Exception e) {
                        e.printStackTrace();
                        new ArrayList<Word>(){{add(Word.Builder().withVal( "activate internet").build()); }};
                    }
                }
        );
        executor.shutdown();
        //executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    List<ProcessObserver> observers = new ArrayList<>();
    @Override
    public void addProcessObserver(ProcessObserver observer){
        observers.add(observer);
    }

    @Override
    public void removeProcessObserver(ProcessObserver observer){
        observers.remove(observer);
    }
}


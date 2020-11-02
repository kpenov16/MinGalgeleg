package dk.kaloyan.gateways;

import android.os.Handler;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import dk.kaloyan.core.usecases.playgame.WordsGateway;

public class DRWordsGatewayImpl implements WordsGateway {
    private  ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    private ArrayList<String> words = new ArrayList<String>();

    public String getPageAsString(String url) throws IOException {
        InputStream inputStream = new URL(url).openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String linje = br.readLine();
        while (linje != null) {
            sb.append(linje + "\n");
            linje = br.readLine();
        }
        String str = sb.toString();
        inputStream.close();
        return str;
    }


    @Override
    public void getRandomWords(int numberOfWords, Consumable consumable) {

    }

    /**
     * Hent ord fra DRs forside (https://dr.dk)
     */
    public List<String> getWords() throws Exception {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{
            String data = "bil computer programmering motorvej busrute gangsti skovsnegl solsort nitten";
            try {
                data = getPageAsString("https://dr.dk");
                data = data.substring(data.indexOf("<body")). // fjern headere
                        replaceAll("<.+?>", " ").toLowerCase(). // fjern tags
                        replaceAll("&#198;", "æ"). // erstat HTML-tegn
                        replaceAll("&#230;", "æ"). // erstat HTML-tegn
                        replaceAll("&#216;", "ø"). // erstat HTML-tegn
                        replaceAll("&#248;", "ø"). // erstat HTML-tegn
                        replaceAll("&oslash;", "ø"). // erstat HTML-tegn
                        replaceAll("&#229;", "å"). // erstat HTML-tegn
                        replaceAll("[^a-zæøå]", " "). // fjern tegn der ikke er bogstaver
                        replaceAll(" [a-zæøå] "," "). // fjern 1-bogstavsord
                        replaceAll(" [a-zæøå][a-zæøå] "," "); // fjern 2-bogstavsord

                setWords(new HashSet<String>(Arrays.asList(data.split(" "))));
            } catch (IOException e) {
                e.printStackTrace();
                setWords(new HashSet<String>(){{add("activate internet");}});
            }



        });
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);



        return words;
    }

    synchronized private void setWords(HashSet<String> newWords){
        words.clear();
        words.addAll(newWords);
    }

}

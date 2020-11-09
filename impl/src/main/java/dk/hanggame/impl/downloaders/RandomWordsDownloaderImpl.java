package dk.hanggame.impl.downloaders;

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
import java.util.stream.Collectors;

import dk.hanggame.downloaders.ProcessObservable;
import dk.hanggame.downloaders.ProcessObserver;
import dk.hanggame.downloaders.WordsDownloader;


public class RandomWordsDownloaderImpl implements WordsDownloader {
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private ArrayList<String> words = new ArrayList<String>();

    private String getPageAsString(String url) throws IOException {
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
    public void execute() {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{

            observers.stream().forEach(o->o.starting());

            String data = "bil computer programmering motorvej busrute gangsti skovsnegl solsort nitten";
            try {
                data = getPageAsString("https://dr.dk");

                observers.stream().forEach(o->o.pending());

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

                setWords(new HashSet<String>( Arrays.asList(data.split(" ")).stream().filter(w->w.length()>4).collect(Collectors.toList()) ));
            } catch (IOException e) {
                e.printStackTrace();
                setWords(new HashSet<String>(){{add("activate internet");}});
            }

        });
        executor.shutdown();
        //executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    synchronized private void setWords(HashSet<String> newWords){
        words.clear();
        words.addAll(newWords);

        observers.stream().forEach(o->o.processed(words));
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


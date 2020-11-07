package dk.kaloyan.android.startgame;

import java.util.ArrayList;
import java.util.List;

public class WordsDownloaderFactoryImpl implements  WordsDownloaderFactory{
    private final String CITIES = "Cities";
    private final String RANDOM = "Random";
    List<String> categories = new ArrayList<String>(){{add(RANDOM);add(CITIES);}};
    public List<String> getCategories() {
        return categories;
    }

    @Override
    public WordsDownloader make(String category) {
        if(category != null && category.equalsIgnoreCase(CITIES)){
            return new HEROKUWordsDownloader();
        }else if(category != null && category.equalsIgnoreCase(RANDOM)){
            return new DRWordsDownloader();
        }else
            return null;
    }

}

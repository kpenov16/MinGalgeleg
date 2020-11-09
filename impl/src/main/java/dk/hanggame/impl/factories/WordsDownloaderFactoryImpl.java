package dk.hanggame.impl.factories;

import java.util.ArrayList;
import java.util.List;

import dk.hanggame.downloaders.WordsDownloader;
import dk.hanggame.factories.WordsDownloaderFactory;
import dk.hanggame.impl.downloaders.RandomWordsDownloaderImpl;
import dk.hanggame.impl.downloaders.CitiesWordsDownloaderImpl;

public class WordsDownloaderFactoryImpl implements WordsDownloaderFactory {
    private final String CITIES = "Cities";
    private final String RANDOM = "Random";
    List<String> categories = new ArrayList<String>(){{add(RANDOM);add(CITIES);}};
    public List<String> getCategories() {
        return categories;
    }

    @Override
    public WordsDownloader make(String category){//, HandlerWrapper wrapper) {
        if(category != null && category.equalsIgnoreCase(CITIES)){
            return new CitiesWordsDownloaderImpl();
        }else if(category != null && category.equalsIgnoreCase(RANDOM)){
            return new RandomWordsDownloaderImpl();
        }else
            return null;
    }

}

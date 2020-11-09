package dk.hanggame.factories;

import java.util.List;

public interface WordsDownloaderFactory{
    WordsDownloader make(String category);
    List<String> getCategories();
}


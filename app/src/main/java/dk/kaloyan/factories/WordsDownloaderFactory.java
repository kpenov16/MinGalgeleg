package dk.kaloyan.factories;

import java.util.List;

public interface WordsDownloaderFactory{
    WordsDownloader make(String category);
    List<String> getCategories();
}


package dk.hanggame.factories;

import java.util.List;

import dk.hanggame.downloaders.WordsDownloader;

public interface WordsDownloaderFactory{
    WordsDownloader make(String category);
    List<String> getCategories();
}


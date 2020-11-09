package dk.hanggame.downloaders;

import java.util.ArrayList;

public interface ProcessObserver {
    void starting();
    void pending();
    void processed(ArrayList<String> words);
}
package dk.hanggame.factories;

import java.util.ArrayList;

public interface ProcessObserver {
    void starting();
    void pending();
    void processed(ArrayList<String> words);
}
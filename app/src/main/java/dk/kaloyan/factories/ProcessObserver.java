package dk.kaloyan.factories;

import java.util.ArrayList;

public interface ProcessObserver {
    void starting();
    void pending();
    void processed(ArrayList<String> words);
}
package dk.kaloyan.core;

import java.util.List;

import dk.kaloyan.entities.Word;

public interface WordsGateway {
    void getRandomWords(int numberOfWords, Consumable consumable);
    interface Consumable{
        void consume(List<Word> result);
    }
}

package dk.kaloyan.core;

import java.util.List;

import dk.kaloyan.entities.Word;

public interface WordsGateway {
    void getRandomWords(int numberOfWords, Consumable consumable);

    List<String> getWords() throws Exception;

    interface Consumable{
        void consume(List<Word> result);
    }

}

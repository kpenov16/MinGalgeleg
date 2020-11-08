package dk.kaloyan.core.usecases.playgame;

import java.util.List;
import dk.kaloyan.entities.Word;

public interface WordsGateway {
    List<String> getWords();
}

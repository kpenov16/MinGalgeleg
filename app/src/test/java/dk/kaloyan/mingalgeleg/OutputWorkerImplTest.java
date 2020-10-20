package dk.kaloyan.mingalgeleg;

import org.junit.Test;

import java.util.ArrayList;

import dk.kaloyan.entities.Game;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class OutputWorkerImplTest {
    @Test
    public void givenWrongGuessWithCorrectGuesses_returnFormattedStringShowingOnlyTheGuessedLetters() {
        //setup
        String word = "bananas";
        ArrayList<String> guessed = new ArrayList<String>(){{add("a"); add("b");}};
        String visibleWord = "";
        int wrongGuesses = 2;
        GameViewModel vm = new GameViewModel();
        OutputWorkerImpl outputWorker = new OutputWorkerImpl(new FakeGameView(), vm);

        //act
        Game game = new Game.Builder()
                .withUsedLetters(guessed)
                .withWordToGuess(word)
                .withWrongLettersCount(wrongGuesses)
                .build();
        outputWorker.presentResult(game);

        //assert
        assertEquals(
                String.format(outputWorker.CURRENT_GUESS_PLAY_GAME, "ba*a*a*", wrongGuesses, guessed.toString()),
                vm.currentGuess);
    }

    class FakeGameView implements GameView{
        @Override
        public void show(GameViewModel viewModel) {

        }
    }
}
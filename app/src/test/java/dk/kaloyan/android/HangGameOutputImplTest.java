package dk.kaloyan.android;

import org.junit.Test;

import java.util.ArrayList;

import dk.kaloyan.android.playgame.HangGameOutputImpl;
import dk.kaloyan.android.playgame.HangGameView;
import dk.kaloyan.android.playgame.HangGameViewModel;
import dk.hanggame.usecases.entities.Game;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class HangGameOutputImplTest {
    @Test
    public void givenWrongGuessWithCorrectGuesses_returnFormattedStringShowingOnlyTheGuessedLetters() {
        //setup
        String word = "bananas";
        ArrayList<String> guessed = new ArrayList<String>(){{add("a"); add("b");}};
        String visibleWord = "";
        int wrongGuesses = 2;
        HangGameViewModel vm = new HangGameViewModel();
        HangGameOutputImpl outputWorker = new HangGameOutputImpl(new FakeHangGameView(), vm);

        //act
        Game game = Game.Builder()
                .withUsedLetters(guessed)
                .withWordToGuess(word)
                .withWrongLettersCount(wrongGuesses)
                .build();
        outputWorker.present(game);

        //assert
        assertEquals(
                String.format(outputWorker.CURRENT_GUESS_PLAY_GAME, "ba*a*a*", wrongGuesses, guessed.toString()),
                vm.currentGuess);
    }

    class FakeHangGameView implements HangGameView {
        @Override
        public void show(HangGameViewModel viewModel) {

        }
    }
}
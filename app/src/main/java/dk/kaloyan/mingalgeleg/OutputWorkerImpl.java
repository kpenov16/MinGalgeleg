package dk.kaloyan.mingalgeleg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dk.kaloyan.core.OutputPort;
import dk.kaloyan.entities.Game;

public class OutputWorkerImpl implements OutputPort {
    private GameView view;
    private GameViewModel viewModel;

    public OutputWorkerImpl(GameView view, GameViewModel viewModel) {
        this.view = view;
        this.viewModel = viewModel;
    }


    @Override
    public void presentResult(Game game) {
        viewModel.playerName = "Nickname: " + game.getPlayer().getNickname();
        viewModel.wrongCount = game.getWrongLettersCount();

        viewModel.currentGuess = String.format(CURRENT_GUESS_PLAY_GAME, hideNotGuessed(game.getWordToGuess(), game.getUsedLetters(), '*'), game.getWrongLettersCount(), game.getUsedLetters().toString());
        //"Ord: " + game.getWordToGuess() + "\nForkerte Bogstaver: " + game.getWrongLettersCount() + "\nBrugte Bogstaver: " + game.getUsedLetters().toString();
        view.show(viewModel);
    }

    @Override
    public void presentWinGame(String ordet) {
        viewModel.currentGuess = "You WIN!!!\nOrdet var: " + ordet;
        viewModel.isWon = true;
        view.show(viewModel);
    }

    @Override
    public void presentLoseGame(String ordet, int antalForkerteBogstaver, ArrayList<String> brugteBogstaver) {
        viewModel.wrongCount = antalForkerteBogstaver;
        viewModel.currentGuess = String.format(CURRENT_GUESS_LOSE_GAME, ordet, antalForkerteBogstaver, brugteBogstaver.toString());
        view.show(viewModel);
    }

    protected final String CURRENT_GUESS_LOSE_GAME = "You LOSE!!!\nOrdet var: %s\nForkerte gæt: %d\nBrugte bogstaver: %s";
    protected final String CURRENT_GUESS_PLAY_GAME = "Ord: %s\nForkerte gæt: %d\nBrugte bogstaver: %s";

    private String hideNotGuessed(String word, List<String> visible, char replaceWith) {
        final Map<String,String> map = visible.stream().distinct().collect(Collectors.toMap(s->s, s->s));
        final String visibleWord = word.chars()
                .map(c -> map.get(Character.toString((char)c)) != null ? c : replaceWith)
                .collect(StringBuilder::new, (sb,c) -> sb.append((char)c), StringBuilder::append).toString();
        return visibleWord;
    }

    protected GameViewModel getViewModel() {
        return viewModel;
    }

    protected void setViewModel(GameViewModel viewModel) {
        this.viewModel = viewModel;
    }
}

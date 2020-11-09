package dk.kaloyan.android.playgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dk.kaloyan.android.ViewablePlayer;
import dk.hanggame.usecases.playgame.HangGameOutputPort;
import dk.hanggame.usecases.entities.Game;

public class HangGameOutputImpl implements HangGameOutputPort {
    private static final String USED_LETTERS_SEPARATOR = ", ";
    private static final Character HIDDEN_LETTERS_PLACEHOLDER = '*';
    private HangGameView view;
    private HangGameViewModel viewModel;

    public HangGameOutputImpl(HangGameView view, HangGameViewModel viewModel) {
        this.view = view;
        this.viewModel = viewModel;
    }

    @Override
    public void present(Game game) {
        final String nickname = game.getPlayer().getNickname();
        if(viewModel.viewablePlayer == null)
            viewModel.viewablePlayer = new ViewablePlayer(nickname, 0 , 0);
        viewModel.playerName = "Nickname: " + nickname;
        viewModel.wrongCount = game.getWrongLettersCount();

        viewModel.currentGuess = String.format(
                CURRENT_GUESS_PLAY_GAME,
                hideNotGuessed(game.getWordToGuess(), game.getUsedLetters(), HIDDEN_LETTERS_PLACEHOLDER),
                game.getWrongLettersCount(),
                formatLetters(game.getUsedLetters(), USED_LETTERS_SEPARATOR) );
        view.show(viewModel);
    }
    private String formatLetters(final List<String> letters, final String separator){
        return letters.stream().collect(Collectors.joining(separator));
    }
    @Override
    public void presentWin(String word) {
        viewModel.currentGuess = "You WIN!!!\nThe word was: " + word;
        viewModel.isWon = true;
        viewModel.viewablePlayer.setWins(viewModel.viewablePlayer.getWins() + 1);
        view.show(viewModel);
    }

    @Override
    public void presentLose(String word, int countWrongLetters, ArrayList<String> usedLetters) {
        viewModel.wrongCount = countWrongLetters;
        viewModel.currentGuess = String.format(
                CURRENT_GUESS_LOSE_GAME,
                word,
                countWrongLetters,
                formatLetters(usedLetters, USED_LETTERS_SEPARATOR));
        viewModel.viewablePlayer.setLoses(viewModel.viewablePlayer.getLoses() + 1);
        view.show(viewModel);
    }

    public final String CURRENT_GUESS_LOSE_GAME = "You LOSE!!!\nThe word was: %s\nWrong guesses: %d\nUsed letters: \n%s";
    public final String CURRENT_GUESS_PLAY_GAME = "Word: %s\nWrong guesses: %d\nUsed letters: \n%s";

    private String hideNotGuessed(String word, List<String> visible, char replaceWith) {
        final Map<String,String> map = visible.stream().distinct().collect(Collectors.toMap(s->s.toLowerCase(), s->s));
        final String visibleWord = word.chars()
                .map(c -> map.get(Character.toString((char)c).toLowerCase()) != null ? c : replaceWith)
                .collect(StringBuilder::new, (sb,c) -> sb.append((char)c), StringBuilder::append).toString();
        return visibleWord;
    }

    protected HangGameViewModel getViewModel() {
        return viewModel;
    }

    protected void setViewModel(HangGameViewModel viewModel) {
        this.viewModel = viewModel;
    }
}

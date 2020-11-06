package dk.kaloyan.android.playgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dk.kaloyan.android.ViewablePlayer;
import dk.kaloyan.core.usecases.playgame.HangGameOutputPort;
import dk.kaloyan.entities.Game;

public class HangGameOutputImpl implements HangGameOutputPort {
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

        viewModel.currentGuess = String.format(CURRENT_GUESS_PLAY_GAME, hideNotGuessed(game.getWordToGuess(), game.getUsedLetters(), '*'), game.getWrongLettersCount(), game.getUsedLetters().toString());
        //"Ord: " + game.getWordToGuess() + "\nForkerte Bogstaver: " + game.getWrongLettersCount() + "\nBrugte Bogstaver: " + game.getUsedLetters().toString();
        view.show(viewModel);
    }

    @Override
    public void presentWin(String ordet) {
        viewModel.currentGuess = "You WIN!!!\nOrdet var: " + ordet;
        viewModel.isWon = true;
        viewModel.viewablePlayer.setWins(viewModel.viewablePlayer.getWins() + 1);
        view.show(viewModel);
    }

    @Override
    public void presentLose(String ordet, int antalForkerteBogstaver, ArrayList<String> brugteBogstaver) {
        viewModel.wrongCount = antalForkerteBogstaver;
        viewModel.currentGuess = String.format(CURRENT_GUESS_LOSE_GAME, ordet, antalForkerteBogstaver, brugteBogstaver.toString());
        viewModel.viewablePlayer.setLoses(viewModel.viewablePlayer.getLoses() + 1);
        view.show(viewModel);
    }

    public final String CURRENT_GUESS_LOSE_GAME = "You LOSE!!!\nOrdet var: %s\nForkerte gæt: %d\nBrugte bogstaver: %s";
    public final String CURRENT_GUESS_PLAY_GAME = "Ord: %s\nForkerte gæt: %d\nBrugte bogstaver: %s";

    private String hideNotGuessed(String word, List<String> visible, char replaceWith) {
        final Map<String,String> map = visible.stream().distinct().collect(Collectors.toMap(s->s, s->s));
        final String visibleWord = word.chars()
                .map(c -> map.get(Character.toString((char)c)) != null ? c : replaceWith)
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

package dk.kaloyan.mingalgeleg;

import java.util.ArrayList;

import dk.kaloyan.core.OutputPort;

public class OutputWorkerImpl implements OutputPort {
    private GameView view;
    private GameViewModel viewModel;

    public OutputWorkerImpl(GameView view, GameViewModel viewModel) {
        this.view = view;
        this.viewModel = viewModel;
    }


    @Override
    public void presentResult(String playerName, String synligtOrd, int antalForkerteBogstaver, ArrayList<String> brugteBogstaver) {
        viewModel.playerName = "Name: " + playerName;
        viewModel.wrongCount = antalForkerteBogstaver;
        viewModel.currentGuess = "Synligt Ord: " + synligtOrd + "\nForkerte Bogstaver: " + antalForkerteBogstaver + "\nBrugte Bogstaver: " + brugteBogstaver.toString();
        view.show(viewModel);
    }

    @Override
    public void presentWinGame(String ordet) {
        viewModel.currentGuess = "You WIN!!!\nOrdet var: " + ordet;
        view.show(viewModel);
    }

    @Override
    public void presentLoseGame(String ordet, int antalForkerteBogstaver, ArrayList<String> brugteBogstaver) {
        viewModel.wrongCount = antalForkerteBogstaver;
        viewModel.currentGuess = "You LOSE!!!\nOrdet var: " + ordet + "\nForkerte gæt: " + antalForkerteBogstaver + "\nBrugte bogstaver: " + brugteBogstaver.toString();
        viewModel.restartButton = true;
        view.show(viewModel);
    }
}

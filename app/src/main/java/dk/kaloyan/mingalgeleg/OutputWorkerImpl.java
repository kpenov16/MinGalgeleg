package dk.kaloyan.mingalgeleg;

import java.util.stream.Collectors;

import dk.kaloyan.core.OutputPort;

import static java.util.stream.Stream.generate;

public class OutputWorkerImpl implements OutputPort {
    private GameView view;
    private GameViewModel viewModel;

    public OutputWorkerImpl(GameView view, GameViewModel viewModel) {
        this.view = view;
        this.viewModel = viewModel;
    }

    @Override
    public void presentSecret(String secret) {
        if(viewModel.firstGuess){
            viewModel.currentGuess = generate(()->"*").limit(secret.length()).collect(Collectors.joining());
            view.show(viewModel);
        }

    }
}

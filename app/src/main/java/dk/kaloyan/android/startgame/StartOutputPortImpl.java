package dk.kaloyan.android.startgame;

import dk.kaloyan.core.usecases.startgame.StartOutputPort;

public class StartOutputPortImpl implements StartOutputPort {
    private StartView startView;
    @Override
    public void presentSetup() {
        StartViewModel startViewModel = new StartViewModel();
        startViewModel.setWordSourceChosen(false);
        startViewModel.chooseWordSourceMessage = "Choose words source and player name before start";
        startViewModel.wordSources = new String[]{"choose source", "From DR", "From Heroku"};

        startView.showChooseWordSource(startViewModel);
    }

    public StartView getStartView() {
        return startView;
    }

    public void setStartView(StartView startView) {
        this.startView = startView;
    }
}

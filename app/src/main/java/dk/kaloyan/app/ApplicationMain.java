package dk.kaloyan.app;

import android.app.Application;

import dk.kaloyan.android.startgame.StartActivity;
import dk.kaloyan.android.startgame.WordsDownloaderFactoryImpl;
import dk.kaloyan.core.usecases.playgame.HangGameInteractorImpl;
import dk.kaloyan.gateways.DRWordsGatewayImpl;
import dk.kaloyan.gateways.OneWordHangGameLogicImpl;

public class ApplicationMain extends Application {
    public HangGameInteractorImpl gameInteractor;

    public HangGameInteractorImpl getGameInteractor() {
        return gameInteractor;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    private void initialize() {
        StartActivity.wordsDownloaderFactory = new WordsDownloaderFactoryImpl();



        gameInteractor = new HangGameInteractorImpl();
        gameInteractor.setGalgelogikGateway(new OneWordHangGameLogicImpl());

        //the specific DRWordsGatewayImpl implements the generic WordsGateway
        //we move step by step the logic away from the Galgelogik so we can achieve SOLID
        //here the call is still holding the main thread but the gateway has the single responsibility to deliver the words
        //and by implementing the interface the dependency is inverted letting the interactor free of a specific dependency
        gameInteractor.setWordsGateway(new DRWordsGatewayImpl());


        /*inputWorker = new InputWorkerImpl(
                new GameInteractorImpl( new OutputWorkerImpl(this, new GameViewModel()), )
        );*/
    }
}

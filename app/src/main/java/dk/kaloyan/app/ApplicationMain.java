package dk.kaloyan.app;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import dk.hanggame.usecases.playgame.GameGateway;
import dk.kaloyan.android.startgame.StartActivity;
import dk.hanggame.usecases.playgame.HangGameInteractorImpl;
import dk.hanggame.usecases.playgame.WordsGateway;
import dk.hanggame.impl.factories.WordsDownloaderFactoryImpl;
import dk.kaloyan.gateways.GameGatewayImpl;
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
        gameInteractor.setGameLogicGateway(new OneWordHangGameLogicImpl());

        gameInteractor.setGameGateway( newGameGateway() );

        //the specific DRWordsGatewayImpl implements the generic WordsGateway
        //we move step by step the logic away from the Galgelogik so we can achieve SOLID
        //here the call is still holding the main thread but the gateway has the single responsibility to deliver the words
        //and by implementing the interface the dependency is inverted letting the interactor free of a specific dependency
        gameInteractor.setWordsGateway(new WordsGateway(){

            @Override
            public List<String> getWords(){
                return new ArrayList<String>(){{add("something");add("wrong");}};
            }
        });


        /*inputWorker = new InputWorkerImpl(
                new GameInteractorImpl( new OutputWorkerImpl(this, new GameViewModel()), )
        );*/
    }
    public GameGateway newGameGateway(){
        return new GameGatewayImpl(KEY->getSharedPreferences(KEY, Activity.MODE_PRIVATE));
    }
}

package dk.kaloyan.mingalgeleg;

import android.app.Application;

import dk.kaloyan.core.GameInteractorImpl;
import dk.kaloyan.galgeleg.Galgelogik;
import dk.kaloyan.galgeleg.MinGalgelogikImpl;

public class ApplicationMain extends Application {
    public GameInteractorImpl gameInteractor;

    public GameInteractorImpl getGameInteractor() {
        return gameInteractor;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    private void initialize() {
        gameInteractor = new GameInteractorImpl();
        gameInteractor.setGalgelogikGateway(new MinGalgelogikImpl(new Galgelogik()));


        /*inputWorker = new InputWorkerImpl(
                new GameInteractorImpl( new OutputWorkerImpl(this, new GameViewModel()), )
        );*/
    }
}

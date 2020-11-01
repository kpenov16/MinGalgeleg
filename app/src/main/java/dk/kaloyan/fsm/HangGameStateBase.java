package dk.kaloyan.fsm;

import dk.kaloyan.mingalgeleg.StartActivity;

public abstract class HangGameStateBase implements HangGameState {
    public static InitState INIT = new InitState();
    public static PlayingState PLAYING = new PlayingState();
    public static WinState WIN = new WinState();
    public static LoseState LOSE = new LoseState();

    public abstract void setContext(HangGameState hangGameState);
}
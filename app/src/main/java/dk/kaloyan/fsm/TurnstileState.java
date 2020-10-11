package dk.kaloyan.fsm;

public interface TurnstileState {
    void start(TurnstileFSM turnstileFSM);
    void guess(TurnstileFSM turnstileFSM);
    void back(TurnstileFSM turnstileFSM);
}

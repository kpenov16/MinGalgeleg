package dk.kaloyan.fsm;

public enum OneGuessTurnstileState implements TurnstileState {
    INIT {
        @Override
        public void start(TurnstileFSM fsm) { //event
            fsm.setState(PLAYING);            //new state
            fsm.showCurrentGame();            //action
        }

        @Override
        public void guess(TurnstileFSM fsm) {
            //not possible
        }

        @Override
        public void back(TurnstileFSM fsm) {
            //exit app
        }
    },

    PLAYING {

    },

    WIN {

    },

    LOSE {

    }
}

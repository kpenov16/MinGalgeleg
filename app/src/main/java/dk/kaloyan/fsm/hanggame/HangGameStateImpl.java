package dk.kaloyan.fsm.hanggame;

import dk.kaloyan.fsm.hanggame.HangGameFSM;
import dk.kaloyan.fsm.hanggame.HangGameState;

public enum HangGameStateImpl implements HangGameState {
    INIT {
        @Override
        public void start(HangGameFSM fsm) { //event
            fsm.setState(PLAYING);            //new state
            fsm.showCurrentGame();            //action
        }

        @Override
        public void guess(HangGameFSM fsm) {
            //not possible
        }

        @Override
        public void back(HangGameFSM fsm) {
            //exit app
        }
    },

    PLAYING {
        @Override
        public void start(HangGameFSM fsm) {
            //already started
        }

        @Override
        public void guess(HangGameFSM fsm) {

        }

        @Override
        public void back(HangGameFSM hangGameFSM) {

        }
    },

    WIN {
        @Override
        public void start(HangGameFSM hangGameFSM) {

        }

        @Override
        public void guess(HangGameFSM hangGameFSM) {

        }

        @Override
        public void back(HangGameFSM hangGameFSM) {

        }
    },

    LOSE {
        @Override
        public void start(HangGameFSM hangGameFSM) {

        }

        @Override
        public void guess(HangGameFSM hangGameFSM) {

        }

        @Override
        public void back(HangGameFSM hangGameFSM) {

        }
    }
}

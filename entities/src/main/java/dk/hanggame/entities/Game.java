package dk.hanggame.entities;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private String wordToGuess = "";
    private int wrongLettersCount = 0;
    private List<String> usedLetters = new ArrayList<>();
    private Player player = Player.Builder().build();

    public static GameBuilder Builder(){
        return new GameBuilder();
    }

    public static class GameBuilder {
        private String wordToGuess = "";
        private int wrongLettersCount = 0;
        private List<String> usedLetters = new ArrayList<>();
        private Player player = Player.Builder().build();

        private GameBuilder(){}

        public GameBuilder withWordToGuess(String wordToGuess) {
            this.wordToGuess = wordToGuess;
            return this;
        }
        public GameBuilder withWrongLettersCount(int wrongLettersCount) {
            this.wrongLettersCount = wrongLettersCount;
            return this;
        }
        public GameBuilder withUsedLetters(List<String> usedLetters) {
            this.usedLetters = usedLetters;
            return this;
        }
        public GameBuilder withPlayer(Player player) {
            this.player = player;
            return this;
        }

        public Game build(){
            Game game = new Game();
            game.setWordToGuess(this.wordToGuess);
            game.setWrongLettersCount(this.wrongLettersCount);
            game.setUsedLetters(this.usedLetters);
            game.setPlayer(this.player);
            return game;
        }
    }

    private Game(){}

    public String getWordToGuess() {
        return wordToGuess;
    }

    public void setWordToGuess(String wordToGuess) {
        this.wordToGuess = wordToGuess;
    }

    public int getWrongLettersCount() {
        return wrongLettersCount;
    }

    public void setWrongLettersCount(int wrongLettersCount) {
        this.wrongLettersCount = wrongLettersCount;
    }

    public List<String> getUsedLetters() {
        return usedLetters;
    }

    public void setUsedLetters(List<String> usedLetters) {
        this.usedLetters = usedLetters;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

package dk.kaloyan.entities;



public class Player {
    private String nickname;
    private int wins = 0;
    private int loses = 0;

    private Player(){}

    public static PlayerBuilder Builder(){
        return new PlayerBuilder();
    }

    public static class PlayerBuilder {
        private String nickname = "";
        private int wins = 0;
        private int loses = 0;

        private PlayerBuilder(){}

        public PlayerBuilder withNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public PlayerBuilder withWins(int wins){
            this.wins = wins;
            return this;
        }

        public PlayerBuilder withLoses(int loses){
            this.loses = loses;
            return this;
        }

        public Player build(){
            Player player = new Player();
            player.setNickname(nickname);
            return player;
        }
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public int getWins() {
        return wins;
    }
    public void setWins(int wins) {
        this.wins = wins;
    }
    public int getLoses() {
        return loses;
    }
    public void setLoses(int loses) {
        this.loses = loses;
    }
    public String toString(){
        return String.format("Player: %s, wins: %d, loses: %d", nickname, wins, loses);
    }
}

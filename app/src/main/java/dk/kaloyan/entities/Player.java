package dk.kaloyan.entities;

public class Player {
    private String nickname;
    private Player(){}

    public static PlayerBuilder Builder(){
        return new PlayerBuilder();
    }

    public static class PlayerBuilder {
        private String nickname = "";

        private PlayerBuilder(){}

        public PlayerBuilder withNickname(String nickname) {
            this.nickname = nickname;
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
}

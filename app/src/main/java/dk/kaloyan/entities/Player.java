package dk.kaloyan.entities;

import android.os.Build;

public class Player {
    public static class Builder{
        private String nickname = "";

        public Builder withNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Player build(){
            Player player = new Player();
            player.setNickname(nickname);
            return player;
        }
    }

    private String nickname;
    private Player(){}
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

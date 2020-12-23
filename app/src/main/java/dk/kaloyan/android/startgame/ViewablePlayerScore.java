package dk.kaloyan.android.startgame;

public class ViewablePlayerScore {
    public String viewablePlayerName;
    public String wins;
    public String looses;
    public String nickname;

    public ViewablePlayerScore(String nickname, String viewablePlayerName, String wins, String losses) {
        this.nickname = nickname;
        this.viewablePlayerName = viewablePlayerName;
        this.wins = wins;
        this.looses = losses;
    }
}

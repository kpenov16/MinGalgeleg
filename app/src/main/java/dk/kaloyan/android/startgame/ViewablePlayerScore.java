package dk.kaloyan.android.startgame;

public class ViewablePlayerScore {
    public String playerName;
    public String wins;
    public String looses;

    public ViewablePlayerScore(String name, String wins, String losses) {
        this.playerName = name;
        this.wins = wins;
        this.looses = losses;
    }
}

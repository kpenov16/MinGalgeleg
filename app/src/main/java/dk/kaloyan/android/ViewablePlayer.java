package dk.kaloyan.android;

import android.os.Parcel;
import android.os.Parcelable;

public class ViewablePlayer implements Parcelable, Comparable<ViewablePlayer> {

        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
            public ViewablePlayer createFromParcel(Parcel in) {
                return new ViewablePlayer(in);
            }

            public ViewablePlayer[] newArray(int size) {
                return new ViewablePlayer[size];
            }
        };
    public static final String VIEWABLE_PLAYER = "dk.kaloyan.mingalgeleg.VIEWABLE_PLAYER";


    private String nickname;
        private int wins = 0;
        private int loses = 0;

    public ViewablePlayer(){}

    public ViewablePlayer(String nickname, int wins, int loses){
        setNickname(nickname);
        setWins(wins);
        setLoses(loses);
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

    public ViewablePlayer(Parcel in) {
        setNickname(in.readString());
        setWins(in.readInt());
        setLoses(in.readInt());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(getNickname());
        parcel.writeInt(getWins());
        parcel.writeInt(getLoses());
    }

    @Override
    public String toString() {
        return "ViewablePlayer{" +
                "nickname='" + nickname + '\'' +
                ", wins='" + wins + '\'' +
                ", loses='" + loses + '\'' +
                '}';
    }

    @Override
    public int compareTo(ViewablePlayer otherPlayer) {
        if(Integer.compare(this.wins, otherPlayer.getWins()) != 0)
            return Integer.compare(this.wins, otherPlayer.getWins());
        else
            return Integer.compare(this.loses, otherPlayer.getLoses())*(-1);
    }
}

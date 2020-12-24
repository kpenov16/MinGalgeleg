package dk.kaloyan.android;

import android.os.Parcel;
import android.os.Parcelable;

public class ViewableGameDetails implements Parcelable{
    public static final String GAME_DETAILS = "dk.kaloyan.android.GameDetails.GAME_DETAILS";

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ViewableGameDetails createFromParcel(Parcel in) {
            return new ViewableGameDetails(in);
        }
        public ViewableGameDetails[] newArray(int size) {
            return new ViewableGameDetails[size];
        }
    };

    private String nickname;
    private Boolean isWinning;

    public ViewableGameDetails(){}

    public ViewableGameDetails(String nickname, Boolean isWinning){
        setNickname(nickname);
        setWinning(isWinning);
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public Boolean getWinning() {
        return isWinning;
    }
    public void setWinning(Boolean winning) {
        isWinning = winning;
    }

    public ViewableGameDetails(Parcel in) {
        setNickname(in.readString());
        setWinning(in.readInt()==1?true:false);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(getNickname());
        parcel.writeInt(getWinning()?1:0);
    }


}
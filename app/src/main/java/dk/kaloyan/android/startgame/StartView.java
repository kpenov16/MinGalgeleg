package dk.kaloyan.android.startgame;

import java.util.Collection;
import java.util.List;

public interface StartView {
    void showChooseWordSource(StartViewModel startViewModel);

    List<String> getCategories();
}

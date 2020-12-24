package dk.kaloyan.gateways;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import dk.hanggame.entities.Game;
import dk.hanggame.usecases.playgame.GameGateway;
import dk.kaloyan.utils.JsonWorker;

public class GameGatewayImpl implements GameGateway{
    public interface SharedPreferencesSource{
        public SharedPreferences getSharedPreferences(final String KEY);
    }
    private static final String PREF_KAY_PLAYER = "PREF_KEY_%s";
    private SharedPreferencesSource sharedPreferencesSource;
    public GameGatewayImpl(SharedPreferencesSource sharedPreferencesSource){
        this.sharedPreferencesSource = sharedPreferencesSource;
    }

    @Override
    public void save(Game game) {
        final String KEY_PLAYER = String.format(PREF_KAY_PLAYER, game.getPlayer().getNickname());
        SharedPreferences sharedPreferences = sharedPreferencesSource.getSharedPreferences(KEY_PLAYER);
        //
        Set<String> set = new LinkedHashSet<String>(sharedPreferences.getStringSet(KEY_PLAYER, new LinkedHashSet<>()));

        List<Game> list = new JsonWorker<Game>().toList(set, Game.class);
        list.add(game);
        //

        Set<String> jsonPlayers = new JsonWorker<Game>().toStringSet(new ArrayList<>(list));


        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        editor.putStringSet(KEY_PLAYER, new LinkedHashSet<>(new ArrayList<String>(jsonPlayers)));
        editor.commit();
    }
}

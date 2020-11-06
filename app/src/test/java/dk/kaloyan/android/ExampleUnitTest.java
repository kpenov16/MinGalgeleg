package dk.kaloyan.android;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void nothing() {
        String word = "bananas";
        List<String> guessed = new ArrayList<>();
        guessed.add("a");
        guessed.add("b");
        String visibleWord = "";

        visibleWord = makeVisible(word, guessed, "*");

        assertEquals("ba*a*a*", visibleWord);
    }

    private String makeVisible(String word, List<String> guessed, String replaceWith) {
        Map<String,String> map = guessed.stream().distinct().collect(Collectors.toMap(s->s, s->s));
        String visibleWord = word.chars()
                .map(c -> map.get(Character.toString((char)c)) != null ? c : '*')
                .collect(StringBuilder::new, (sb,c) -> sb.append((char)c), StringBuilder::append).toString();
        return visibleWord;
    }
}
package dk.kaloyan.gateways;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GuessWordGatewayTest {

    @Test
    public void givenRequestNumberOfWords_returnNumberOfWords(){
        GuessWordGateway gateway = new GuessWordGateway();
        int numberOfWords = 5;
        //assertEquals("numberOfWords", gateway.getRandomWordsAsStr(numberOfWords));
    }

}

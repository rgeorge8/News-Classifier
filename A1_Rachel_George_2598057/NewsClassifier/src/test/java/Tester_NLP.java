import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import uob.oop.NLP;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Tester_NLP {

    @Test
    @Order(1)
    void textCleaning(){
        String strTest = "!\"$%&^%H).,e+ll~'/o/Wor.l,d!";
        assertEquals("helloworld", NLP.textCleaning(strTest));
    }

    @Test
    @Order(2)
    void textLemmatization(){
        String strTest = "bananas";
        assertEquals("banana", NLP.textLemmatization(strTest));
    }

    @Test
    @Order(3)
    void removeStopWords(){
        String strTest = "and the banana and the is in green";
        String[] stopWords = {"and","the","is","in","a"};
        assertEquals("banana green",NLP.removeStopWords(strTest,stopWords));
        //String[] stopWords = {"is"};
        //assertEquals("banana green",NLP.removeStopWords(strTest,stopWords));
    }

}

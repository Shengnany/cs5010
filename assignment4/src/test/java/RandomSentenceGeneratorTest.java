import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class RandomSentenceGeneratorTest {

    @Test
    void main() throws InvalidArgumentException {
        RandomSentenceGenerator r = new RandomSentenceGenerator();
        r.main(new String[]{"src/main/resources"});
        assertThrows(InvalidArgumentException.class,()->r.main(null));

    }


}
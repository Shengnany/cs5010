import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class RandomSentenceGeneratorTest {

    @Test
    void main() throws InvalidArgumentException, IOException, org.json.simple.parser.ParseException {
        RandomSentenceGenerator r = new RandomSentenceGenerator();
        r.main(new String[]{"src/main/resources"});
    }
}
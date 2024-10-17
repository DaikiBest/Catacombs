package model.persistence;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Test;
import java.io.IOException;

import persistence.JsonWriter;

public class testJsonWritter {
    
    @Test
    void testWriterWrongFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/wrong.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterInitialGameState() {
        try {
            writer.open();
            writer.write();
        } catch (IOException e) {
            // TODO: handle exception
        }
    }
}

package persistence;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes the game's state to a JSON file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: creates the writer that will write the game's state into a json file
    public JsonWriter(String destination) {
        //
    }

    // MODIFIES: this
    // EFFECTS: opens the write. Will throw an exception if the file to be writen can't be opened.
    public void open() throws FileNotFoundException {
        //writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: close the writer
    public void close() {
        //stub
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        //stub
    }

}

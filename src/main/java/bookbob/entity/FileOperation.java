package bookbob.entity;

import java.io.IOException;

//@@author coraleaf0602
public interface FileOperation {
    void initFile(String filePath);
    void autosave(String filePath) throws IOException;
    void retrieveData(String filePath);
}

package rcp.taskholder.services;

import java.util.List;

import rcp.taskholder.model.Person;
import rcp.taskholder.util.JsonFileWriter;


public class FileService {
    
    /**
     * Saves list of <code>TableData</code> to the default file
     * @param data - list of data for saving
     */
    public static void saveDataToFile(List<Person> data){
        JsonFileWriter.writeToJsonFile(data);
    }
    
    /**
     * Saves list of <code>TableData</code> to the selected file
     * @param data - list of data for saving
     * @param filePath - path to the file for saving 
     */
    public static void saveDataToFile(List<Person> data, String filePath) {
        JsonFileWriter.writeToJsonFile(data, filePath);
    }

}

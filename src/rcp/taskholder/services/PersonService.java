package rcp.taskholder.services;

import java.util.List;

import rcp.taskholder.model.Person;
import rcp.taskholder.repository.ArrayDataProvider;

public class PersonService {
    
    /**
     * Instance of <code>ArrayDataProvider</code> - provides the list of <code>Person</code>s
     */
    private ArrayDataProvider provider;
    
    /**
     * Creates the instance of this class. Instantiates <code>ArrayDataProvider</code> 
     */
    public PersonService() {
        provider = ArrayDataProvider.getInstance();
    }
    
    /**
     * Get the list of data from <code>DataProvider</code>
     * 
     * @return list of <code>Person</code>s
     */
    public List<Person> getData() {
        return provider.getData();
    }

    /**
     * Get the row from the list by the index
     * 
     * @param index - index of the row
     * @return instance of <code>Person</code>
     */
    public Person getRow(int index) {
        return provider.getRow(index);
    }

    /**
     * Deletes row from the list by index using <code>DataProvider</code>
     * 
     * @param index - index of the row
     * @return <code>true</code> if deleting is successful or <code>false</code> if not
     */
    public boolean deleteRow(int index) {
        return provider.deleteRow(index);
    }
    
    /**
     * Deletes the current person from the list
     * 
     * @param person
     * @return <code>true</code> if deletion success and <code>false</code> if not
     */
    public boolean deleteRow(Person person) {
    	return provider.deleteRow(person);
    }

    /**
     * Adds the row (instance of <code>Person</code>) to the list using <code>DataProvider</code>
     * 
     * @param rowData - instance of <code>Person</code>
     * @return <code>true</code> if data was added successfully or <code>false</code> if not
     */
    public boolean addRow(Person rowData) {
        return provider.addRow(rowData);
    }

    /**
     * Adds the row (instance of <code>Person</code>) to the list using <code>DataProvider</code>
     * 
     * @param name - person's name
     * @param group - its group
     * @param isDone - check if he done the task
     * @return <code>true</code> if data was added successfully or <code>false</code> if not
     */
    public boolean addRow(String name, String group, boolean isDone) {
        return provider.addRow(name, group, isDone);
    }

    /**
     * Updates <code>Person</code>'s data in the list by index using <code>DataProvider</code>
     * 
     * @param index - index of the row
     * @param rowData - updated row (instance of <code>Person</code>)
     * @return <code>true</code> if data was updated successfully or <code>false</code> if not
     */
    public boolean updateRow(int index, Person rowData) {
        return provider.update(index, rowData);
    }
    
    /**
     * Sets the data from the file If the path wrong or such file doesn't exit table will be fullfilled with default
     * data
     * 
     * @param filePath - full path to the file where table data stored
     */
    public void setDataFromFile(String filePath) {
        provider.setData(filePath);
    }
    
    public void setRow(int index, Person person) {
    	provider.setRow(index, person);
    }

}

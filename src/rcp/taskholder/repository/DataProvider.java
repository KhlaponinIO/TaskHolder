package rcp.taskholder.repository;

import java.util.List;

import rcp.taskholder.model.Person;

/**
 * This is the interface for storing the row data about the <code>Person</code>
 * 
 * @author Igor Khlaponin
 *
 */
public interface DataProvider {

    /**
     * Get the list of all data stored in the table
     * 
     * @return list of <code>Person</code>
     */
    public List<Person> getData();

    /**
     * Get the row with current index
     * 
     * @param index of the row
     * @return data stored in the current row (instance of <code>Person</code>)
     */
    public Person getRow(int index);

    /**
     * Deletes the row from the list by current index
     * 
     * @param index of the row
     * @return <code>true</code> if deletion success and <code>false</code> if not
     */
    public boolean deleteRow(int index);
    
    /**
     * Deletes the current person from the list
     * 
     * @param person
     * @return <code>true</code> if deletion success and <code>false</code> if not
     */
    public boolean deleteRow(Person person);

    /**
     * Add the row of data to the list
     * 
     * @param rowData - instance of <code>Person</code>
     * @return <code>true</code> if the data was successfully added and <code>false</code> if not
     */
    public boolean addRow(Person rowData);

    /**
     * Creates the instance of <code>Person</code> and adds it to the list
     * 
     * @param name - name of the person
     * @param group - its group
     * @param isDone - check if he done his task
     * @return <code>true</code> if the data was successfully added and <code>false</code> if not
     */
    public boolean addRow(String name, String group, boolean isDone);

    /**
     * Updates row of data in the list by the index
     * 
     * @param index - index of row for updating
     * @param rowData - instance of <code>Person</code>
     * @return
     */
    public boolean update(int index, Person rowData);
    
    public boolean setRow(int index, Person rowData);
}

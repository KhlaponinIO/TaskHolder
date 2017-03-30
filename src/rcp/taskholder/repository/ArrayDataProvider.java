package rcp.taskholder.repository;

import java.util.ArrayList;
import java.util.List;

import rcp.taskholder.model.Person;
import rcp.taskholder.util.JsonFileWriter;

public class ArrayDataProvider implements DataProvider {

	/**
	 * Data storage. Contains the instances of <code>Person</code>s
	 */
	private List<Person> data;

	private static class SingletonHolder {
		private final static ArrayDataProvider INSTANCE = new ArrayDataProvider();
	}

	/**
	 * Returns the instance of this class
	 * 
	 * @return instance of <code>ArrayDataProvider</code>
	 */
	public static ArrayDataProvider getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private ArrayDataProvider() {
		data = JsonFileWriter.getDataFromJsonFile();
		if (data == null) {
			data = new ArrayList<>();
			initData();
		}
	}

	private void initData() {
		data.add(new Person("Darth Vader", "1", false));
		data.add(new Person("Yoda", "2", true));
		data.add(new Person("Luk Skywalker", "1", true));
	}

	/**
	 * Sets the data from the file If the path wrong or such file doesn't exit
	 * table will be fullfilled with default data
	 * 
	 * @param fileName
	 *            - full path to the file where table data stored
	 */
	public void setData(String fileName) {
		if (JsonFileWriter.getDataFromJsonFile(fileName) != null) {
			data = JsonFileWriter.getDataFromJsonFile(fileName);
		}
	}

	@Override
	public List<Person> getData() {
		return data;
	}

	@Override
	public Person getRow(int index) {
		if (index < 0 || index > data.size()) {
			return null;
		} else {
			return data.get(index);
		}
	}

	@Override
	public boolean deleteRow(int index) {
		if (index < 0 || index > data.size()) {
			return false;
		} else {
			data.remove(index);
			return true;
		}
	}

	@Override
	public boolean deleteRow(Person person) {
		if (person != null) {
			return data.remove(person);
		} else {
			return false;
		}
	}

	@Override
	public boolean addRow(Person rowData) {
		return data.add(rowData);
	}

	@Override
	public boolean addRow(String name, String group, boolean isDone) {
		return this.addRow(new Person(name, group, isDone));
	}

	@Override
	public boolean update(int index, Person rowData) {
		if (index < 0 || index > data.size()) {
			return false;
		} else {
			data.set(index, rowData);
			return true;
		}
	}

	@Override
	public boolean setRow(int index, Person rowData) {
		if (index < 0 || index > data.size()) {
			return false;
		} else {
			data.add(index, rowData);
			return true;
		}
	}

}

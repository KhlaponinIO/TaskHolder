package rcp.taskholder.repository;

import java.util.ArrayList;
import java.util.List;

import rcp.taskholder.model.Group;
import rcp.taskholder.model.Person;
import rcp.taskholder.services.PersonService;

public class GroupDataProvider {

	private List<Group> groupsData;
	private PersonService personService = new PersonService();

	private static class SingletonHolder {
		private final static GroupDataProvider INSTANCE = new GroupDataProvider();
	}

	public static GroupDataProvider getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private GroupDataProvider() {
		groupsData = new ArrayList<>();
		fillGroupsData();
	}
	
	private void fillGroupsData() {
		List<Person> persons = personService.getData();
		
		for (Person person : persons) {
			String groupNumber = person.getGroup();
			Group currentGroup = new Group(Integer.parseInt(groupNumber));
			if (groupsData.size() == 0 || !groupsData.contains(currentGroup)) {
				groupsData.add(currentGroup);
			}
			if (groupsData.contains(currentGroup)) {
				groupsData.get(groupsData.indexOf(currentGroup)).setStudent(person);
			}
		}
	}
	
	public void update() {
		groupsData.clear();
		fillGroupsData();
	}
	
	
	
	public List<Group> getGroupsData() {
		return groupsData;
	}

	public void setGroupsData(List<Group> groupsData) {
		this.groupsData = groupsData;
		update();
	}

}

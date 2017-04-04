package rcp.taskholder.repository;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;

import rcp.taskholder.model.Group;
import rcp.taskholder.model.Person;
import rcp.taskholder.services.PersonService;
import rcp.taskholder.util.ApplicationScope;

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
				groupsData.get(groupsData.lastIndexOf(currentGroup)).setStudent(person);
			}
		}
	}

	public void update() {
		groupsData.clear();
		fillGroupsData();
		TreeViewer treeViewer = ((TreeViewer) ApplicationScope.getInstance().getElement("treeViewer"));
		if (treeViewer != null) {
			treeViewer.refresh();
			treeViewer.expandAll();
		}
	}

	public List<Group> getGroupsData() {
		return groupsData;
	}

	public void setGroupsData(List<Group> groupsData) {
		this.groupsData = groupsData;
		update();
	}

}

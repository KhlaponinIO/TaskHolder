package rcp.taskholder.model;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;

import rcp.taskholder.repository.GroupDataProvider;

/**
 * Instance of <code>ITreeContentProvider</code> for tree content
 * 
 * @author Ihor Khlaponin
 */
public class TreeContentProvider implements ITreeContentProvider {

    ArrayList<Group> groups = (ArrayList<Group>) GroupDataProvider.getInstance().getGroupsData();

    @Override
    @SuppressWarnings("unchecked")
    public Object[] getElements(Object inputElement) {
        return ((ArrayList<Person>) inputElement).toArray();
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof Group) {
            return ((Group) parentElement).getStudents().toArray();
        }
        return null;
    }

    @Override
    public Object getParent(Object element) {
        if (element instanceof Group) {
            return null;
        }
        if (element instanceof Person) {
            int groupNum = Integer.parseInt(((Person) element).getGroup());
            int index = groups.indexOf(new Group(groupNum));
            if (index > 0) {
                return groups.get(index);
            }
        }
        return null;
    }

    @Override
    public boolean hasChildren(Object element) {
        return element instanceof Group;
    }

}

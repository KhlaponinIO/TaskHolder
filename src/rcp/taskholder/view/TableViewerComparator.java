package rcp.taskholder.view;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import rcp.taskholder.model.Person;

public class TableViewerComparator extends ViewerComparator {

    private int propertyIndex;
    private static final int DESCENDING = 1;
    private int direction = DESCENDING;

    public TableViewerComparator() {
        this.propertyIndex = 0;
        direction = DESCENDING;
    }

    public int getDirection() {
        return direction == 1 ? SWT.DOWN : SWT.UP;
    }

    public void setColumn(int column) {
        if (column == this.propertyIndex) {
            // Same column as last sort; toggle the direction
            direction = 1 - direction;
        } else {
            // New column; do an ascending sort
            this.propertyIndex = column;
            direction = DESCENDING;
        }
    }

    @Override
    public int compare(Viewer viewer, Object e1, Object e2) {
        Person p1 = (Person) e1;
        Person p2 = (Person) e2;
        int rc = 0;
        switch (propertyIndex) {
        case 0:
            rc = p1.getName().compareTo(p2.getName());
            break;
        case 1:
            rc = p1.getGroup().compareTo(p2.getGroup());
            break;
        default:
            rc = 0;
        }
        // If descending order, flip the direction
        if (direction == DESCENDING) {
            rc = -rc;
        }
        return rc;
    }

}

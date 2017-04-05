package rcp.taskholder.services;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.TreeItem;

import rcp.taskholder.model.Person;
import rcp.taskholder.repository.GroupDataProvider;
import rcp.taskholder.util.ApplicationScope;

public class ViewPartsService {

    private ApplicationScope scope;
    private PersonService service;

    private TreeViewer treeViewer;
    private TableViewer tableViewer;

    public ViewPartsService() {
        scope = ApplicationScope.getInstance();
        service = new PersonService();
        treeViewer = (TreeViewer) scope.getElement("treeViewer");
        tableViewer = (TableViewer) scope.getElement("tableViewer");
    }

    public int getSelectionIndex() {
        int index = -1;

        if (tableViewer != null && !tableViewer.getTable().isDisposed()) {
            index = tableViewer.getTable().getSelectionIndex();
        } else if (treeViewer != null && !treeViewer.getTree().isDisposed()) {
            TreeItem[] treeItem = treeViewer.getTree().getSelection();
            if (treeItem.length > 0) {
                Object element = treeItem[0].getData();
                if (element == null) {
                    return -1;
                } else if (element instanceof Person) {
                    return service.getData().indexOf(element);
                }
            }
        }

        return index;
    }

    public void refresh() {
        if (tableViewer != null) {
            tableViewer.refresh();
        }
        if (treeViewer != null) {
            GroupDataProvider.getInstance().update();
        }
    }
}

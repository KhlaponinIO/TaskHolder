package rcp.taskholder.services;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;

import rcp.taskholder.model.Person;
import rcp.taskholder.repository.GroupDataProvider;
import rcp.taskholder.util.ApplicationContextUtil;

/**
 * Contains the methods for interaction with table and tree view parts
 * 
 * @author Ihor Khlaponin
 */
public class ViewPartsService {

    private PersonService service;

    private TreeViewer treeViewer;
    private TableViewer tableViewer;

    public ViewPartsService() {
        service = ApplicationContextUtil.getFromContext(PersonService.class);
    }

    /**
     * Returns the index of selected item.
     * If <code>TablePart</code> is active it returns selection index of the <code>TableViewer</code>.
     * If not then the <code>TreePart</code> must be active, so it calculates the index by selected item
     * 
     * @return the index of selected item or <code>-1</code> if selection is wrong
     */
    public int getSelectionIndex() {
        int index = -1;
        treeViewer = (TreeViewer) ApplicationContextUtil.getFromContext("treeViewer");
        tableViewer = (TableViewer) ApplicationContextUtil.getFromContext("tableViewer");

        if (tableViewer != null && !tableViewer.getTable().isDisposed()) {
            TableItem[] tableItem = tableViewer.getTable().getSelection();
            if (tableItem.length > 0) {
                Object element = tableItem[0].getData();
                if (element == null) {
                    return -1;
                } else if (element instanceof Person) {
                    index = service.getData().indexOf(element);
                }
            }
            
        } else if (treeViewer != null && !treeViewer.getTree().isDisposed()) {
            TreeItem[] treeItem = treeViewer.getTree().getSelection();
            if (treeItem.length > 0) {
                Object element = treeItem[0].getData();
                if (element == null) {
                    return -1;
                } else if (element instanceof Person) {
                    index = service.getData().indexOf(element);
                }
            }
        }

        return index;
    }

    /**
     * Refresh the <code>TableViewer</code> or <code>TreeViewer</code> depends on which one is active  
     */
    public void refresh() {
        treeViewer = (TreeViewer) ApplicationContextUtil.getFromContext("treeViewer");
        tableViewer = (TableViewer) ApplicationContextUtil.getFromContext("tableViewer");
        
        if (tableViewer != null) {
            tableViewer.refresh();
        }
        if (treeViewer != null) {
            GroupDataProvider.getInstance().update();
        }
    }
}

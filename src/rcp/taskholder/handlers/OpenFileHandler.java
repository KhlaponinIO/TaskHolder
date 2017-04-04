package rcp.taskholder.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import rcp.taskholder.repository.GroupDataProvider;
import rcp.taskholder.services.PersonService;
import rcp.taskholder.util.ApplicationScope;

public class OpenFileHandler extends AbstractHandler {
    
    private PersonService service;
    private ApplicationScope scope;
    TableViewer tableViewer;
    
    {
        service = new PersonService();
        scope = ApplicationScope.getInstance();
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        
        FileDialog fileDialog = new FileDialog(Display.getCurrent().getShells()[0], SWT.OPEN);
        fileDialog.setText("Open");
        fileDialog.setFilterPath("D:/");
        String[] filterExtensions = { "*.*", ".json", ".xml" };
        fileDialog.setFilterExtensions(filterExtensions);

        String selected = fileDialog.open();
        
        service.setDataFromFile(selected);
        tableViewer = (TableViewer) scope.getElement("tableViewer");
        
        refresh();
        
        return null;
    }
    
    private void refresh() {
        TreeViewer treeViewer = ((TreeViewer) scope.getElement("treeViewer"));
        
        if (tableViewer != null) {
        	tableViewer.setInput(service.getData());
        	tableViewer.refresh();
        }
        if (treeViewer != null) {
        	GroupDataProvider.getInstance().update();
        	treeViewer.refresh();
        	treeViewer.expandAll();
        }
	}

}

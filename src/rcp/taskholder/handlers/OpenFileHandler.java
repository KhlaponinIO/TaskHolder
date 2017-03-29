package rcp.taskholder.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import rcp.taskholder.services.PersonService;
import rcp.taskholder.util.ApplicationScope;

public class OpenFileHandler extends AbstractHandler {
    
    private PersonService service;
    private ApplicationScope scope;
    
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
        TableViewer tableViewer = (TableViewer) scope.getElement("tableViewer");
        tableViewer.setInput(service.getData());
        tableViewer.refresh();
        
        return null;
    }

}

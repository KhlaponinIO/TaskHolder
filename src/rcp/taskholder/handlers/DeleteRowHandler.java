package rcp.taskholder.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.TableViewer;

import rcp.taskholder.services.PersonService;
import rcp.taskholder.util.ApplicationScope;

public class DeleteRowHandler extends AbstractHandler {
    
    private PersonService service;
    private ApplicationScope scope;
    
    {
        service = new PersonService();
        scope = ApplicationScope.getInstance();
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        int index = ((TableViewer) scope.getElement("tableViewer")).getTable().getSelectionIndex();
        service.deleteRow(index);
        ((TableViewer) scope.getElement("tableViewer")).refresh();
        return null;
    }


}

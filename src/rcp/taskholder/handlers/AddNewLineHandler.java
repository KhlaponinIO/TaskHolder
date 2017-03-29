package rcp.taskholder.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.TableViewer;

import rcp.taskholder.model.Person;
import rcp.taskholder.services.PersonService;
import rcp.taskholder.util.ApplicationScope;

public class AddNewLineHandler extends AbstractHandler {
    
    private PersonService service;
    private ApplicationScope scope;
    
    {
        service = new PersonService();
        scope = ApplicationScope.getInstance();
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        service.addRow(new Person());
        ((TableViewer) scope.getElement("tableViewer")).refresh();
        return null;
    }

}

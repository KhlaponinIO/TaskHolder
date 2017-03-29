package rcp.taskholder.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import rcp.taskholder.model.Person;
import rcp.taskholder.services.PersonService;
import rcp.taskholder.util.ApplicationScope;

public class SaveRowHandler extends AbstractHandler {
    
    private PersonService service;
    private ApplicationScope scope;
    
    {
        service = new PersonService();
        scope = ApplicationScope.getInstance();
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        Text nameTextField = (Text) scope.getElement("nameTextField");
        Text groupTextField = (Text) scope.getElement("groupTextField");
        Button checkTaskButton = (Button) scope.getElement("checkTaskButton");
        
        int index = ((TableViewer) scope.getElement("tableViewer")).getTable().getSelectionIndex();
        service.updateRow(index, new Person(nameTextField.getText(), 
                groupTextField.getText(), checkTaskButton.getSelection()));
        
        ((TableViewer) scope.getElement("tableViewer")).refresh();
        return null;
    }

}

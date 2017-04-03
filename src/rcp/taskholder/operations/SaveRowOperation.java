package rcp.taskholder.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import rcp.taskholder.model.Person;
import rcp.taskholder.repository.GroupDataProvider;
import rcp.taskholder.services.PersonService;
import rcp.taskholder.util.ApplicationScope;

public class SaveRowOperation extends AbstractOperation {
	
    private PersonService service;
    private ApplicationScope scope;
    
    private Person storagePerson;
    private Person updatedPerson;
    private int storageIndex = -1;
    
    {
        service = new PersonService();
        scope = ApplicationScope.getInstance();
    }

	public SaveRowOperation(String login) {
		super(login);
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

		Text nameTextField = (Text) scope.getElement("nameTextField");
        Text groupTextField = (Text) scope.getElement("groupTextField");
        Button checkTaskButton = (Button) scope.getElement("checkTaskButton");
        
        int index = ((TableViewer) scope.getElement("tableViewer")).getTable().getSelectionIndex();
        
        storageIndex = index;
        storagePerson = service.getRow(index);
        
        updatedPerson = new Person(nameTextField.getText(), 
                groupTextField.getText(), checkTaskButton.getSelection());
        service.updateRow(index, updatedPerson);
        
        refresh();
        
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (storageIndex < 0) {
			return Status.CANCEL_STATUS;
		}
		service.updateRow(storageIndex, updatedPerson);
		refresh();
		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (storageIndex < 0) {
			return Status.CANCEL_STATUS;
		}
		service.updateRow(storageIndex, storagePerson);
		refresh();
		return Status.OK_STATUS;
	}
	
	private void refresh() {
		TableViewer tableViewer = ((TableViewer) scope.getElement("tableViewer"));
        TreeViewer treeViewer = ((TreeViewer) scope.getElement("treeViewer"));
        
        if (tableViewer != null) {
        	tableViewer.refresh();
        }
        if (treeViewer != null) {
        	GroupDataProvider.getInstance().update();
        	treeViewer.refresh();
        }
	}
	
}

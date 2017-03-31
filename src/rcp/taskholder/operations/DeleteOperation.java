package rcp.taskholder.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.TableViewer;

import rcp.taskholder.model.Person;
import rcp.taskholder.services.PersonService;
import rcp.taskholder.util.ApplicationScope;

public class DeleteOperation extends AbstractOperation {
	
	private PersonService service;
	private ApplicationScope scope;
	
	private Person storagePerson;
	private int storageIndex = -1;

	{
		service = new PersonService();
		scope = ApplicationScope.getInstance();
	}
	
	public DeleteOperation(String login) {
		super(login);
	}
	
	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		int index = ((TableViewer) scope.getElement("tableViewer")).getTable().getSelectionIndex();
		if (index < 0) {
			return Status.CANCEL_STATUS;
		}
		storagePerson = new Person(service.getRow(index));
		storageIndex = index;
		service.deleteRow(index);
		((TableViewer) scope.getElement("tableViewer")).refresh();
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		service.deleteRow(storagePerson);
		((TableViewer) scope.getElement("tableViewer")).refresh();
		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (storageIndex < 0) {
			return Status.CANCEL_STATUS;
		}
		service.setRow(storageIndex, storagePerson);
		((TableViewer) scope.getElement("tableViewer")).refresh();
		return Status.OK_STATUS;
	}

}
package rcp.taskholder.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;

import rcp.taskholder.model.Person;
import rcp.taskholder.repository.GroupDataProvider;
import rcp.taskholder.services.PersonService;
import rcp.taskholder.util.ApplicationScope;

public class AddNewLineOperation extends AbstractOperation {
	
	private PersonService service;
	private ApplicationScope scope;
	
	private Person storagePerson;

	{
		service = new PersonService();
		scope = ApplicationScope.getInstance();
	}
	
	public AddNewLineOperation(String login) {
		super(login);
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		storagePerson = new Person();
		service.addRow(storagePerson);
		refresh();
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		execute(monitor, info);
		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		service.deleteRow(storagePerson);
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

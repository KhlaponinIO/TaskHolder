package rcp.taskholder.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.ui.PlatformUI;

import rcp.taskholder.model.Person;
import rcp.taskholder.services.PersonService;
import rcp.taskholder.util.ApplicationScope;

public class DeleteRowHandler extends AbstractHandler {

	private PersonService service;
	private ApplicationScope scope;
	
	private Person storagePerson;
	private int storageIndex = -1;

	{
		service = new PersonService();
		scope = ApplicationScope.getInstance();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IOperationHistory history = PlatformUI.getWorkbench().getOperationSupport().getOperationHistory();
		AbstractOperation deleteOperation = new AbstractOperation("Delete Operation") {

			@Override
			public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				int index = ((TableViewer) scope.getElement("tableViewer")).getTable().getSelectionIndex();
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
				service.setRow(storageIndex, storagePerson);
				((TableViewer) scope.getElement("tableViewer")).refresh();
				return Status.OK_STATUS;
			}
		};
		
		deleteOperation.addContext(PlatformUI.getWorkbench().getOperationSupport().getUndoContext());
		history.execute(deleteOperation, null, null);

		return null;
	}

}

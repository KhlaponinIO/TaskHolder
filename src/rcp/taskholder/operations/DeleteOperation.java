package rcp.taskholder.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.TreeItem;

import rcp.taskholder.model.Person;
import rcp.taskholder.repository.GroupDataProvider;
import rcp.taskholder.services.PersonService;
import rcp.taskholder.util.ApplicationScope;

public class DeleteOperation extends AbstractOperation {

	private PersonService service;
	private ApplicationScope scope;

	private Person storagePerson;
	private int storageIndex = -1;

	TableViewer tableViewer;
	TreeViewer treeViewer;

	{
		service = new PersonService();
		scope = ApplicationScope.getInstance();

		tableViewer = ((TableViewer) scope.getElement("tableViewer"));
		treeViewer = ((TreeViewer) scope.getElement("treeViewer"));
	}

	public DeleteOperation(String login) {
		super(login);
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		int index = getSelectionIndex();
		if (index < 0) {
			return Status.CANCEL_STATUS;
		}
		storagePerson = new Person(service.getRow(index));
		storageIndex = index;
		service.deleteRow(index);
		refresh();
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (storageIndex < 0) {
			return Status.CANCEL_STATUS;
		}
		service.deleteRow(storageIndex);
		refresh();
		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (storageIndex < 0) {
			return Status.CANCEL_STATUS;
		}
		service.setRow(storageIndex, storagePerson);
		refresh();
		return Status.OK_STATUS;
	}

	private void refresh() {

		if (tableViewer != null) {
			tableViewer.refresh();
		}
		if (treeViewer != null) {
			GroupDataProvider.getInstance().update();
//			treeViewer.refresh();
		}
	}

	private int getSelectionIndex() {
		int index = -1;

		if (tableViewer != null) {
			index = tableViewer.getTable().getSelectionIndex();
		} else if (treeViewer != null) {
			TreeItem[] treeItem = treeViewer.getTree().getSelection();
			if (treeItem.length > 0) {
				Object element = treeItem[0].getData();
				if (element == null) {
					return -1;
				} else if (element instanceof Person) {
					return service.getData().indexOf((Person) element);
				}
			}
		}

		return index;
	}
}

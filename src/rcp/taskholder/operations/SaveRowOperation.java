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
import rcp.taskholder.services.PersonService;
import rcp.taskholder.services.ViewPartsService;
import rcp.taskholder.util.ApplicationScope;

public class SaveRowOperation extends AbstractOperation {

    private PersonService service;
    private ApplicationScope scope;
    private ViewPartsService viewService;

    private Person storagePerson;
    private Person updatedPerson;
    private int storageIndex = -1;

    {
        service = new PersonService();
        viewService = new ViewPartsService();
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

        TableViewer tableViewer = (TableViewer) scope.getElement("tableViewer");
        TreeViewer treeViewer = (TreeViewer) scope.getElement("treeViewer");
        if (tableViewer == null & treeViewer == null) {
            return Status.CANCEL_STATUS;
        }

        int index = viewService.getSelectionIndex();

        storageIndex = index;
        storagePerson = service.getRow(index);

        updatedPerson = new Person(nameTextField.getText(), groupTextField.getText(), checkTaskButton.getSelection());
        service.updateRow(index, updatedPerson);

        viewService.refresh();

        return Status.OK_STATUS;
    }

    @Override
    public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
        if (storageIndex < 0) {
            return Status.CANCEL_STATUS;
        }
        service.updateRow(storageIndex, updatedPerson);
        viewService.refresh();
        return Status.OK_STATUS;
    }

    @Override
    public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
        if (storageIndex < 0) {
            return Status.CANCEL_STATUS;
        }
        service.updateRow(storageIndex, storagePerson);
        viewService.refresh();
        return Status.OK_STATUS;
    }

}

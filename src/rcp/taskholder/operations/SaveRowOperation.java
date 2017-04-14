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
import rcp.taskholder.util.ApplicationContextUtil;

public class SaveRowOperation extends AbstractOperation {

    private PersonService service;
    private ViewPartsService viewService;

    private Person storagePerson;
    private Person updatedPerson;
    private int storageIndex = -1;

    {
        service = ApplicationContextUtil.getFromContext(PersonService.class);
        viewService = ApplicationContextUtil.getFromContext(ViewPartsService.class);
    }

    public SaveRowOperation() {
        super("SaveRowOperation");
    }

    @Override
    public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

        Text nameTextField = (Text) ApplicationContextUtil.getFromContext("nameTextField");
        Text groupTextField = (Text) ApplicationContextUtil.getFromContext("groupTextField");
        Button checkTaskButton = (Button) ApplicationContextUtil.getFromContext("checkTaskButton");

        TableViewer tableViewer = (TableViewer) ApplicationContextUtil.getFromContext("tableViewer");
        TreeViewer treeViewer = (TreeViewer) ApplicationContextUtil.getFromContext("treeViewer");
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

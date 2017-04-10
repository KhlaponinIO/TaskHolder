package rcp.taskholder.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import rcp.taskholder.model.Person;
import rcp.taskholder.services.PersonService;
import rcp.taskholder.services.ViewPartsService;
import rcp.taskholder.util.ApplicationContextUtil;

public class DeleteOperation extends AbstractOperation {

    private PersonService service;
    private ViewPartsService viewService;

    private Person storagePerson;
    private int storageIndex = -1;

    {
        service = ApplicationContextUtil.getFromContext(PersonService.class);
        viewService = ApplicationContextUtil.getFromContext(ViewPartsService.class);
    }

    public DeleteOperation() {
        super("DeleteOperation");
    }

    @Override
    public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
        int index = viewService.getSelectionIndex();
        if (index < 0) {
            return Status.CANCEL_STATUS;
        }
        storagePerson = new Person(service.getRow(index));
        storageIndex = index;
        service.deleteRow(index);
        viewService.refresh();
        return Status.OK_STATUS;
    }

    @Override
    public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
        if (storageIndex < 0) {
            return Status.CANCEL_STATUS;
        }
        service.deleteRow(storageIndex);
        viewService.refresh();
        return Status.OK_STATUS;
    }

    @Override
    public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
        if (storageIndex < 0) {
            return Status.CANCEL_STATUS;
        }
        service.setRow(storageIndex, storagePerson);
        viewService.refresh();
        return Status.OK_STATUS;
    }

}

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

public class AddNewLineOperation extends AbstractOperation {

    private ViewPartsService viewService;
    private PersonService service;

    private Person storagePerson;

    {
        viewService = ApplicationContextUtil.getFromContext(ViewPartsService.class);
        service = ApplicationContextUtil.getFromContext(PersonService.class);
    }

    
    public AddNewLineOperation() {
        super("AddNewLineOperation");
    }

    @Override
    public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
        storagePerson = new Person();
        service.addRow(storagePerson);
        viewService.refresh();
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
        viewService.refresh();
        return Status.OK_STATUS;
    }

}

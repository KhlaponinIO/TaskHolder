package rcp.taskholder.operations;

import static rcp.taskholder.util.ApplicationContextUtil.getFromContext;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import rcp.taskholder.model.Person;
import rcp.taskholder.services.PersonService;
import rcp.taskholder.services.ViewPartsService;

public class CancelOperation extends AbstractOperation {
    
    private PersonService service;
    private ViewPartsService viewService;

    public CancelOperation() {
        super("CancelOperation");
        service = getFromContext(PersonService.class);
        viewService = getFromContext(ViewPartsService.class);
    }

    @Override
    public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
        int storageIndex = -1;
        Person storagePerson;

        storageIndex = viewService.getSelectionIndex();
        if (storageIndex >= 0) {
            storagePerson = service.getRow(storageIndex);
            ((Text) getFromContext("nameTextField")).setText(storagePerson.getName());
            ((Text) getFromContext("groupTextField")).setText(storagePerson.getGroup());
            ((Button) getFromContext("checkTaskButton")).setSelection(storagePerson.isTaskDone());
        }
        return Status.OK_STATUS;
    }

    @Override
    public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
        // do nothing
        return Status.CANCEL_STATUS;
    }

    @Override
    public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
        // do nothing
        return Status.CANCEL_STATUS;
    }

}

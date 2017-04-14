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
import rcp.taskholder.services.ViewPartsService;

public class CancelOperation extends AbstractOperation {
    
    private ViewPartsService viewService;

    public CancelOperation() {
        super("CancelOperation");
        viewService = getFromContext(ViewPartsService.class);
    }

    @Override
    public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
        Person storagePerson;
        storagePerson = viewService.getSelectedPerson();
        
        if (storagePerson != null) {
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

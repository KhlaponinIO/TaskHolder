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
import rcp.taskholder.util.ApplicationScope;

public class PasteOperation extends AbstractOperation {

    private PersonService service;
    private ApplicationScope scope;
    private ViewPartsService viewService;

    private Person clipboardPerson;
    private int storageIndex = -1;

    {
        service = ApplicationContextUtil.getFromContext(PersonService.class);
        viewService = ApplicationContextUtil.getFromContext(ViewPartsService.class);
        scope = ApplicationScope.getInstance();
    }

    public PasteOperation() {
        super("PasteOperation");
    }

    @Override
    public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
        try {
            clipboardPerson = (Person) scope.getElement("clipboardPerson");

            if (clipboardPerson != null) {
                service.addRow(clipboardPerson);
                storageIndex = service.getData().lastIndexOf(clipboardPerson);
                viewService.refresh();
            }
        } catch (ClassCastException e) {
            System.err.println(e.getMessage());
            return Status.CANCEL_STATUS;
        }
        return Status.OK_STATUS;
    }

    @Override
    public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
        execute(monitor, info);
        return Status.OK_STATUS;
    }

    @Override
    public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
        if (storageIndex < 0) {
            return Status.CANCEL_STATUS;
        }
        service.deleteRow(storageIndex);
        viewService.refresh();

        return Status.OK_STATUS;
    }

}

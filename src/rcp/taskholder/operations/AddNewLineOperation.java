package rcp.taskholder.operations;

import javax.inject.Inject;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.PlatformUI;
import org.eclipse.e4.core.contexts.IEclipseContext;

import rcp.taskholder.model.Person;
import rcp.taskholder.services.PersonService;
import rcp.taskholder.services.ViewPartsService;

public class AddNewLineOperation extends AbstractOperation {

    private ViewPartsService viewService;
    private PersonService service;

    private Person storagePerson;

    {
        viewService = new ViewPartsService();
        IEclipseContext context = (IEclipseContext) PlatformUI.getWorkbench().getService(IEclipseContext.class);
        context.set(PersonService.class, new PersonService());
//        ContextInjectionFactory.inject(service, context);
        service = context.get(PersonService.class);
        	
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

package rcp.taskholder.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.ui.PlatformUI;

import rcp.taskholder.operations.AddNewLineOperation;

public class AddNewLineHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {

        IOperationHistory history = PlatformUI.getWorkbench().getOperationSupport().getOperationHistory();
        AbstractOperation addNewLineOperation = new AddNewLineOperation("Add New Line Operation");

        addNewLineOperation.addContext(PlatformUI.getWorkbench().getOperationSupport().getUndoContext());
        history.execute(addNewLineOperation, null, null);

        return null;
    }

}

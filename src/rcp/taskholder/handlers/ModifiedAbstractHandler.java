package rcp.taskholder.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.ui.PlatformUI;

public abstract class ModifiedAbstractHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {

        AbstractOperation operation = createOperation(event);
        IOperationHistory history = PlatformUI.getWorkbench().getOperationSupport().getOperationHistory();
        operation.addContext(PlatformUI.getWorkbench().getOperationSupport().getUndoContext());
        history.execute(operation, null, null);
        return null;
    }

    protected abstract AbstractOperation createOperation(ExecutionEvent event);

}

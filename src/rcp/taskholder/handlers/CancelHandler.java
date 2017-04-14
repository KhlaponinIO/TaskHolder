package rcp.taskholder.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.operations.AbstractOperation;

import rcp.taskholder.operations.CancelOperation;

public class CancelHandler extends ModifiedAbstractHandler {

    @Override
    protected AbstractOperation createOperation(ExecutionEvent event) {
        return new CancelOperation();
    }

}

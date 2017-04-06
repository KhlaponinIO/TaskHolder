package rcp.taskholder.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.operations.AbstractOperation;

import rcp.taskholder.operations.DeleteOperation;

public class DeleteRowHandler extends ModifiedAbstractHandler {

    protected AbstractOperation createOperation(ExecutionEvent event) {
        return new DeleteOperation();
    }

}

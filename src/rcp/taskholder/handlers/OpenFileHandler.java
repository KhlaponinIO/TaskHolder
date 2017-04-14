package rcp.taskholder.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.operations.AbstractOperation;

import rcp.taskholder.operations.OpenFileOperation;

public class OpenFileHandler extends ModifiedAbstractHandler {

    @Override
    protected AbstractOperation createOperation(ExecutionEvent event) {
        return new OpenFileOperation();
    }

}

package rcp.taskholder.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.operations.AbstractOperation;

import rcp.taskholder.operations.SaveRowOperation;

public class SaveRowHandler extends ModifiedAbstractHandler {

    @Override
    protected AbstractOperation createOperation(ExecutionEvent event) {
        return new SaveRowOperation();
    }

}

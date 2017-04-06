package rcp.taskholder.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.operations.AbstractOperation;

import rcp.taskholder.operations.PasteOperation;

public class PasteHandler extends ModifiedAbstractHandler {

    @Override
    protected AbstractOperation createOperation(ExecutionEvent event) {
        return new PasteOperation();
    }

}

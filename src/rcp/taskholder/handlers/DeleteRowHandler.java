package rcp.taskholder.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.ui.PlatformUI;

import rcp.taskholder.operations.DeleteOperation;

public class DeleteRowHandler extends AbstractHandler {


	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IOperationHistory history = PlatformUI.getWorkbench().getOperationSupport().getOperationHistory();
		AbstractOperation deleteOperation = new DeleteOperation("Delete Operation");
		
		deleteOperation.addContext(PlatformUI.getWorkbench().getOperationSupport().getUndoContext());
		history.execute(deleteOperation, null, null);

		return null;
	}

}

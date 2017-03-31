package rcp.taskholder.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import rcp.taskholder.view.TreePart;

public class OpenTreeHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		
		//TODO: need to close TablePart view and open TreePart view against here
		try {
			//open view in the current perspective
			HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().showView(TreePart.ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}

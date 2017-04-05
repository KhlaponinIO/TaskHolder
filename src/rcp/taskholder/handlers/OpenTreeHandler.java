package rcp.taskholder.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import rcp.taskholder.repository.GroupDataProvider;
import rcp.taskholder.view.TablePart;
import rcp.taskholder.view.TreePart;

public class OpenTreeHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {

        Event selectionEvent = (Event) event.getTrigger();
        MenuItem item = (MenuItem) selectionEvent.widget;

        IWorkbenchPage workbenchPage = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
        if (item.getSelection()) {
            // open tree view part in the current perspective and close the
            // table view part
            try {
                workbenchPage.showView(TreePart.ID);
                GroupDataProvider.getInstance().update();
                IViewPart tablePart = workbenchPage.findView(TablePart.ID);
                workbenchPage.hideView(tablePart);
            } catch (PartInitException e) {
                e.printStackTrace();
            }
        } else {
            // open table view part in the current perspective and close the
            // tree view part
            IViewPart treePart = workbenchPage.findView(TreePart.ID);
            workbenchPage.hideView(treePart);
            try {
                workbenchPage.showView(TablePart.ID);
            } catch (PartInitException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}

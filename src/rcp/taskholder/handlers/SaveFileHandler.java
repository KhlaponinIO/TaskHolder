package rcp.taskholder.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class SaveFileHandler implements IHandler {

    @Override
    public void addHandlerListener(IHandlerListener handlerListener) {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        // TODO Auto-generated method stub
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
        MessageDialog.openInformation(window.getShell(), "Title", "Some message");
        return null;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isHandled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void removeHandlerListener(IHandlerListener handlerListener) {
        // TODO Auto-generated method stub

    }

}

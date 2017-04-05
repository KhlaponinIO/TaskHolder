package rcp.taskholder.handlers;

import java.util.ResourceBundle;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import rcp.taskholder.util.PackageUtil;

public class ExitHandler extends AbstractHandler {

    private ResourceBundle rb;
    private final String EXIT_TITLE;
    private final String EXIT_MESSAGE;

    {
        rb = ResourceBundle.getBundle(PackageUtil.getPackageName(this.getClass()) + ".dialogs");
        EXIT_TITLE = rb.getString("ExitHandler.exit.title");
        EXIT_MESSAGE = rb.getString("ExitHandler.exit.message");
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {

        if (MessageDialog.openQuestion(null, EXIT_TITLE, EXIT_MESSAGE)) {
            HandlerUtil.getActiveWorkbenchWindow(event).close();
        }
        return null;
    }

}

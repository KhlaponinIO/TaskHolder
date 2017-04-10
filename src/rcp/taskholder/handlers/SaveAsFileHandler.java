package rcp.taskholder.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import rcp.taskholder.services.FileService;
import rcp.taskholder.services.PersonService;
import rcp.taskholder.util.ApplicationContextUtil;

public class SaveAsFileHandler extends AbstractHandler {

    private PersonService service;

    {
        service = ApplicationContextUtil.getFromContext(PersonService.class);
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {

        FileDialog fileDialog = new FileDialog(Display.getCurrent().getShells()[0], SWT.SAVE);
        fileDialog.setText("Save as");
        fileDialog.setFilterPath("D:/");
        String[] filterExt = { ".json", ".xml" };
        fileDialog.setFilterExtensions(filterExt);

        String path = fileDialog.open();

        FileService.saveDataToFile(service.getData(), path);

        return null;
    }

}

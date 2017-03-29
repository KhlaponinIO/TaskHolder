package rcp.taskholder.handlers;

import java.util.ResourceBundle;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;

import rcp.taskholder.services.FileService;
import rcp.taskholder.services.PersonService;
import rcp.taskholder.util.JsonFileWriter;
import rcp.taskholder.util.PackageUtil;

public class SaveFileHandler extends AbstractHandler {
    
    private PersonService service;
    private ResourceBundle rb;
    
    private final String SAVE_TITLE;
    private final String SAVE_MESSAGE;
    
    {
        rb = ResourceBundle.getBundle(PackageUtil.getPackageName(this.getClass()) + ".dialogs");
        SAVE_TITLE = rb.getString("SaveFileHandler.save.message.title");
        SAVE_MESSAGE = rb.getString("SaveFileHandler.save.message");
        
        service = new PersonService();
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        if (MessageDialog.openQuestion(null, SAVE_TITLE, SAVE_MESSAGE + " " + JsonFileWriter.PATH + "?")) {
            FileService.saveDataToFile(service.getData());
        }
        return null;
    }

}

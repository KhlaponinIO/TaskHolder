package rcp.taskholder.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import rcp.taskholder.model.Person;
import rcp.taskholder.services.ViewPartsService;
import rcp.taskholder.util.ApplicationContextUtil;

public class CopyHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        ViewPartsService viewService = ApplicationContextUtil.getFromContext(ViewPartsService.class);
        Person clipboardPerson;
        
        clipboardPerson = viewService.getSelectedPerson();
        if (clipboardPerson != null) {
            ApplicationContextUtil.setToAppContext("clipboardPerson", clipboardPerson);
        }

        return null;
    }

}

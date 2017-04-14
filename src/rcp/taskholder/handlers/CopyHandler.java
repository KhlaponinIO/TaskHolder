package rcp.taskholder.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import rcp.taskholder.model.Person;
import rcp.taskholder.services.PersonService;
import rcp.taskholder.services.ViewPartsService;
import rcp.taskholder.util.ApplicationContextUtil;

public class CopyHandler extends AbstractHandler {

    private PersonService service;
    private ViewPartsService viewService;

    {
        service = ApplicationContextUtil.getFromContext(PersonService.class);
        viewService = ApplicationContextUtil.getFromContext(ViewPartsService.class);
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        Person clipboardPerson;

        int index = viewService.getSelectionIndex();

        if (index >= 0) {
            clipboardPerson = service.getRow(index);
            ApplicationContextUtil.setToAppContext("clipboardPerson", clipboardPerson);
        }

        return null;
    }

}

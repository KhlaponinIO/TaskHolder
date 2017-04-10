package rcp.taskholder.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import rcp.taskholder.model.Person;
import rcp.taskholder.services.PersonService;
import rcp.taskholder.services.ViewPartsService;
import rcp.taskholder.util.ApplicationContextUtil;
import rcp.taskholder.util.ApplicationScope;

public class CopyHandler extends AbstractHandler {

    private PersonService service;
    private ApplicationScope scope;
    private ViewPartsService viewService;

    private Person clipboardPerson;

    {
        service = ApplicationContextUtil.getFromContext(PersonService.class);
        viewService = ApplicationContextUtil.getFromContext(ViewPartsService.class);
        scope = ApplicationScope.getInstance();
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {

        int index = viewService.getSelectionIndex();

        if (index >= 0) {
            clipboardPerson = service.getRow(index);
            scope.putElement("clipboardPerson", clipboardPerson);
        }

        return null;
    }

}

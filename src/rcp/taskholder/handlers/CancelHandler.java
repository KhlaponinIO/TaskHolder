package rcp.taskholder.handlers;

import static rcp.taskholder.util.ApplicationContextUtil.getFromContext;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import rcp.taskholder.model.Person;
import rcp.taskholder.services.PersonService;
import rcp.taskholder.services.ViewPartsService;

public class CancelHandler extends AbstractHandler {

    private PersonService service;
    private ViewPartsService viewService;

    {
        service = getFromContext(PersonService.class);
        viewService = getFromContext(ViewPartsService.class);
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        int storageIndex = -1;
        Person storagePerson;

        storageIndex = viewService.getSelectionIndex();
        if (storageIndex >= 0) {
            storagePerson = service.getRow(storageIndex);
            ((Text) getFromContext("nameTextField")).setText(storagePerson.getName());
            ((Text) getFromContext("groupTextField")).setText(storagePerson.getGroup());
            ((Button) getFromContext("checkTaskButton")).setSelection(storagePerson.isTaskDone());
        }
        return null;
    }

}

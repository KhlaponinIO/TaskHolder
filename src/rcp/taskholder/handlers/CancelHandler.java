package rcp.taskholder.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import rcp.taskholder.model.Person;
import rcp.taskholder.services.PersonService;
import rcp.taskholder.services.ViewPartsService;
import rcp.taskholder.util.ApplicationContextUtil;
import rcp.taskholder.util.ApplicationScope;

public class CancelHandler extends AbstractHandler {

    private PersonService service;
    private ApplicationScope scope;
    private ViewPartsService viewService;

    private int storageIndex = -1;
    private Person storagePerson;

    {
        service = ApplicationContextUtil.getFromContext(PersonService.class);
        viewService = ApplicationContextUtil.getFromContext(ViewPartsService.class);
        scope = ApplicationScope.getInstance();
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {

        storageIndex = viewService.getSelectionIndex();
        if (storageIndex >= 0) {
            storagePerson = service.getRow(storageIndex);
            ((Text) scope.getElement("nameTextField")).setText(storagePerson.getName());
            ((Text) scope.getElement("groupTextField")).setText(storagePerson.getGroup());
            ((Button) scope.getElement("checkTaskButton")).setSelection(storagePerson.isTaskDone());
        }
        return null;
    }

}

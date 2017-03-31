package rcp.taskholder.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.TableViewer;

import rcp.taskholder.model.Person;
import rcp.taskholder.services.PersonService;
import rcp.taskholder.util.ApplicationScope;

public class CopyHandler extends AbstractHandler {

	private PersonService data;
	private ApplicationScope scope;

	private Person clipboardPerson;

	{
		data = new PersonService();
		scope = ApplicationScope.getInstance();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		int index = ((TableViewer) scope.getElement("tableViewer")).getTable().getSelectionIndex();

		if (index >= 0) {
			clipboardPerson = data.getRow(index);
			scope.putElement("clipboardPerson", clipboardPerson);
		}

		return null;
	}

}

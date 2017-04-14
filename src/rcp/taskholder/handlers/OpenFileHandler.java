package rcp.taskholder.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import rcp.taskholder.repository.GroupDataProvider;
import rcp.taskholder.services.PersonService;
import rcp.taskholder.util.ApplicationContextUtil;

public class OpenFileHandler extends AbstractHandler {

    private PersonService service;

    {
        service = ApplicationContextUtil.getFromContext(PersonService.class);
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {

        FileDialog fileDialog = new FileDialog(Display.getCurrent().getShells()[0], SWT.OPEN);
        fileDialog.setText("Open");
        fileDialog.setFilterPath("D:/");
        String[] filterExtensions = { "*.*", ".json", ".xml" };
        fileDialog.setFilterExtensions(filterExtensions);

        String selected = fileDialog.open();

        service.setDataFromFile(selected);

        refresh();

        return null;
    }

    private void refresh() {
        TableViewer tableViewer = (TableViewer) ApplicationContextUtil.getFromContext("tableViewer");
        TreeViewer treeViewer = (TreeViewer) ApplicationContextUtil.getFromContext("treeViewer");

        if (tableViewer != null) {
            tableViewer.setInput(service.getData());
            tableViewer.refresh();
        }
        if (treeViewer != null) {
            GroupDataProvider.getInstance().update();
            treeViewer.refresh();
            treeViewer.expandAll();
        }
    }

}

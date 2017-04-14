package rcp.taskholder.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import rcp.taskholder.repository.GroupDataProvider;
import rcp.taskholder.services.PersonService;
import rcp.taskholder.util.ApplicationContextUtil;

public class OpenFileOperation extends AbstractOperation {
    
    private PersonService service;

    public OpenFileOperation() {
        super("OpenFileOperation");
        service = ApplicationContextUtil.getFromContext(PersonService.class);
    }

    @Override
    public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
        FileDialog fileDialog = new FileDialog(Display.getCurrent().getShells()[0], SWT.OPEN);
        fileDialog.setText("Open");
        fileDialog.setFilterPath("D:/");
        String[] filterExtensions = { "*.*", ".json", ".xml" };
        fileDialog.setFilterExtensions(filterExtensions);

        String selected = fileDialog.open();

        service.setDataFromFile(selected);

        refresh();
        return Status.OK_STATUS;
    }

    @Override
    public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
        // do nothing
        return Status.CANCEL_STATUS;
    }

    @Override
    public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
        // do nothing
        return Status.CANCEL_STATUS;
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

package rcp.taskholder.view;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.part.ViewPart;

import rcp.taskholder.handlers.CopyHandler;
import rcp.taskholder.handlers.PasteHandler;
import rcp.taskholder.model.Person;
import rcp.taskholder.model.TreeContentProvider;
import rcp.taskholder.repository.GroupDataProvider;
import rcp.taskholder.util.ApplicationScope;

public class TreePart extends ViewPart {

    public static final String ID = "rcp.taskholder.view.TreePart";

    private TreeViewer treeViewer;
    private ApplicationScope scope;

    public TreePart() {
        scope = ApplicationScope.getInstance();
    }

    @Override
    public void createPartControl(Composite parent) {

        instantiateTreeViewer(parent);

        // creating context for undo/redo operations
        IUndoContext undoContext = PlatformUI.getWorkbench().getOperationSupport().getUndoContext();

        UndoActionHandler undoAction = new UndoActionHandler(getSite(), undoContext);
        undoAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_UNDO);

        RedoActionHandler redoAction = new RedoActionHandler(getSite(), undoContext);
        redoAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_REDO);

        getViewSite().getActionBars().setGlobalActionHandler(ActionFactory.UNDO.getId(), undoAction);
        getViewSite().getActionBars().setGlobalActionHandler(ActionFactory.REDO.getId(), redoAction);

        // instantiate the handler service for copy/paste operations
        IHandlerService handlerService = getSite().getService(IHandlerService.class);
        CopyHandler copyHandler = new CopyHandler();
        PasteHandler pasteHandler = new PasteHandler();
        handlerService.activateHandler(org.eclipse.ui.IWorkbenchCommandConstants.EDIT_COPY, copyHandler);
        handlerService.activateHandler(org.eclipse.ui.IWorkbenchCommandConstants.EDIT_PASTE, pasteHandler);
    }

    private void instantiateTreeViewer(Composite parent) {
        treeViewer = new TreeViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL);
        treeViewer.getTree().setLinesVisible(true);
        treeViewer.getTree().setHeaderVisible(true);

        TreeViewerColumn viewerColumn1 = new TreeViewerColumn(treeViewer, SWT.LEFT);
        TreeColumn column1 = viewerColumn1.getColumn();
        column1.setText("Group/Name");
        column1.setWidth(160);
        viewerColumn1.setLabelProvider(new DelegatingStyledCellLabelProvider(new TreeGroupCellLabelProvider()));

        TreeViewerColumn viewerColumn2 = new TreeViewerColumn(treeViewer, SWT.LEFT);
        TreeColumn column2 = viewerColumn2.getColumn();
        column2.setText("Task done");
        column2.setWidth(100);
        viewerColumn2.setLabelProvider(new DelegatingStyledCellLabelProvider(new TaskTreeCellLabelProvider()));

        treeViewer.setContentProvider(new TreeContentProvider());
        treeViewer.setInput(GroupDataProvider.getInstance().getGroupsData());
        treeViewer.expandAll();
        addItemSelectionEvent();

        // create a menu manager and create context menu
        MenuManager menuManager = new MenuManager();
        Menu menu = menuManager.createContextMenu(treeViewer.getTree());
        treeViewer.getTree().setMenu(menu);
        getSite().registerContextMenu(menuManager, treeViewer); // register the menu with the framework
        getSite().setSelectionProvider(treeViewer); // makes the viewer selection available

        scope.putElement("treeViewer", treeViewer);
    }

    @Override
    public void setFocus() {
        // Auto-generated method stub
    }

    private void addItemSelectionEvent() {
        treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                showRowDataOnEditBar(selection, (Text) scope.getElement("nameTextField"),
                        (Text) scope.getElement("groupTextField"), (Button) scope.getElement("checkTaskButton"));
            }

        });
    }

    private void showRowDataOnEditBar(IStructuredSelection selection, Text nameTextField, Text groupTextField,
            Button checkTaskButton) {
        if (selection.getFirstElement() instanceof Person) {
            Person rowData = (Person) selection.getFirstElement();
            if (rowData != null) {
                nameTextField.setText(rowData.getName());
                groupTextField.setText(rowData.getGroup());
                checkTaskButton.setSelection(rowData.isTaskDone());
            }
        } else {
            nameTextField.setText("");
            groupTextField.setText("");
            checkTaskButton.setSelection(false);
        }
    }

    @Override
    public void dispose() {
        // remove TreeViewer instance from the application scope and than dispose the ViewPart
        scope.clearElement("treeViewer");
        super.dispose();
    }

}

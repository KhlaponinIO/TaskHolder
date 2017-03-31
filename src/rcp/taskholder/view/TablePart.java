package rcp.taskholder.view;

import java.util.ResourceBundle;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
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
import rcp.taskholder.services.PersonService;
import rcp.taskholder.util.ApplicationScope;
import rcp.taskholder.util.PackageUtil;

public class TablePart extends ViewPart {
    
    public static final String ID = "rcp.taskholder.view.TablePart";
    
    private TableViewer tableViewer;
    private PersonService data;
    private ApplicationScope scope;
    
    private ResourceBundle rb;
    private final String FIRST_COLUMN_NAME;
    private final String SECOND_COLUMN_NAME;
    private final String THIRD_COLUMN_NAME;

    {
        rb = ResourceBundle.getBundle(PackageUtil.getPackageName(this.getClass()) + ".elementsNames");
        FIRST_COLUMN_NAME = rb.getString("TablePart.first.column.name");
        SECOND_COLUMN_NAME = rb.getString("TablePart.second.column.name");
        THIRD_COLUMN_NAME = rb.getString("TablePart.third.column.name");
    }

    public TablePart() {
        data = new PersonService();
        scope = ApplicationScope.getInstance();
    }

    @Override
    public void createPartControl(Composite parent) {
        buildAndLayoutTable(parent);
        addRowSelectionEvent();
        
        
        IUndoContext undoContext = PlatformUI.getWorkbench().getOperationSupport().getUndoContext();
        
        UndoActionHandler undoAction = new UndoActionHandler(getSite(), undoContext);
        undoAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_UNDO);
        
        RedoActionHandler redoAction = new RedoActionHandler(getSite(), undoContext);
        redoAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_REDO);
        
        getViewSite().getActionBars().setGlobalActionHandler(ActionFactory.UNDO.getId(), undoAction);
        getViewSite().getActionBars().setGlobalActionHandler(ActionFactory.REDO.getId(), redoAction);
        
        IHandlerService handlerService = (IHandlerService) getSite().getService(IHandlerService.class);
        CopyHandler copyHandler = new CopyHandler();
        PasteHandler pasteHandler = new PasteHandler();
        handlerService.activateHandler(org.eclipse.ui.IWorkbenchCommandConstants.EDIT_COPY, copyHandler);
        handlerService.activateHandler(org.eclipse.ui.IWorkbenchCommandConstants.EDIT_PASTE, pasteHandler);
    }

    @Override
    public void setFocus() {
        
    }
    
    private void buildAndLayoutTable(Composite parent) {

        tableViewer = new TableViewer(parent, SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

        createColumns(parent, tableViewer);

        final Table table = tableViewer.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        tableViewer.setContentProvider(new ArrayContentProvider());
        tableViewer.setInput(data.getData());
        scope.putElement("tableViewer", tableViewer);

    }
    
    private void createColumns(Composite parent, TableViewer viewer) {

        TableViewerColumn column1 = createTableViewerColumn(FIRST_COLUMN_NAME, 200, 0);
//        nameEditingSupport = new NameEditingSupport(tableViewer);
//        column1.setEditingSupport(nameEditingSupport);
        column1.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Person data = (Person) element;
                return data.getName();
            }
        });

        TableViewerColumn column2 = createTableViewerColumn(SECOND_COLUMN_NAME, 100, 1);
        column2.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Person data = (Person) element;
                return data.getGroup();
            }
        });

        TableViewerColumn column3 = createTableViewerColumn(THIRD_COLUMN_NAME, 100, 2);
        column3.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Person data = (Person) element;
                return String.valueOf(data.isTaskDone());
            }
        });

    }
    
    private TableViewerColumn createTableViewerColumn(String title, int bound, int colNumber) {

        TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);

        TableColumn column = viewerColumn.getColumn();
        column.setText(title);
        column.setWidth(bound);
        column.setResizable(true);
        column.setMoveable(true);

        return viewerColumn;
    }
    
    public void addRowSelectionEvent() {
        tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

            @Override
            public void selectionChanged(final SelectionChangedEvent event) {

                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                showRowDataOnEditBar(selection, (Text) scope.getElement("nameTextField"), 
                        (Text) scope.getElement("groupTextField"), (Button) scope.getElement("checkTaskButton"));

            }
        });
    }
    
    private void showRowDataOnEditBar(IStructuredSelection selection, Text nameTextField, Text groupTextField,
            Button checkTaskButton) {
        Person rowData = (Person) selection.getFirstElement();
        if (rowData == null) {
            nameTextField.setText("");
            groupTextField.setText("");
            checkTaskButton.setSelection(false);
        } else {
            nameTextField.setText(rowData.getName());
            groupTextField.setText(rowData.getGroup());
            checkTaskButton.setSelection(rowData.isTaskDone());
        }
    }
    
}

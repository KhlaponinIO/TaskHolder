package rcp.taskholder.view;

import java.util.ResourceBundle;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.ObjectUndoContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.part.ViewPart;

import rcp.taskholder.util.ApplicationScope;
import rcp.taskholder.util.PackageUtil;

public class EditPart extends ViewPart {
    
    
    public static final String ID = "rcp.taskholder.view.EditPart";
    
    private Button newButton;
    private Button saveButton;
    private Button deleteButton;
    private Button cancelButton;
    
    private Text nameTextField;
    private Text groupTextField;
    private Button checkTaskButton;
    
    private ApplicationScope scope;
    
    private ResourceBundle rb;
    private final String NEW_BUTTON_NAME;
    private final String SAVE_BUTTON_NAME;
    private final String DELETE_BUTTON_NAME;
    private final String CANCEL_BUTTON_NAME;
    private final String NAME_LABEL;
    private final String GROUP_LABEL;
    private final String TASK_LABEL;
    
    {
        rb = ResourceBundle.getBundle(PackageUtil.getPackageName(this.getClass()) + ".elementsNames");
        NEW_BUTTON_NAME = rb.getString("EditPart.button.new");
        SAVE_BUTTON_NAME = rb.getString("EditPart.button.save");
        DELETE_BUTTON_NAME = rb.getString("EditPart.button.delete");
        CANCEL_BUTTON_NAME = rb.getString("EditPart.button.cancel");
        NAME_LABEL = rb.getString("EditPart.label.name");
        GROUP_LABEL = rb.getString("EditPart.label.group");
        TASK_LABEL = rb.getString("EditPart.label.task");
    }

    public EditPart() {
        scope = ApplicationScope.getInstance();
    }

    @Override
    public void createPartControl(Composite parent) {
        createFieldsAndButtons(parent);
        
        IUndoContext undoContext = PlatformUI.getWorkbench().getOperationSupport().getUndoContext();
        ObjectUndoContext editorUndoContext = new ObjectUndoContext(undoContext);
        
        UndoActionHandler undoAction = new UndoActionHandler(getSite(), editorUndoContext);
        undoAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_UNDO);
        
        RedoActionHandler redoAction = new RedoActionHandler(getSite(), editorUndoContext);
        redoAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_REDO);
        
        getViewSite().getActionBars().setGlobalActionHandler(ActionFactory.UNDO.getId(), undoAction);
        getViewSite().getActionBars().setGlobalActionHandler(ActionFactory.REDO.getId(), redoAction);
    }

    @Override
    public void setFocus() {
        //stub
    }
    
    private void createFieldsAndButtons(Composite parent) {

        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout grid = new GridLayout(4, false);
        composite.setLayout(grid);

        Label name = new Label(composite, SWT.LEFT);
        name.setText(NAME_LABEL);
        GridData nameGridData = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, true);
        name.setLayoutData(nameGridData);

        nameTextField = new Text(composite, SWT.SINGLE | SWT.BORDER | SWT.RIGHT);
        GridData nameTextFieldData = new GridData(SWT.FILL, SWT.BEGINNING, true, true);
        nameTextFieldData.horizontalAlignment = GridData.FILL;
        nameTextFieldData.horizontalSpan = 3;
        nameTextField.setLayoutData(nameTextFieldData);

        Label group = new Label(composite, SWT.LEFT);
        group.setText(GROUP_LABEL);
        GridData groupGridData = new GridData(SWT.FILL, SWT.BEGINNING, true, true);
        group.setLayoutData(groupGridData);

        groupTextField = new Text(composite, SWT.SINGLE | SWT.BORDER | SWT.RIGHT);
        GridData groupTextFieldData = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, true);
        groupTextFieldData.horizontalAlignment = GridData.FILL;
        groupTextFieldData.horizontalSpan = 3;
        groupTextField.setLayoutData(groupTextFieldData);

        Label task = new Label(composite, SWT.LEFT);
        task.setText(TASK_LABEL);
        GridData taskGridData = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, true);
        taskGridData.horizontalAlignment = GridData.FILL;
        taskGridData.horizontalSpan = 3;
        task.setLayoutData(taskGridData);

        checkTaskButton = new Button(composite, SWT.CHECK);
        GridData checkTaskButtonGrid = new GridData(SWT.RIGHT, SWT.BEGINNING, true, true);
        checkTaskButtonGrid.horizontalSpan = 1;
        checkTaskButton.setLayoutData(checkTaskButtonGrid);

        addButtons(composite);
        addButtonsListeners();
        
        //add data to scope
        scope.putElement("nameTextField", nameTextField);
        scope.putElement("groupTextField", groupTextField);
        scope.putElement("checkTaskButton", checkTaskButton);
    }

    private void addButtons(Composite parent) {

        newButton = new Button(parent, SWT.PUSH);
        newButton.setText(NEW_BUTTON_NAME);
        newButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));

        saveButton = new Button(parent, SWT.PUSH);
        saveButton.setText(SAVE_BUTTON_NAME);
        saveButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));

        deleteButton = new Button(parent, SWT.PUSH);
        deleteButton.setText(DELETE_BUTTON_NAME);
        deleteButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));

        cancelButton = new Button(parent, SWT.PUSH);
        cancelButton.setText(CANCEL_BUTTON_NAME);
        cancelButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
    }
    
    public void addButtonsListeners() {
        newButton.addListener(SWT.Selection, event -> {
            executeCommand("rcp.taskholder.addNewLine");
        });

        saveButton.addListener(SWT.Selection, event -> {
            executeCommand("rcp.taskholder.saveRow");
        });

        deleteButton.addListener(SWT.Selection, event -> {
            executeCommand("rcp.taskholder.delete");
        });

        cancelButton.addListener(SWT.Selection, event -> {
            executeCommand("rcp.taskholder.cancel");
        });
    }
    
    /**
     * Calling the command by id
     * 
     * @param command
     */
    private void executeCommand(String command) {
        // From a view you get the site which allow to get the service 
        IHandlerService handlerService = getSite().getService(IHandlerService.class);
        try {
            handlerService.executeCommand(command, null);
        } catch (Exception e) {
//            throw new RuntimeException("command " + command + " not found");
        	e.printStackTrace();
        }
    }

}

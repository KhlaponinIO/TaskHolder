package rcp.taskholder.view;

import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import rcp.taskholder.model.Person;
import rcp.taskholder.services.PersonService;
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
    
    private PersonService service;
    
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
        
    }

    @Override
    public void createPartControl(Composite parent) {
        createFieldsAndButtons(parent);
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
//        addTextFieldsListeners();
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
            service.addRow(new Person());
            System.out.println("new row created"); //stub
        });

        saveButton.addListener(SWT.Selection, event -> {
            System.out.println("data saved"); //stub
        });

        deleteButton.addListener(SWT.Selection, event -> {
            System.out.println("row deleted"); //stub
        });

        cancelButton.addListener(SWT.Selection, event -> {
            System.out.println("changes canceled"); //stub
        });
    }

}

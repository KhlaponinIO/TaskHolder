package rcp.taskholder.view;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

import rcp.taskholder.model.Person;

public class TablePart extends ViewPart {
    
    public static final String ID = "rcp.taskholder.view.TablePart";
    
    private TableViewer tableViewer;

    public TablePart() {
        
    }

    @Override
    public void createPartControl(Composite parent) {
        buildAndLayoutTable(parent);
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
//        tableViewer.setInput(dataService.getData());
        tableViewer.setInput(new java.util.ArrayList<Person>());

    }
    
    private void createColumns(Composite parent, TableViewer viewer) {

//        TableViewerColumn column1 = createTableViewerColumn(FIRST_COLUMN_NAME, 200, 0);
        TableViewerColumn column1 = createTableViewerColumn("Name", 200, 0);
//        nameEditingSupport = new NameEditingSupport(tableViewer);
//        column1.setEditingSupport(nameEditingSupport);
        column1.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Person data = (Person) element;
                return data.getName();
            }
        });

//        TableViewerColumn column2 = createTableViewerColumn(SECOND_COLUMN_NAME, 100, 1);
        TableViewerColumn column2 = createTableViewerColumn("Group", 100, 1);
        column2.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Person data = (Person) element;
                return data.getGroup();
            }
        });

//        TableViewerColumn column3 = createTableViewerColumn(THIRD_COLUMN_NAME, 100, 2);
        TableViewerColumn column3 = createTableViewerColumn("Task done", 100, 2);
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
    
}

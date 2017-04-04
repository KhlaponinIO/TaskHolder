package rcp.taskholder.view;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.part.ViewPart;

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

		IUndoContext undoContext = PlatformUI.getWorkbench().getOperationSupport().getUndoContext();

		UndoActionHandler undoAction = new UndoActionHandler(getSite(), undoContext);
		undoAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_UNDO);

		RedoActionHandler redoAction = new RedoActionHandler(getSite(), undoContext);
		redoAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_REDO);

		getViewSite().getActionBars().setGlobalActionHandler(ActionFactory.UNDO.getId(), undoAction);
		getViewSite().getActionBars().setGlobalActionHandler(ActionFactory.REDO.getId(), redoAction);

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
		scope.clearElement("treeViewer");
		super.dispose();
	}

}

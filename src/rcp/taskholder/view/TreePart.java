package rcp.taskholder.view;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.part.ViewPart;

import rcp.taskholder.model.Group;
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

	class TreeLabelProvider extends LabelProvider {

		@Override
		public Image getImage(Object element) {
			// TODO
			return null;
		}

		@Override
		public String getText(Object element) {
			if (element instanceof Group) {
				return "Group #" + element;
			}
			if (element instanceof Person) {
				return ((Person) element).getName();
			}
			return null;
		}
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
		treeViewer.setContentProvider(new TreeContentProvider());
		treeViewer.setLabelProvider(new TreeLabelProvider());
		treeViewer.setInput(GroupDataProvider.getInstance().getGroupsData()); 
		treeViewer.expandAll();
		
		scope.putElement("treeViewer", treeViewer);
	}

	@Override
	public void setFocus() {
		// Auto-generated method stub
	}
	
	@Override
	public void dispose() {
		scope.clearElement("treeViewer");
		super.dispose();
	}

}

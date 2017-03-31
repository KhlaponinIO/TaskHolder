package rcp.taskholder.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class TreePart extends ViewPart {
	
	public static final String ID = "rcp.taskholder.view.TreePart";

	public TreePart() {
		// Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		
		//stub
		Text text = new Text(parent, SWT.BORDER);
		text.setText("tree part");

	}

	@Override
	public void setFocus() {
		// Auto-generated method stub
	}

}

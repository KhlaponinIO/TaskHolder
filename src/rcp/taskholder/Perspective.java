package rcp.taskholder;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import rcp.taskholder.view.EditPart;
import rcp.taskholder.view.TablePart;

public class Perspective implements IPerspectiveFactory {

    @Override
    public void createInitialLayout(IPageLayout layout) {
        layout.setEditorAreaVisible(false);
        layout.addStandaloneView(TablePart.ID, false, IPageLayout.LEFT, 0.5f, layout.getEditorArea());
        layout.addStandaloneView(EditPart.ID, false, IPageLayout.RIGHT, 0.5f, layout.getEditorArea());
    }
}

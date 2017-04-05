package rcp.taskholder.view;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;

import rcp.taskholder.model.Person;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class TaskTreeCellLabelProvider extends LabelProvider implements IStyledLabelProvider {

    private ImageDescriptor descriptor;

    @Override
    public Image getImage(Object element) {
        if (element instanceof Person) {
            if (((Person) element).isTaskDone()) {
                descriptor = TaskTreeCellLabelProvider.getImageDescriptor("tick.png");
            } else {
                descriptor = TaskTreeCellLabelProvider.getImageDescriptor("cross.png");
            }
            Image image = descriptor.createImage();
            return image;
        } else {
            return null;
        }
    }

    private static ImageDescriptor getImageDescriptor(String name) {

        String iconPath = "icons/" + name;
        Bundle bundle = FrameworkUtil.getBundle(TaskTreeCellLabelProvider.class);
        URL url = FileLocator.find(bundle, new Path(iconPath), null);
        return ImageDescriptor.createFromURL(url);
    }

    @Override
    public StyledString getStyledText(Object element) {
        return new StyledString("");
    }

}

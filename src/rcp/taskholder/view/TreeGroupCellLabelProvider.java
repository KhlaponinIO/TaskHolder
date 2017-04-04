package rcp.taskholder.view;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import rcp.taskholder.model.Group;
import rcp.taskholder.model.Person;

public class TreeGroupCellLabelProvider extends LabelProvider implements IStyledLabelProvider {

	private ImageDescriptor descriptor;

	@Override
	public Image getImage(Object element) {
		if (element instanceof Person) {
			descriptor = TreeGroupCellLabelProvider.getImageDescriptor("eclipse16.png");
			Image image = descriptor.createImage();
			return image;
		} else {
			return null;
		}
	}

	private static ImageDescriptor getImageDescriptor(String name) {

		String iconPath = "icons/" + name;
		Bundle bundle = FrameworkUtil.getBundle(TreeGroupCellLabelProvider.class);
		URL url = FileLocator.find(bundle, new Path(iconPath), null);
		return ImageDescriptor.createFromURL(url);
	}

	@Override
	public StyledString getStyledText(Object element) {
		// TODO Auto-generated method stub
		if (element instanceof Group) {
			return new StyledString("Group #" + element);
		}
		if (element instanceof Person) {
			return new StyledString(((Person) element).getName());
		}

		return null;
	}

}

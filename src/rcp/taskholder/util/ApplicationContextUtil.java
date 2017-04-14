package rcp.taskholder.util;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.ui.PlatformUI;

/**
 * Utility class for getting access to Eclipse application context
 * 
 * @author Ihor Khlaponin
 */
public class ApplicationContextUtil {

    /**
     * Add new object to Eclipse application context
     * 
     * @param object for setting to the application context
     */
    public static void setToAppContext(Object object) {
        IEclipseContext context = PlatformUI.getWorkbench().getService(IEclipseContext.class);
        context.set(object.getClass().getName(), object);
    }
    
    
    /**
     * Add new object to Eclipse application context by name
     * 
     * @param name - name of the object
     * @param object for storing
     */
    public static void setToAppContext(String name, Object object) {
        IEclipseContext context = PlatformUI.getWorkbench().getService(IEclipseContext.class);
        context.set(name, object);
    }

    /**
     * Returns the instance of received class from application context.
     * If application context isn't have such class, than new instance of this class 
     * will be created and added to application context. After that instance of needed
     * will be returned. 
     * 
     * @param clazz - class that we need to obtain from the application context
     * @return instance of the needed class
     */
    public static <T> T getFromContext(Class<T> clazz) {
        IEclipseContext context = PlatformUI.getWorkbench().getService(IEclipseContext.class);
        T service = context.get(clazz);
        if (service != null) {
            return service;
        } else {
            try {
                context.set(clazz, clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return context.get(clazz);
        }
    }
    
    /**
     * Returns the object from application context by its name.
     * Returns <code>null</code> if context doesn't have such object
     * 
     * @param name
     * @return object by name or <code>null</code> if such object doesn't exists
     */
    public static Object getFromContext(String name) {
        IEclipseContext context = PlatformUI.getWorkbench().getService(IEclipseContext.class);
        Object object = context.get(name);
        return object;
    }
    
    public static void clearElement(String name) {
        IEclipseContext context = PlatformUI.getWorkbench().getService(IEclipseContext.class);
        context.remove(name);
    }

}

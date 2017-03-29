package rcp.taskholder.util;

public class PackageUtil {

    public static String getPackageName(Class clazz) {

        String className = clazz.getName();
        return className.substring(0, className.lastIndexOf('.'));
    }

}

package rcp.taskholder.util;

import java.util.HashMap;

public class ApplicationScope {
    
    /**
     * Contains the map of key-value pairs that contains the object in the application scope 
     */
    private HashMap<String, Object> scope;

    private static class SingletonHolder {
        private final static ApplicationScope INSTANCE = new ApplicationScope();
    }

    public static ApplicationScope getInstance() {
        return SingletonHolder.INSTANCE;
    }
    
    private ApplicationScope() {
        scope = new HashMap<>();
    }
    
    public Object getElement(String key) {
        return scope.get(key);
    }
    
    public void putElement(String key, Object value) {
        scope.put(key, value);
    }
}

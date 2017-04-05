package rcp.taskholder.util;

import java.util.HashMap;

/**
 * Global application scope for storing key-value pairs
 * 
 * @author Ihor Khlaponin
 */
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
    
    /**
     * Returns the element by its key
     * 
     * @param key - name of the element
     * @return object from the scope or <code>null</code> if map isn't contains such key
     */
    public Object getElement(String key) {
        return scope.get(key);
    }
    
    /**
     * Puts new element to the scope
     * 
     * @param key - String name
     * @param value - object for storing
     */
    public void putElement(String key, Object value) {
        scope.put(key, value);
    }
    
    /**
     * Removes element from scope by its key
     * 
     * @param key of the element
     */
    public void clearElement(String key) {
    	scope.remove(key);
    }
}

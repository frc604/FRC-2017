package com._604robotics.robotnik.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com._604robotics.robotnik.Settings;
import com._604robotics.robotnik.exceptions.NonExistentDataError;
import com._604robotics.robotnik.exceptions.OverwriteDataError;
import com._604robotics.robotnik.logging.Logger;

/**
 * A map containing data.
 */
public class DataMap implements Iterable<Map.Entry<String, Data>> {
    private final Map<String, Data> dataTable = new HashMap<String, Data>();

    /**
     * Adds data to the map.
     * @param name Name of the data.
     * @param data Data to add.
     */
    protected void add (String name, Data data) {
    	if (this.dataTable.containsKey(name)) {
    		Logger.warn("Attempting to add data " + name + " when this already exists");
    		if (Settings.DEBUG_THROW >= Settings.SET_DEBUG) {
    			throw new OverwriteDataError();
    		}
    	}
    	
        this.dataTable.put(name, data);
    }

    /**
     * Gets data from the map.
     * @param name Name of the data.
     * @return The retrieved data.
     * @throws NonExistentDataError
     */
    protected Data getData (String name) {
        Data returnData = this.dataTable.get(name);
        if (returnData == null) {
        	throw new NonExistentDataError("Attempted to access nonexistent data " + name);
        }
        
        return returnData;
    }
    
    /**
     * Gets the size of the data map.
     * @return The data map's size.
     */
    public int size () {
    	return this.dataTable.size();
    }

    @Override
    public Iterator<Map.Entry<String, Data>> iterator () {
        return this.dataTable.entrySet().iterator();
    }
}

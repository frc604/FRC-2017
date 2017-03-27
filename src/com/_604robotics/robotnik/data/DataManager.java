package com._604robotics.robotnik.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com._604robotics.robotnik.Safety;
import com._604robotics.robotnik.memory.IndexedTable;
import com._604robotics.robotnik.utils.FuzzyMatcher;

/**
 * Manages data.
 */
public class DataManager {
    private final Map<String, DataReference> dataTable;

    /**
     * Creates a data manager.
     * @param dataMap Map of data to manage.
     * @param table Table to contain the data values.
     */
    public DataManager (DataMap dataMap, final IndexedTable table) {
        this.dataTable = new HashMap<String, DataReference>();
        for (Map.Entry<String, Data> entry : dataMap) {
            this.dataTable.put(entry.getKey(), new DataReference(entry.getValue(), table.getSlice(entry.getKey())));
        }
    }

    /**
     * Gets a reference to data.
     * @param name Name of the data.
     * @return The retrieved data reference.
     */
    public DataReference getData (String name) {
        final DataReference ref = this.dataTable.get(name);
        if (ref == null) {
        	System.err.println("Data \"" + name + "\" does not exist.");
        	ArrayList<String> matching = new ArrayList<String>();
        	for (String data: this.dataTable.keySet()) {
        		if (FuzzyMatcher.match(data, name)<(0.3*name.length())) {
        			matching.add(data);
        		}
        	}
        	System.err.println("Did you mean:");
        	for (String datamatch:matching) {
        		System.err.print("    ");
        		System.err.println(datamatch);
        	}
        	throw new IllegalArgumentException("Data \"" + name + "\" does not exist.");
        }
        
        return ref;
    }

    /**
     * Updates the data manager.
     * @param safety Safety mode to operate with.
     */
    public void update (Safety safety) {
        for (DataReference ref : this.dataTable.values()) {
            ref.update(safety);
        }
    }
}

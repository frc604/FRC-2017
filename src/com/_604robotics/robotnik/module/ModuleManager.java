package com._604robotics.robotnik.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com._604robotics.robotnik.Safety;
import com._604robotics.robotnik.memory.IndexedTable;
import com._604robotics.robotnik.utils.FuzzyMatcher;

/**
 * Manages modules.
 */
public class ModuleManager {
    private final Map<String, ModuleReference> moduleTable;

    /**
     * Creates a module manager.
     * @param moduleMap Map of modules to manage.
     * @param table Table to store module data in.
     */
    public ModuleManager (ModuleMap moduleMap, final IndexedTable table) {
        this.moduleTable = new HashMap<String, ModuleReference>();
        for (Map.Entry<String, Module> entry : moduleMap) {
            this.moduleTable.put(entry.getKey(), new ModuleReference(entry.getKey(), entry.getValue(), table.getSubTable(entry.getKey())));
        }
    }

    /**
     * Gets a reference to a module.
     * @param name Name of the module.
     * @return The retrieved module reference.
     */
    public ModuleReference getModule (String name) {
        ModuleReference ref = this.moduleTable.get(name);
        if (ref == null) {
        	System.err.println("Module \"" + name + "\" does not exist.");
        	ArrayList<String> matching = new ArrayList<String>();
        	for (String module: this.moduleTable.keySet()) {
        		if (FuzzyMatcher.match(module, name)<(0.3*name.length())) {
        			matching.add(module);
        		}
        	}
        	System.err.println("Did you mean:");
        	for (String modmatch:matching) {
        		System.err.print("    ");
        		System.err.println(modmatch);
        	}
        	throw new IllegalArgumentException("Module \"" + name + "\" does not exist.");
        }
        
        return ref;
    }

    /**
     * Starts all modules.
     * @param safety Safety mode to operate under.
     */
    public void start (Safety safety) {
        for (ModuleReference ref : this.moduleTable.values()) {
            ref.start(safety);
        }
    }

    /**
     * Updates all modules.
     * @param safety Safety mode to operate under.
     */
    public void update (Safety safety) {
        for (ModuleReference ref : this.moduleTable.values()) {
            ref.update(safety);
        }
    }

    /**
     * Executes all modules.
     * @param safety Safety mode to operate under.
     */
    public void execute (Safety safety) {
        for (ModuleReference ref : this.moduleTable.values()) {
            ref.execute(safety);
        }
    }

    /**
     * Stops all modules.
     * @param safety Safety mode to operate under.
     */
    public void stop (Safety safety) {
        for (ModuleReference ref : this.moduleTable.values()) {
            ref.stop(safety);
        }
    }
}
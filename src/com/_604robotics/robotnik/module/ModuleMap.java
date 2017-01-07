package com._604robotics.robotnik.module;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com._604robotics.robotnik.exceptions.NonExistentModuleError;

/**
 * A map containing modules.
 */
public class ModuleMap implements Iterable<Map.Entry<String, Module>> {
    private final Map<String, Module> moduleTable = new HashMap<String, Module>();

    /**
     * Adds a module.
     * @param name Name of the module.
     * @param module Module to add.
     */
    protected void add (String name, Module module) {
        this.moduleTable.put(name, module);
    }

    /**
     * Gets a module.
     * @param name Name of the module.
     * @return The retrieved module.
     * @throws NonExistentModuleError
     */
    protected Module getModule (String name) {
        Module returnModule = this.moduleTable.get(name);
        if (returnModule == null) {
        	throw new NonExistentModuleError("Attempted to access nonexistent module" + name);
        }
        return returnModule;
    }

    @Override
    public Iterator<Map.Entry<String, Module>> iterator () {
        return this.moduleTable.entrySet().iterator();
    }
}

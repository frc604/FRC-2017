package com._604robotics.robot2017.modules;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.action.field.FieldMap;
import com._604robotics.robotnik.module.Module;
import com._604robotics.robotnik.trigger.TriggerMap;

public class DynamicToggle extends Module {
    private static enum DriveSwitch {
        ARCADE,
        TANK;
    }
    
    private DriveSwitch driveChange = DriveSwitch.TANK;
    
    public DynamicToggle () {
        this.set(new TriggerMap() {{
            add("Tank Drive", () -> driveChange == DriveSwitch.TANK);
            add("Arcade Drive", () -> driveChange == DriveSwitch.ARCADE);
        }});
        
        this.set(new ElasticController() {{
        	addDefault("Idle", new Action() {
        	});
            addDefault("Check Driver", new Action(new FieldMap () {{
                define("rightY", 0D);
                define("rightX", 0D);
            }}) {
                public void begin (ActionData data) {
                    driveChange = DriveSwitch.ARCADE;
                }
                
                public void run (ActionData data) {
                    if (driveChange == DriveSwitch.TANK) {
                        if (Math.abs(data.get("rightY")) <= 0.2 && Math.abs(data.get("rightX")) > 0.3) {
                            driveChange = DriveSwitch.ARCADE;
                        }
                    } else {
                        if (Math.abs(data.get("rightX")) <= 0.2 && Math.abs(data.get("rightY")) > 0.3) {
                            driveChange = DriveSwitch.TANK;
                        }
                    }
                }
            });
            
            addDefault("Check Override", new Action(new FieldMap () {{
                define("rightY", 0D);
                define("rightX", 0D);
            }}) {
                public void begin (ActionData data) {
                    driveChange = DriveSwitch.ARCADE;
                }
                
                public void run (ActionData data) {
                    if (driveChange == DriveSwitch.TANK) {
                        if (Math.abs(data.get("rightY")) <= 0.2 && Math.abs(data.get("rightX")) > 0.3) {
                            driveChange = DriveSwitch.ARCADE;
                        }
                    } else {
                        if (Math.abs(data.get("rightX")) <= 0.2 && Math.abs(data.get("rightY")) > 0.3) {
                            driveChange = DriveSwitch.TANK;
                        }
                    }
                }
            });
            
            add("OverrideTank", new Action() {
                public void begin (ActionData data) {
                    driveChange = DriveSwitch.TANK;
                }
            });
            
            add("OverrideArcade", new Action() {
                public void begin (ActionData data) {
                    driveChange = DriveSwitch.ARCADE;
                }
            });
        }});
    }
}

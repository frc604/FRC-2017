package com._604robotics.robot2017.modules;

import com._604robotics.robot2017.constants.Ports;
import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.action.controllers.StateController;
import com._604robotics.robotnik.action.field.FieldMap;
import com._604robotics.robotnik.data.Data;
import com._604robotics.robotnik.data.DataMap;
import com._604robotics.robotnik.module.Module;

import edu.wpi.first.wpilibj.AnalogInput;

public class Ultrasonic extends Module {

	private AnalogInput ultraL = new AnalogInput(Ports.ULTRASONIC_LEFT);
	private AnalogInput ultraR = new AnalogInput(Ports.ULTRASONIC_RIGHT);
	
	private double leftInches = 0;
	private double rightInches = 0;
	
	public Ultrasonic()
	{
		this.set(new DataMap() {{
			add("Left Inches", new Data() {
                public double run () {
                    return leftInches;
                }
            });
			add("Right Inches", new Data() {
				public double run () {
					return rightInches;
				}
			});
		}});
		this.set(new StateController() {{
            addDefault("Off", new Action() {
                public void run(ActionData data) {
                	
                }
            });

            add("On", new Action(new FieldMap() {{
            }}) {
                public void run(ActionData data) {
                	int leftTotal = 0;
                	for( int f=0; f<64; f++ )
                	{
                		leftTotal += ultraL.getValue();
                	}
                	leftTotal = leftTotal / 64;
                	double leftTemp = leftTotal / 20;
                	leftInches = leftTemp - 11.8;
                	int rightTotal = 0;
                	for( int f=0; f<64; f++ )
                	{
                		rightTotal += ultraR.getValue();
                	}
                	rightTotal = rightTotal / 64;
                	double rightTemp = rightTotal / 20;
                	rightInches = rightTemp - 11.8;
                }
            });
        }});
	}
	
}

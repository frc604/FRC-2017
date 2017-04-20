package com._604robotics.robot2017.modules;

import java.io.IOException;

import com._604robotics.robot2017.modes.TeleopMode;
import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.module.Module;

public class Recorder extends Module {
    private static final String RECORDING_FILE = "autonomous.log";

    public Recorder () {
		set(new ElasticController() {{
			addDefault("Idle", new Action());
			
			add("Record", new Action () {
			    @Override
				public void begin (ActionData data) {
				    TeleopMode.joystick.startRecording(RECORDING_FILE);
				}

			    @Override
				public void end (ActionData data) {
				    TeleopMode.joystick.stopRecording();
				}
			});
			
			add("Play", new Action () {
			    @Override
				public void begin (ActionData data) {
				    try {
                        TeleopMode.joystick.startPlaying(RECORDING_FILE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
				}

			    @Override
				public void end (ActionData data) {
				    TeleopMode.joystick.stopPlaying();
				}
			});
		}});
    }
}

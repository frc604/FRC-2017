package com._604robotics.robot2017.modules;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import com._604robotics.robotnik.action.Action;
import com._604robotics.robotnik.action.ActionData;
import com._604robotics.robotnik.action.controllers.ElasticController;
import com._604robotics.robotnik.module.Module;

import edu.wpi.cscore.CvSink;
import edu.wpi.first.wpilibj.CameraServer;

public class Vision extends Module {
    public Vision () {
        set(new ElasticController() {{
            addDefault("Idle");
            
            add("Record", new Action () {
                private Thread recordThread;

                @Override
                public void begin (ActionData data) {
                    recordThread = new Thread(() -> {
                        int index = 0;

                        try {
                            final CvSink cvSink = CameraServer.getInstance().getVideo();
                            final Mat source = new Mat();
                            
                            while (!Thread.interrupted()) {
                                try {
                                    cvSink.grabFrame(source);
                                    Imgcodecs.imwrite(index + ".jpg", source);
                                    ++index;
                                } catch (Throwable t) {
                                    t.printStackTrace();
                                }
                                
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    break;
                                }
                            }
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                    });
                    recordThread.start();
                }

                @Override
                public void end (ActionData data) {
                    if (recordThread != null) {
                        recordThread.interrupt();
                        recordThread = null;
                    }
                }
            });
        }});

        try {
            CameraServer.getInstance().startAutomaticCapture("cam0", "/dev/video0");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
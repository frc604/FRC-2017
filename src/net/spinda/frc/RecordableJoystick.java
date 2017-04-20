package net.spinda.frc;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import edu.wpi.first.wpilibj.Joystick;

public class RecordableJoystick extends Joystick {
    private enum State {
        NORMAL,
        RECORDING,
        PLAYING
    }

    // Default record interval = 50 ms
    public static final long DEFAULT_RECORD_INTERVAL = 50;
    
    private State state = State.NORMAL;

    private Thread recordThread;

    private JoystickRecording playbackRecording;
    private long playbackTimestamp;
    
    public RecordableJoystick (int port) {
        super(port);
    }

    public RecordableJoystick (int port, int numAxisTypes, int numButtonTypes) {
        super(port, numAxisTypes, numButtonTypes);
    }
    
    public boolean isRecording () {
        return state == State.RECORDING;
    }
    
    public boolean isPlaying () {
        return state == State.PLAYING;
    }

    @Override
    public double getRawAxis (int axis) {
        if (playbackRecording == null) {
            return super.getRawAxis(axis);
        }
        return playbackRecording.getAxisAtTime(axis, getPlaybackTime());
    }

    @Override
    public boolean getRawButton (int button) {
        if (playbackRecording == null) {
            return super.getRawButton(button);
        }
        return playbackRecording.getButtonAtTime(button, getPlaybackTime());
    }
    
    public void startRecording (String recordingFilePath, long recordInterval) {
        enterState(State.RECORDING);
        
        recordThread = new Thread(() -> {
            try (final DataOutputStream out =
                    new DataOutputStream(new BufferedOutputStream(new FileOutputStream(recordingFilePath)))) {
                out.writeLong(recordInterval);

                final int axisCount = getAxisCount();
                final int buttonCount = getButtonCount();

                out.writeInt(axisCount);
                out.writeInt(buttonCount);

                while (isRecording()) {
                    for (int i = 0; i < axisCount; ++i) {
                        out.writeDouble(getRawAxis(i));
                    }

                    for (int i = 0; i < buttonCount; ++i) {
                        out.writeByte(getRawButton(i) ? 1 : 0);
                    }

                    try {
                        Thread.sleep(recordInterval);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            } catch (IOException e) {
                // We don't have anywhere else to throw the exception to,
                // since this is running in the background.
                throw new RuntimeException(e);
            } finally {
                state = State.NORMAL;
            }
        });

        recordThread.start();
    }
    
    public void startRecording (String recordingFilePath) {
        startRecording(recordingFilePath, DEFAULT_RECORD_INTERVAL);
    }

    public void stopRecording () {
        if (!isRecording()) {
            return;
        }

        state = State.NORMAL;
        recordThread.interrupt();
        recordThread = null;
    }
    
    public void startPlaying (JoystickRecording recording) {
        enterState(State.PLAYING);
        playbackRecording = recording;
        playbackTimestamp = System.currentTimeMillis();
    }
    
    public void startPlaying (String recordingFilePath) throws IOException {
        startPlaying(JoystickRecording.load(recordingFilePath));
    }

    public void stopPlaying () {
        if (!isPlaying()) {
            return;
        }

        state = State.NORMAL;
        playbackRecording = null;
    }
    
    private long getPlaybackTime () {
        return System.currentTimeMillis() - playbackTimestamp;
    }
    
    private void enterState (State state) {
        if (this.state != State.NORMAL) {
            throw new IllegalStateException(
                    "Cannot start " + state.toString().toLowerCase() +
                    " when RecordableJoystick is already " + state.toString().toLowerCase());
        }
        
        this.state = state;
    }
}
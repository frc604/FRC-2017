package net.spinda.frc;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class JoystickRecording {
    private final ByteBuffer buffer;
    
    private final long frameInterval;
    private final int frameLength;
    private final int frameButtonsOffset;

    private final int axisCount;
    private final int buttonCount;
    
    private JoystickRecording (ByteBuffer buffer, long frameInterval, int axisCount, int buttonCount) {
        this.buffer = buffer;
        this.frameInterval = frameInterval;
        this.axisCount = axisCount;
        this.buttonCount = buttonCount;
        
        frameButtonsOffset = axisCount * Double.SIZE;
        frameLength = frameButtonsOffset + buttonCount;
    }
    
    public double getAxisAtTime (int axis, long time) {
        if (axis >= 0 && axis < axisCount) {
            return buffer.getDouble(getFrameOffsetAtTime(time) + axis * Double.SIZE);
        } else {
            return 0;
        }
    }
    
    public boolean getButtonAtTime (int button, long time) {
        if (button >= 0 && button < buttonCount) {
            return buffer.get(getFrameOffsetAtTime(time) + frameButtonsOffset + button) == 1;
        } else {
            return false;
        }
    }
    
    private int getFrameOffsetAtTime (long time) {
        return frameLength * (int) (time / frameInterval);
    }

    public static JoystickRecording load (String recordingFilePath) throws IOException {
        try (final FileInputStream fileStream = new FileInputStream(recordingFilePath);
             final DataInputStream dataStream = new DataInputStream(fileStream)) {
            final long frameInterval = dataStream.readLong();
            final int axisCount = dataStream.readInt();
            final int buttonCount = dataStream.readInt();

            final FileChannel channel = fileStream.getChannel();
            final int bufferSize = (int) (channel.size() - channel.position());
            final ByteBuffer buffer = ByteBuffer.allocateDirect(bufferSize);
            fileStream.getChannel().read(buffer);
            
            return new JoystickRecording(buffer, frameInterval, axisCount, buttonCount);
        }
    }
}
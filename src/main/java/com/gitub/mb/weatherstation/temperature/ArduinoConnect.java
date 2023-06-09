package com.gitub.mb.weatherstation.temperature;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class ArduinoConnect {
    private static final int PORT_NO = 0;
    private static final int RECEIVING_PACKET_SEQUENCE_MILLIS = 1000;
    private final StringBuilder measurment;
    private final SerialPort comPort = SerialPort.getCommPorts()[PORT_NO];


    public ArduinoConnect(StringBuilder measurments) {
        this.measurment = measurments;
    }

    public StringBuilder getMeasurment() {
        openPort();
        InputStream in = comPort.getInputStream();

        try {
            int bytesPerPacket = getBytesPerPacket();
            for (int j = 0; j < bytesPerPacket; ++j) {
                measurment.append((char) in.read());
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            comPort.closePort();
        }

        return measurment;
    }

    private void openPort() {
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        comPort.flushIOBuffers();
    }

    private int getBytesPerPacket() throws InterruptedException {
        Thread.sleep(RECEIVING_PACKET_SEQUENCE_MILLIS);
        return comPort.bytesAvailable();
    }

}



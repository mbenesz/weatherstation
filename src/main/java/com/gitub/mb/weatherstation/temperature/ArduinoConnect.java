package com.gitub.mb.weatherstation.temperature;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class ArduinoConnect {
    private static final int NO_OF_MEASURMENTS = 4;
    private static final int DATA_INPUT_SEQUENCE_MILISEC = 1000;
    private static final int PORT_NO = 0;
    private SerialPort comPort;
    public final StringBuilder measurments;
    private final Scanner scanner;

    public ArduinoConnect(SerialPort comPort, StringBuilder measurments, Scanner scanner) {
        this.comPort = comPort;
        this.measurments = measurments;
        this.scanner = scanner;
    }

    private void openPort() {
        SerialPort[] comPorts = SerialPort.getCommPorts();
        comPort = comPorts[PORT_NO];
        comPort.openPort();
    }

    public StringBuilder getMeasurments(){
        openPort();

        try {
            for (int j = 0; j < NO_OF_MEASURMENTS; j++) {
                Thread.sleep(DATA_INPUT_SEQUENCE_MILISEC);

                int i1 = comPort.bytesAvailable();
                if (i1 > 0) {
                    byte[] readBuffer = new byte[i1];
                    comPort.readBytes(readBuffer, readBuffer.length);

                    for (byte b : readBuffer) {
                        char c = (char) b;
                        measurments.append(c);
                    }
                } else
                    System.out.println("No data to proceed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            comPort.closePort();
            scanner.close();
        }

        return measurments;
    }

}



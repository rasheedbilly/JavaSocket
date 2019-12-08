/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terminal;

import java.util.Arrays;

/**
 *
 * @author Rasheed
 */
public class Packet {
    
    private int sequenceNumber;
    private int indicater;
    private byte[] payload;

    public Packet(int sequenceNumber, int indicater, byte[] payload) {
        this.sequenceNumber = sequenceNumber;
        this.indicater = indicater;
        this.payload = payload;
    }
    
    public Packet(byte[] fullPacket){
        sequenceNumber = fullPacket[0];
        indicater = fullPacket[3];
        payload = Arrays.copyOfRange(fullPacket, 3, fullPacket.length);
    }
    

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public int getIndicater() {
        return indicater;
    }

    public byte[] getPayload() {
        return payload;
    }
    
    
    
}

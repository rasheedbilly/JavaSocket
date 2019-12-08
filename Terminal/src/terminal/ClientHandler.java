/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terminal;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rasheed
 */
class ClientHandler extends Thread {

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    final byte[] page;

    private Integer size;
    private Integer payloadLength;
    private Integer timeout;
    private String userID;

    // Constructor 
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, byte[] page) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.page = page;
    }

    @Override
    public void run() {

        String received;

        try {
            dos.writeUTF("Put the shit in");
            received = dis.readUTF();

            received = "500 100 1 xyz";

            //Parameter assignment
            size = Integer.valueOf(received.split(" ")[0]);
            payloadLength = Integer.valueOf(received.split(" ")[1]);
            timeout = Integer.valueOf(received.split(" ")[2]);
            userID = received.split(" ")[3];

        } catch (IOException ex) {
        }

        //Helpful numbers
        int numPackets = page.length / size;
        int indicator = 1;
        boolean notLast = true;
        int start = 0;
        int count = 0;

        while (true) {
            try {
                //dos.writeUTF(Arrays.toString(Arrays.copyOfRange(page, start, start+payloadLength)));
                
                //Send byte[] to Client
                if(count <= numPackets-2)
                    notLast = true;
                else
                    notLast = false;
                
                byte[] packet = getPacket(intToByte(count%128), notLast, Arrays.copyOfRange(page, start, start+payloadLength));
                dos.writeUTF(byteArrayToString(packet));
                start = start + payloadLength;
                count++;
                //Exit
                //If payload size < payload buffer -> indicator = 0
                if (count >= numPackets) {
                    break;
                }
            } catch (IOException ex) {}

            if (!notLast) 
                break;
            
        }
        System.out.println("Done!");

    }

    private byte[] getPacket(byte sn, boolean ind, byte[] pl) throws IOException {
        //Create Header
        //a sequence number sn, the page content size b, the number of bytes nb of page content in the packet, and an indicator 
        byte[] header = new byte[]{sn, intToByte(pl.length), intToByte(pl.length), booleanToByte(ind)};

        //Add payload
        //sn=8, size=8, length=8, i=1 -> offset = 25
        return concatinateByteArray(header, pl);

        //Send
    }
    
    private String ByteArrayToString(byte[] ba, int delta, int start, int end){
        return Arrays.toString(ba);
    }
    
    private byte[] StringToByteArray(String str){
        return str.getBytes();
    }

    private static byte booleanToByte(boolean b) {
        boolean vIn = b;
        return (byte) (vIn ? 1 : 0);
    }

    private static byte intToByte(int i) {
        if (i < 278) {
            return (byte) i;
        } else {
            return -1;
        }
    }

    private byte[] concatinateByteArray(byte[] a, byte[] b) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(a);
        outputStream.write(b);

        return outputStream.toByteArray();
    }
    
    private String byteArrayToString(byte[] ar){
        return Arrays.toString(ar);
    }
    
    private byte[] getSubByteArray(byte[] a, int start, int stop){
        return Arrays.copyOfRange(a, start, stop);
    }

}

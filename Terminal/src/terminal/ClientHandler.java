/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terminal;

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
        int sequenceNumber = 0;
        int start = 0;
        int count = 0;

        while (true) {
            try {
                //dos.writeUTF("received" + String.valueOf(new Random().nextInt(255)));
                dos.writeUTF(new String(page).substring(start, start+payloadLength));
                start = start+payloadLength;
                count++;
                //Exit
                //If payload size < payload buffer -> indicator = 0
                if(count >= numPackets)
                    break;
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (indicator == 0) {
                break;
            }
        }
        System.out.println("Done!");

    }

}

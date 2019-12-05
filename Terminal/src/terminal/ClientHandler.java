/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terminal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
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
        String toreturn;

        try {
            dos.writeUTF("Put the shit in");
            received = dis.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while (true) {
            try {
                dos.writeUTF("received"+String.valueOf(new Random().nextInt(255)));
                
                //received = dis.readUTF();

                //dos.writeUTF(new String(page).substring(0, 100));
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}

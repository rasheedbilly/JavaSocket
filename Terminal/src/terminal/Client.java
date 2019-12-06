/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terminal;

/**
 *
 * @author Rasheed
 */
// Java implementation for a client 
// Save file as Client.java 
import java.io.*;
import java.net.*;
import java.util.Scanner;

// Client class 
public class Client {

    public Client() {

        (new Thread() {
            @Override
            public void run() {
                runClient();
            }
        }).start();
    }

    public void runClient() {
        try {
            Scanner scn = new Scanner(System.in);

            // getting localhost ip 
            InetAddress ip = InetAddress.getByName("localhost");

            // establish the connection with server port 22122 
            Socket s = new Socket(ip, 22122);

            // obtaining input and out streams 
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            // the following loop performs the exchange of 
            // information between client and client handler 
            System.out.println("Client: " + dis.readUTF());
            String tosend = scn.nextLine();
            while (true) {

                dos.writeUTF(tosend);

                // If client sends exit,close this connection 
                // and then break from the while loop 
                if (tosend.equals("Exit")) {
                    System.out.println("Client: Closing this connection : " + s);
                    s.close();
                    System.out.println("Client: Connection closed");
                    break;
                }

                // printing date or time as requested by client 
                String received = dis.readUTF();
                System.out.println(received);
            }

            // closing resources 
            scn.close();
            dis.close();
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] appendPage(byte[] a, byte[] b) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(a);
        outputStream.write(b);

        return outputStream.toByteArray();
    }

    private void printPage(byte[] page) {

    }

}

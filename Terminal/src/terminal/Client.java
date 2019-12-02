/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terminal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.awt.event.*;
import java.util.Scanner;

/**
 *
 * @author Rasheed
 */
public class Client {

    private static Socket socket;

    //input Parameters
    int size = 0;
    int payloadLength = 0;
    int timeout = 0;
    String userID = "Empty";

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
            int port = 22122;
            InetAddress address = InetAddress.getByName("localhost");
            socket = new Socket(address, port);

            //Send the message to the server
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            String number = "2";

            String sendMessage = number + "\n";
            bw.write(sendMessage);
            bw.flush();
            System.out.println("Client: Message sent to the server : " + sendMessage);
            //clientArea.append("Message sent to the server : " + sendMessage);

            //Get the return message from the server
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String message = br.readLine();
            System.out.println("Client: Message received from the server : " + message);
            //clientArea.append("Message received from the server : " + message);

            getInput();
            printParameters();

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            //Closing the socket
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getInput() {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter values [Size, PayloadLength, Timeout, userID]\n"
                + "Entering 'X' will enter the default values of '500 100 1 xyz'");
        //String s = this.clientInput.getText();
        String s = in.nextLine();
        if (s.equals("x")) {
            s = "500 100 1 xyz";
        }
        //String[] ary = "abc".split("");
        //inputArray = new String[4];
        //inputArray = s.split(" ");

        //Parameter assignment
        size = Integer.valueOf(s.split(" ")[0]);
        payloadLength = Integer.valueOf(s.split(" ")[1]);
        timeout = Integer.valueOf(s.split(" ")[2]);
        userID = s.split(" ")[3];
    }

    public void printParameters() {
        System.out.printf("Size: %d\n", size);
        System.out.printf("Payload Length: %d\n", payloadLength);
        System.out.printf("Timeout: %d\n", timeout);
        System.out.println("User ID: " + userID);

//        clientArea.append("\nSize: " + size);
//        clientArea.append("\nPayload Length:  " + payloadLength);
//        clientArea.append("\nTimeout: " + timeout);
//        clientArea.append("\nUser ID: " + userID);
    }

}

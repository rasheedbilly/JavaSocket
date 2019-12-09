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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// Client class 
public class Client {

    private ArrayList<Packet> packets;
    private byte[] page;
    private Integer size;
    private Integer payloadLength;
    private Integer timeout;
    private String userID;

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
            packets = new ArrayList<>();

            // getting localhost ip 
            InetAddress ip = InetAddress.getByName("localhost");

            // establish the connection with server port 22122 
            Socket s = new Socket(ip, 22122);

            // obtaining input and out streams 
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            //Get Client parameters
            System.out.println("Client: " + dis.readUTF());

            String tosend = scn.nextLine();
            dos.writeUTF(tosend);
            if(tosend.equals(""))
                tosend = "500 100 1 xyz";
            
            //Parameter assignment
            size = Integer.valueOf(tosend.split(" ")[0]); //b?
            payloadLength = Integer.valueOf(tosend.split(" ")[1]);
            timeout = Integer.valueOf(tosend.split(" ")[2]);
            userID = tosend.split(" ")[3];
            
            long T1 = System.currentTimeMillis(), T2;
            int bytesSoFar = 0;
            int count = 0;
            Packet p;

            while (true) {
                //incoming data stream from Client Handler
                String received = dis.readUTF();
                
                //Recorded when stream was recieved
                T2 = System.currentTimeMillis();
                
                //Adds revieved data into packet ArrayList
                p = new Packet(StringToByteArray(received));
                packets.add(p);
                
                if((T2-T1) >= timeout){
                    System.out.println("Client: timeout");
                }
                if(p.getTotalSize() > size){
                    System.out.println("Client: size exceeded");
                }
                
                //Adds number of bytes recieved so far
                bytesSoFar += p.getPayload().length;

                System.out.println("Client received:" + received);
                if (packets.get(count).getIndicater() == 0) {
                    break;
                }
                count++;
            }

            System.out.println("\nOkay\nLoading page..\n");
            System.out.println(new String(loadPage()));
            System.out.println("\nPage loaded");

            s.close();
            scn.close();
            dis.close();
            dos.close();
            System.out.println("\nClient: Connection closed");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads page into one byte array
     * @return
     * @throws IOException 
     */
    private byte[] loadPage() throws IOException {
        byte[] array = new byte[0];
        for (int i = 0; i < packets.size(); i++) {
            array = appendPage(array, packets.get(i).getPayload());
        }
        return array;
    }

    /**
     * Concatinates two byte arrays
     * @param a
     * @param b
     * @return
     * @throws IOException 
     */
    private byte[] appendPage(byte[] a, byte[] b) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(a);
        outputStream.write(b);

        return outputStream.toByteArray();
    }

    /**
     * Converts String to byte array
     * @param str
     * @return 
     */
    private static byte[] StringToByteArray(String str) {

        String[] byteValues = str.substring(1, str.length() - 1).split(",");
        byte[] bytes = new byte[byteValues.length];

        for (int i = 0, len = bytes.length; i < len; i++) {
            bytes[i] = Byte.parseByte(byteValues[i].trim());
        }

        return bytes;
    }
    
    private static void Sort(ArrayList al){
        int flips = al.size()/128;
        ArrayList temp;
        for(int i = 0; i < flips; i++){
            
        }
    }

    private static void quickSort(byte[] array, int left, int right) {

        int i = left;
        int j = right;
        // find pivot number, we will take it as mid
        int pivot = array[left + (right - left) / 2];

        while (i <= j) {
            /**
             * In each iteration, we will increment left until we find element
             * greater than pivot We will decrement right until we find element
             * less than pivot
             */
            while (array[i] < pivot) {
                i++;
            }
            while (array[j] > pivot) {
                j--;
            }
            if (i <= j) {
                byte temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        // call quickSort() method recursively
        if (left < j) {
            quickSort(array, left, j);
        }
        if (i < right) {
            quickSort(array, i, right);
        }
    }
    private static void exchange(byte[] array, int i, int j) {
        byte temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}

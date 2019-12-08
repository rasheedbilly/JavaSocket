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

            //Read byte[]
            //System.out.println("Client: " + dis.read);
            String tosend = scn.nextLine();
            dos.writeUTF(tosend);
            int count = 0;
            while (true) {
                //incoming data stream
                String received = dis.readUTF();
                //packets.add(new Packet(new byte[]{1, 22, 22, 1, 24, 24, 34}));
                packets.add(new Packet(StringToByteArray(received)));

                // If client sends exit,close this connection 
                // and then break from the while loop 
                if (tosend.equals("Exit")) { // || received.getBytes()[4] == 0
                    System.out.println("Client: Closing this connection : " + s);
                    s.close();
                    System.out.println("Client: Connection closed");
                    break;
                }

                //System.out.println("Client received:" + received);
                if(packets.get(count).getIndicater() == 0){
                    break;
                }
                count++;
            }
            System.out.println("Loading page..");
            System.out.println(new String(loadPage()));
            System.out.println("Page loaded");
            
            
            
            // closing resources 
            scn.close();
            dis.close();
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] loadPage() throws IOException {
        byte[] array = new byte[0];
        for(int i = 0; i < packets.size(); i++){
            array = appendPage(array, packets.get(i).getPayload());
        }
        return array;
    }

    private byte[] appendPage(byte[] a, byte[] b) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(a);
        outputStream.write(b);

        return outputStream.toByteArray();
    }

    private String ByteArrayToString(byte[] ba) {
        return Arrays.toString(ba);
    }

    private static byte[] StringToByteArray(String str) {

        String[] byteValues = str.substring(1, str.length() - 1).split(",");
        byte[] bytes = new byte[byteValues.length];

        for (int i = 0, len = bytes.length; i < len; i++) {
            bytes[i] = Byte.parseByte(byteValues[i].trim());
        }

        return bytes;
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

    private void printByteArrayList(ArrayList al) {
        for (int i = 0; i < al.size(); i++) {
            System.out.println(al.get(i));
        }
    }
}

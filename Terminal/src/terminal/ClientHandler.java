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
import java.net.Socket;
import java.util.Arrays;

/**
 *
 * @author Rasheed
 */
class ClientHandler extends Thread {

    private final DataInputStream dis;
    private final DataOutputStream dos;
    private final Socket s;
    private final byte[] page, b;

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
        this.b = toBytes(page.length);
    }

    @Override
    public void run() {

        String received;

        try {
            dos.writeUTF("Please enter parameters");
            received = dis.readUTF();
            if (received.equals("")) {
                received = "500 100 1 xyz";
            }

            //Parameter assignment
            size = Integer.valueOf(received.split(" ")[0]); //b?
            payloadLength = Integer.valueOf(received.split(" ")[1]);
            timeout = Integer.valueOf(received.split(" ")[2]);
            userID = received.split(" ")[3];

        } catch (IOException ex) {
        }

        //Helpful numbers
        int numPackets = size / payloadLength;
        boolean notLast = true;
        int start = 0;
        int count = 0;

        while (true) {
            try {
                //Send byte[] to Client
                if (count <= numPackets - 2) {
                    notLast = true;
                } else {
                    notLast = false;
                }

                byte[] packet = getPacket((byte) (count % 128), notLast, Arrays.copyOfRange(page, start, start + payloadLength));
                dos.writeUTF(byteArrayToString(packet));
                start = start + payloadLength;
                count++;
                //Exit
                //If payload size < payload buffer -> indicator = 0
                if (count >= numPackets) {
                    break;
                }
            } catch (IOException ex) {
            }

            if (!notLast) {
                break;
            }
        }
        System.out.println("Done!");
    }

    /**
     * Converts integer to 32 binary represented as 4 bytes
     * @param i
     * @return 
     */
    private static byte[] toBytes(int i) {
        byte[] result = new byte[4];

        result[0] = (byte) (i >> 24);
        result[1] = (byte) (i >> 16);
        result[2] = (byte) (i >> 8);
        result[3] = (byte) (i /*>> 0*/);

        return result;
    }

    /**
     * Creates a packet of data
     * A byte array that contains a header and the payload
     * @param sn
     * @param ind
     * @param pl
     * @return
     * @throws IOException 
     */
    private byte[] getPacket(byte sn, boolean ind, byte[] pl) throws IOException {
        //Create Header
        //a sequence number sn, the page content size b, the number of bytes nb of page content in the packet, and an indicator 
        //[SeqNum, b, pl size, indicator]
        //byte[] header = new byte[]{sn, intToByte(pl.length), intToByte(pl.length), booleanToByte(ind)};
        byte[] header = new byte[]{sn, b[0], b[1], b[2], b[3], intToByte(pl.length), booleanToByte(ind)};

        //Add payload
        return concatinateByteArray(header, pl);
    }

    /**
     * Converts a boolean variable to a byte
     * true = 1
     * false = 0
     * @param b
     * @return 
     */
    private static byte booleanToByte(boolean b) {
        boolean vIn = b;
        return (byte) (vIn ? 1 : 0);
    }

    /**
     * Converts an integer into a byte
     * @param i
     * @return 
     */
    private static byte intToByte(int i) {
        if (i < 278) {
            return (byte) i;
        } else {
            return -1;
        }
    }

    /**
     * Concatinates two byte arrays
     * @param a
     * @param b
     * @return
     * @throws IOException 
     */
    private byte[] concatinateByteArray(byte[] a, byte[] b) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(a);
        outputStream.write(b);

        return outputStream.toByteArray();
    }

    /**
     * Converts a byte array into a String
     * @param ar
     * @return 
     */
    private String byteArrayToString(byte[] ar) {
        return Arrays.toString(ar);
    }
}

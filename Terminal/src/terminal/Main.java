/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terminal;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ProtocolException;

/**
 *
 * @author Rasheed
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //MontyHall mh = new MontyHall();

        Server server = new Server();
        try {
            server.GET("http://www.ni.com/product-documentation/12402/en/");
        } catch (ProtocolException ex) {} 
        catch (IOException ex) {}

        Client client = new Client();


            //trash();
    }

    private static void trash() {
        String s = "500 100 1 xyz";
        System.out.println("Original string: "+s);
        int size = Integer.valueOf(s.split(" ")[0]);
        int payloadLength = Integer.valueOf(s.split(" ")[1]);
        int timeout = Integer.valueOf(s.split(" ")[2]);
        String userID = s.split(" ")[3];
        
        String bytes = "";
        bytes += String.format("%16s", Integer.toBinaryString(size)).replace(' ', '0');
        bytes += String.format("%16s", Integer.toBinaryString(payloadLength)).replace(' ', '0');
        bytes += String.format("%8s", Integer.toBinaryString(timeout)).replace(' ', '0');
        bytes += "1";
        System.out.println("To String: "+bytes);
        
        int i = Integer.parseInt("0101011", 2); // parse base 2	
        System.out.println("To number: "+i);
    }

}

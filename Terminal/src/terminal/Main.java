/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terminal;

import java.io.*;
import java.net.*;

/**
 *
 * @author Rasheed
 * 
 * Get HTTP request
 * Store page in memory
 * Client give input
 */
public class Main {
    
    
    public static void main(String[] args) throws MalformedURLException, ProtocolException, IOException {
        //Window window = new Window();
        Server server = new Server();
        server.GET("http://www.ni.com/product-documentation/12402/en/");
        System.out.println("Srgv");
        
        Client client = new Client();
        //client.getInput();
        //Example: 500 100 1 xyz
    }
    

}

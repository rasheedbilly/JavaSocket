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
// Java implementation of Server side 
// It contains two classes : Server and ClientHandler 
// Save file as Server.java 
import java.io.*;
import java.util.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// Server class 
public class Server {

    private byte[] page;

    public Server() {
        (new Thread() {
            @Override
            public void run() {
                try {
                    runServer();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    public void runServer() throws IOException {
        // server is listening on port 22122 
        ServerSocket ss = new ServerSocket(22122);
        
        // running infinite loop for getting 
        // client request 
        //DatagramPacket DpReceive = null;
        while (true) {
            Socket s = null;

            try {
                s = ss.accept();
                System.out.println("Server: A new client is connected : " + s);

                // obtaining input and out streams 
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                // create a new thread object 
                System.out.println("Server: Creating Client Handler thread");
                Thread t = new ClientHandler(s, dis, dos, page);

                // Invoking the start() method 
                t.start();

            } catch (Exception e) {
                s.close();
                e.printStackTrace();
            }
        }
    }

    /**
     * Simple HTTP GET Request
     *
     * @param URL
     * @throws MalformedURLException
     * @throws ProtocolException
     * @throws IOException
     */
    public void GET(String URL) throws MalformedURLException, ProtocolException, IOException {

        URL url = new URL(URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        Map<String, String> params = new HashMap<>();
        params.put("v", "dQw4w9WgXcQ");

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }

        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        connection.setDoOutput(true);
        try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
            writer.write(postDataBytes);
            writer.flush();
            writer.close();

            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                String line;
                content = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            //System.out.println(content.toString());
            //byteArray = content.toString().getBytes();
            page = content.toString().getBytes();
        } finally {
            connection.disconnect();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terminal;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rasheed
 */
public class Server {

    private static Socket socket;

    private byte[] byteArray;

    public Server() {
        (new Thread() {
            @Override
            public void run() {
                runServer();
            }
        }).start();
    }

    /**
     * Converts Byte Array to String
     *
     * @return
     */
    public String ByteArrayToString() {
        String s;
        try {
            s = new String(byteArray, "UTF-8");
            return s;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        //String str = new String(byteArray, "UTF-8");
        return null;
    }

    /**
     * Runs Server
     */
    public void runServer() {
        try {

            int port = 22122;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Sever: Server Started and listening to the port 22122");
            //this.serverArea.append("Server Started and listening to the port 22122");

            //Server is running always. This is done using this while(true) loop
            while (true) {
                //Reading the message from the client
                socket = serverSocket.accept();
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String number = br.readLine();
                System.out.println("Server: Message received from client is " + number);
                //serverArea.append("Message received from client is " + number);
                

                //Multiplying the number by 2 and forming the return message
                String returnMessage;
                try {
                    int numberInIntFormat = Integer.parseInt(number);
                    int returnValue = numberInIntFormat * 2;
                    returnMessage = String.valueOf(returnValue) + "\n";
                } catch (NumberFormatException e) {
                    //Input was not a number. Sending proper message back to client.
                    returnMessage = "Please send a proper number\n";
                }

                //Sending the response back to the client.
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(returnMessage);
                System.out.println("Server: Message sent to the client is " + returnMessage);
                //serverArea.append("Message sent to the client is " + returnMessage);
                bw.flush();
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
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
            System.out.println(content.toString());
            byteArray = content.toString().getBytes();
        } finally {
            connection.disconnect();
        }
    }
}

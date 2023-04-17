import java.io.*;
import java.net.*;
import java.util.logging.*;

public class TCPClient {
    private static final Logger LOGGER = Logger.getLogger(TCPClient.class.getName());
    public static void main(String[] args) {

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        String message = "";
        for (int i = 2; i < args.length; i++) {
            message += args[i] + " ";
        }
        message = message.trim();

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            LOGGER.log(Level.INFO, "Connected to {0}:{1}", new Object[] {host, port});

            if (!message.isEmpty()) {
                LOGGER.log(Level.INFO, "Sending: {0}", message);
                out.println(message);
            }

            String inLine;
            while ((inLine = in.readLine()) != null) {
                LOGGER.log(Level.INFO, "Receiving: {0}", inLine);
                System.out.println(inLine);
            }

        } catch (IOException err) {
            LOGGER.log(Level.SEVERE, "Error connecting to server", err);
        }
    }
}
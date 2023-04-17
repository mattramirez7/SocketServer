import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.Random;

public class SocketServer {

    static ExecutorService exec = null;
    static String[] quotes = {
        "\"To be or not to be, that is the question.\" - William Shakespeare, Hamlet (1603)",
        "\"May the Force be with you.\" - George Lucas, Star Wars (1977)",
        "\"Hello, my name is Inigo Montoya. You killed my father. Prepare to die.\" - Mandy Patinkin, The Princess Bride (1987)",
        "\"It's not the years, honey. It's the mileage.\" - Harrison Ford, Raiders of the Lost Ark (1981)",
        "\"There's no place like home.\" - Judy Garland, The Wizard of Oz (1939)",
        "\"As you wish.\" - Cary Elwes, The Princess Bride (1987)",
        "\"I'll be back.\" - Arnold Schwarzenegger, The Terminator (1984)",
        "\"I am the one who knocks!\" - Walter White, Breaking Bad",
        "\"I'm not a traitor to my class. I'm just an extreme example of what a working man can achieve.\" - Tommy Shelby, Peaky Blinders"
    };


    public static void handleTCPRequest() {
        Socket tcpSocket = null;
        try(ServerSocket server = new ServerSocket(17)) {
            while ((tcpSocket = server.accept()) != null) {
                System.out.println("Accepted TCP client request");
                try (BufferedReader in = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(tcpSocket.getOutputStream(), true)) {
                    
                        String inLine;
                        while ((inLine = in.readLine()) != null) {
                            Random rand = new Random();
                            String qotd = quotes[rand.nextInt(quotes.length)];
                            System.out.println(inLine);
                            out.println(qotd);
                        }
                        System.out.println();
                        in.close();
                        out.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public static void handleUDPRequest() {
        try {
            while(true) {
                byte[] buffer = new byte[256];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                DatagramSocket udpSocket = new DatagramSocket(17);
                
                udpSocket.receive(packet);
                System.out.println("Datagram Received.");

                Random rand = new Random();
                String qotd = quotes[rand.nextInt(quotes.length)];
                
                byte[] responseData = qotd.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, packet.getAddress(), packet.getPort());
                
                udpSocket.send(responsePacket);
                udpSocket.close();
            }
        } catch (Exception ex) { ex.printStackTrace(); }
    }
    
    public static void main(String... args) {
        exec = Executors.newFixedThreadPool(2);

        exec.submit(() -> handleTCPRequest());
        exec.submit(() -> handleUDPRequest());
    }
}

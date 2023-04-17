# SocketServer
TCP/UDP Socket Server that conforms to RFC 865 QOTD protocol for a UW Networks class

## Build Instructions
### Compile client and server source-code files
In the root directory, run the following commands:

1. `javac TCPClient.java`
2. `javac UDPClient.java`
3. `javac SocketServer.java

### Run the Server (port 17)
`java SocketServer`

### Connect the Client (TCP or UDP)

Start a TCP connection:
Run `nc localhost 17`

or

Start a UDP connection:
Run `nc -u localhost 17`

Once connected, type a message and press enter to send. 



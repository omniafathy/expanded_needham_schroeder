install: authman bob protocol tcpClient tcpObject tcpServer util

authman: AuthenticationManager.java
	javac AuthenticationManager.java

bob: Bob.java BobProtocol.java
	javac Bob.java
	javac BobProtocol.java

protocol: Protocol.java
	javac Protocol.java

tcpClient: TcpClient.java
	javac TcpClient.java

tcpObject: TcpObject.java
	javac TcpObject.java

tcpServer: TcpServer.java
	javac TcpServer.java

util: Util.java
	javac Util.java


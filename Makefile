install: authman alice bob kdc nsclient nskdc nsserver NSRunner protocol tcpClient tcpObject tcpServer util

authman: AuthenticationManager.java
	javac AuthenticationManager.java

alice: Alice.java
	javac Alice.java

bob: Bob.java
	javac Bob.java

kdc: Kdc.java
	javac Kdc.java

nsclient: NeedhamSchroederClientProtocol.java
	javac NeedhamSchroederClientProtocol.java

nskdc: NeedhamSchroederKdcProtocol.java
	javac NeedhamSchroederKdcProtocol.java

nsserver: NeedhamSchroederServerProtocol.java
	javac NeedhamSchroederServerProtocol.java

NSRunner: NSRunner.java
	javac NSRunner.java

protocol: Protocol.java
	javac Protocol.java

run: install
	java NSRunner

runAlice: alice
	java Alice

runAuth: authman
	java AuthenticationManager

runBob: bob
	java Bob

runKdc: kdc
	java Kdc

tcpClient: TcpClient.java
	javac TcpClient.java

tcpObject: TcpObject.java
	javac TcpObject.java

tcpServer: TcpServer.java
	javac TcpServer.java

util: Util.java
	javac Util.java


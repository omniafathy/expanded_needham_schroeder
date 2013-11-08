TOP := $(shell pwd)
TCP_DIR := $(TOP)/tcp
CLIENT_DIR := $(TCP_DIR)/clients
SERVER_DIR := $(TCP_DIR)/servers
PROTOCOL_DIR := $(TOP)/protocols
AUTHENTICATION_DIR := $(TOP)/authentication
RUNNER_DIR := $(TOP)/runners
UTIL_DIR := $(TOP)/util

install: alice authenticationManager protocol tcpClient tcpObject tcpServer util

alice: $(CLIENT_DIR)/Alice.java
	javac $(CLIENT_DIR)/Alice.java

bob: Bob.java
	javac Bob.java

authenticationManager: $(AUTHENTICATION_DIR)/AuthenticationManager.java
	javac $(AUTHENTICATION_DIR)/AuthenticationManager.java

protocol: $(PROTOCOL_DIR)/Protocol.java
	javac protocols/Protocol.java

tcpClient: $(CLIENT_DIR)/TcpClient.java
	javac $(CLIENT_DIR)/TcpClient.java

tcpObject: $(TCP_DIR)/TcpObject.java
	javac $(TCP_DIR)/TcpObject.java

tcpServer: $(SERVER_DIR)/TcpServer.java
	javac $(SERVER_DIR)/TcpServer.java

util: $(UTIL_DIR)/Util.java
	javac $(UTIL_DIR)/Util.java























#install: authman alice bob ENSclient ENSkdc ENSserver kdc nsclient nskdc nsserver NSRunner protocol tcpClient tcpObject tcpServer util reflectionProtocol trudy ReflectionRunner

#ENSclient: ExpandedNeedhamSchroederClientProtocol.java
#	javac ExpandedNeedhamSchroederKdcProtocol.java
#
#ENSkdc: ExpandedNeedhamSchroederKdcProtocol.java
#	javac ExpandedNeedhamSchroederKdcProtocol.java
#
#ENSserver: ExpandedNeedhamSchroederServerProtocol.java
#	javac ExpandedNeedhamSchroederServerProtocol.java
#
#kdc: Kdc.java
#	javac Kdc.java
#
#nsclient: NeedhamSchroederClientProtocol.java
#	javac NeedhamSchroederClientProtocol.java
#
#nskdc: NeedhamSchroederKdcProtocol.java
#	javac NeedhamSchroederKdcProtocol.java
#
#nsserver: NeedhamSchroederServerProtocol.java
#	javac NeedhamSchroederServerProtocol.java
#
#NSRunner: NSRunner.java
#	javac NSRunner.java
#
#ReflectionRunner: ReflectionRunner.java
#	javac ReflectionRunner.java
#
#protocol: Protocol.java
#	javac Protocol.java
#
#reflectionProtocol: NeedhamSchroederReflectionProtocol.java
#	javac NeedhamSchroederReflectionProtocol.java
#
#run: install
#	java NSRunner
#
#runAlice: alice
#	java Alice
#
#runAuth: authman
#	java AuthenticationManager
#
#runBob: bob
#	java Bob
#
#runKdc: kdc
#	java Kdc
#
#runRef: trudy ReflectionRunner reflectionProtocol
#	java ReflectionRunner
#
#tcpClient: TcpClient.java
#	javac TcpClient.java
#
#tcpObject: TcpObject.java
#	javac TcpObject.java
#
#tcpServer: TcpServer.java
#	javac TcpServer.java
#
#trudy: Trudy.java
#	javac Trudy.java
#
#util: Util.java
#	javac Util.java
#

# Chat_App_TP_RTS_2022

Authors : `Ulysse CAMBIER` & `Thibaut LEFEBVRE`
Date : 2022

*This github is the result of a half-yearly work for the 3rd year RTS section of a master's degree in engineering at the ENSEA.*


<b>This project has dependencies : </b>
<ul>
  <li>org.ini4j.Ini (https://ini4j.sourceforge.net/)</li>
  <li>JavaFX included in latest JDK</li>
  <li>org.json.JSONOBJECT (https://github.com/stleary/JSON-java)</li>
</ul>
<b>You Will find those dependencies .jar in `/libs/` of this github.</b>
<b>If imported on IntellijiIdea, thoses libraries are pre-configured.</b>

<b><i>The whole JavaDoc 📖 of this project is accessible <a href="https://ulyssecambier31.github.io/Chat_App_TP_RTS_2022/">here</a></i></b>

## 1. Chat Application

###  1.1 GUI Application

This is the main result of our work. We have made a working UDP GUI Application exchanging on LAN messages.
<p align="center"><img align="center" height="400" src="/img/chatapp.png"></p>

#### 1.1.1  How to use the GUI Application

To use the GUI Application, you need to edit a [config.ini](config.ini) file (there's an exemple in the root of this github) so that the port parameter and the ip parameter corresponds to the port and ip of the server. After having launched the ChatServer on the same LAN as the clients, you can launch ChatApplication to launch the GUI Application.
<ul>
<li><code>ChatApplication</code><p>Without arguments, the config.ini file will be opened from the current directory.</p></li>
<li><code>ChatApplication "path\to\config.ini"</code><p>In this cas the config.ini file will be opened from the provided path.</p></li>
</ul>

The ChatServer always sets up the 8080 port.

When launched, the application will open a Joining windows in which you can enter your username. Then when you click on Join, the chat conversation window will open and you will be able to send messages on the conversation. Your messages will be hilighted in blue and others messages will be in gray.

[Still in the gray ? Here a step-by-step guide](#41-step-by-step-guide-to-start-the-gui-app)

#### 1.1.2 The Applicative Protocol

Packet exchanged between [Clients](/src/rts/ensea/fr/ChatApplication.java) and the [Server](/src/rts/ensea/fr/ChatServer.java) contain serialized encapsuled data.

To communicate with the server, and vice-versa, the client sends serialized [Payload](/src/rts/ensea/fr/Payload.java) on the datagram socket. The [Payload](/src/rts/ensea/fr/Payload.java) is an encapsulation of the JSON serialization as a part of our own Applicative Protocol.

A payload is constructed in the following way:
<code>Operation flag | Operation arguments | User</code>

This way, the user can ask the server to make differents action for him.
There's 3 Operation supported :
1. *Receive :* In usual case, when the [Server](/src/rts/ensea/fr/ChatServer.java) sends a [Message](/src/rts/ensea/fr/Message.java) to a [Client](/src/rts/ensea/fr/ChatApplication.java), the operation flag is the following <code>""</code>. In that case, the [Payload](/src/rts/ensea/fr/Payload.java) is intended to be of the form <code>"" | Serialized Message | User</code>. The [Message](/src/rts/ensea/fr/Message.java) is then just printed on the [Client](/src/rts/ensea/fr/ChatApplication.java) screen
2. *Send :* In usual case, when a [Client](/src/rts/ensea/fr/ChatApplication.java) sends a [Message](/src/rts/ensea/fr/Message.java) to the [Server](/src/rts/ensea/fr/ChatServer.java), the operation flag is the following <code>"send"</code>. In that case, the [Payload](/src/rts/ensea/fr/Payload.java) is intended to be of the form <code>"send" | Serialized Message | User</code>. The [Message](/src/rts/ensea/fr/Message.java) is added to the chat [Conversation](/src/rts/ensea/fr/Conversation.java). The [Message](/src/rts/ensea/fr/Message.java) is then broadcasted by the [Server](/src/rts/ensea/fr/ChatServer.java) to all [Users](/src/rts/ensea/fr/User.java).  
3. *Connect :* When a [Client](/src/rts/ensea/fr/ChatApplication.java) sends a connection [Payload](/src/rts/ensea/fr/Payload.java) to the [Server](/src/rts/ensea/fr/ChatServer.java), the operation flag is the following <code>"connect"</code>. In that case, the [Payload](/src/rts/ensea/fr/Payload.java) is intended to be of the form <code>"connect" | Formated time of connection request | User</code>. A welcomming [Message](/src/rts/ensea/fr/Message.java) is added to the chat [Conversation](/src/rts/ensea/fr/Conversation.java) by the [Server](/src/rts/ensea/fr/ChatServer.java). The [Message](/src/rts/ensea/fr/Message.java) is then broadcasted by the [Server](/src/rts/ensea/fr/ChatServer.java) to all [Users](/src/rts/ensea/fr/User.java). The [Users](/src/rts/ensea/fr/User.java) is also registered in the [Conversation](/src/rts/ensea/fr/Conversation.java).


#### 1.1.3 Code structure

## 2.UDP Server/Client

## 3. TCP Server/Client

## 4. Appendix

### 4.1 Step-by-step guide to start the GUI App

This is a step-by-step guide on how to use the GUI Application.

1. To begin with, we advise you to check on you server computer the IPv4 address it has on the LAN network on which you would like to run the application.
You can do this by using command <code>ipconfig</code> on Windows or <code>ifconfig</code> on Linux.
<p align="center"><img align="center" height="400" src="/img/ipconfig.jpg"></p>

2. Then you have to create or modify the [config.ini](config.ini) on your client computer so that the parameter IPv4Address matches the IPv4Address of the server. You also need this computer to be connected in the same LAN network.
<p align="center"><img align="center" height="400" src="/img/configini.jpg"></p>

3. Now you can launch the [ChatServer](/src/rts/ensea/fr/ChatServer.java) class on your server computer. There's no need to provide arguments for this program.
<p align="center"><img align="center" height="50" src="/img/Run Chat server.jpg"></p>

4. Then you can launch the [ChatApplication](/src/rts/ensea/fr/ChatApplication.java) class on your client computer. On Intelliji, there's no need to provide arguments for this program. However, in command line, you may have to provide a path to the config.ini file as a paramter of the program.
<p align="center"><img align="center" height="50" src="/img/Run Gui App.jpg"></p>

5. You can start as many [Client](/src/rts/ensea/fr/ChatApplication.java) as you like.

6. Each [Client](/src/rts/ensea/fr/ChatApplication.java) will prompt you to enter a username before connecting to the LAN [Conversation](/src/rts/ensea/fr/Conversation.java).
<p align="center"><img align="center" height="400" src="/img/join window.jpg"></p>

7. You will then be able to see the current state of the conversation and send message to all the other users.
<p align="center"><img align="center" height="400" src="/img/Conversation.jpg"></p>

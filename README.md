# ThreadedDataServer
A java tool that has two components, a server, and a client. The client is able to connect once the server process is running, which allows the client to send messages in a clean format to the server. The server can then process those commands and act uppon them. At the moment, this server is **VERY** insecure, and should only be used on low security systems with nothing significant connected to either end. This is because nearly nothing is cleaned from most of the commands. There are more than likely some arbitrary bash command execution vulnerabilities using the client input vector such as injecting more commands with a ;. Be safe :)
### Uses
This project hosts a java socket server on one computer, and any number of clients can connect and send inputs to the server. At the moment most of the parsed commands are related to the primary use of this project which is to have a rasperry pi servo turret.

### Collaboration
At the moment I am the only person who has added code to this project, but I would be absolutely ecstatic to have someone to nerd out and work on this with. :)

#### Future Goals
Later down the line I would love to have some sort of ASM solution to have the project just search for classes in the classpath that implement some interface to allow any command to work mostly. This is a later stage goal though.

#### Credits

Takes Java Web Server (Implemented but unsure of if I will use it) [Here](https://github.com/yegor256/takes)
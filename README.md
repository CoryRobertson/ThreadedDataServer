# ThreadedDataServer
This java tool that has two components, a server, and a client. The client is able to connect once the server process is running, which allows the client to send messages in a clean format to the server. The server can then process those commands and act uppon them. At the moment, this server is **VERY** insecure, and should only be used on low security systems with nothing significant connected to either end. This is because nearly nothing is cleaned from most of the commands. There are more than likely some arbitrary bash command execution vulnerabilities using the client input vector such as injecting more commands with a ;. Be safe :)

The models that print to build the turret are in the models folder.
Images of the assembled turret are [here](https://imgur.com/a/UkkJbJY)

![Imgur](http://i.imgur.com/G4DfBN3h.gif)

### Uses
This project hosts a java socket server on one computer, and any number of clients can connect and send inputs to the server. At the moment most of the parsed commands are related to the primary use of this project which is to have a rasperry pi servo turret.
There is also a web server host built in that allows you to have a much faster response time to the mouse.

### Collaboration
At the moment I am the only person who has added code to this project, but I would be absolutely ecstatic to have someone to nerd out and work on this with. :)

#### Future Goals
Later down the line I would love to have some sort of ASM solution to have the project just search for classes in the classpath that implement some interface to allow any command to work mostly. This is a later stage goal though.

#### Things needed
-Raspberry Pi (probably something better or equal to a 3?)

-Adafruit 16-channel pwm servo HAT [here](https://www.adafruit.com/product/2327)

-Raspberry Pi Camera Module 2 [here](https://www.raspberrypi.com/products/camera-module-v2/)

-2 Servos

#### Credits

-Solidworks design: elkayamben@gmail.com

-Electrical design: elkayamtom@gmail.com

-Pi Camera WebPage: [https://picamera.readthedocs.io/en/latest/recipes2.html](https://picamera.readthedocs.io/en/latest/recipes2.html)

-Takes java web dev framework: [Takes](https://github.com/yegor256/takes)

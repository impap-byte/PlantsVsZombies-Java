
Plants vs Zombies - Java Edition
Mustafa Göktürk Binay - TOBB ETÜ
Video Linki: https://youtu.be/2c_dMtyTWnk?si=Jn51soZRSYKNKlTB

Prerequisites
Java Development Kit (JDK) 8: This project is built on Java 1.8.

Project Structure
src/: Java source files organized by package (com.pvz.model, com.pvz.view, etc.)

res/: Game assets including textures.

bin/: Output directory for compiled bytecode and savefiles.

Build & Run Instructions
To compile and launch the game on linux, open a terminal in the project root directory and run the following commands:

1. Compilation
This command finds all source files across all subdirectories and outputs them to the bin folder. Run it inside the src directory.
cd src
find . -name "*.java" | xargs javac -d ../bin


2. Execution
Navigate to the bin folder.
cd bin
Run this: java com.pvz.Main


# Plants vs Zombies - Java Edition
[Demo Video](https://youtu.be/2c_dMtyTWnk?si=Jn51soZRSYKNKlTB)

## Overview
This project is a Java-based clone of the classic game *Plants vs. Zombies*, developed as part of the [BIL211 - Computer Programming II](https://abys.etu.edu.tr/public/lesson.jsp?program=5&lang=tr&lesson=B%C4%B0L211) coursework. The game demonstrates core Object-Oriented Programming (OOP) principles, multithreading, state management, and file I/O in Java. 

## Objective
This project is a simplified version of Plants vs. Zombies developed using Java and Java Swing. The primary goal is to defend a 5 x 9 grid from waves of zombies approaching from the right side of the screen. You lose if any zombie reaches the leftmost column, and you win by defeating all zombie waves.

## Core Gameplay Mechanics
### Grid System: 
The game area consists of 5 rows and 9 columns. Each cell can hold only one plant but may contain multiple zombies, suns, or projectiles.  
### Plant Types:
- Peashooter: Shoots horizontal projectiles at zombies.
- Sunflower: Periodically generates sun, which is the game's primary resource for buying plants.
- Wall-Nut: High-health plant used to block zombies.
- SnowPea: Shoots projectiles that temporarily slow down zombie movement.
- CherryBomb: An explosive plant that kills zombies in its cell shortly after being placed.
   
### Zombie Variations: 
There are four zombie types: BasicZombie (low health, slow speed), FastZombie (medium health, medium speed), RunZombie (medium health, very high speed), and TankZombie (high health, very high speed). Each zombie moves continously(fully animated) until it meets a plant. It then attacks the plant.
### Production & Wave Systems 
- Normal Production: Random zombies (Basic and Fast) spawn at low frequencies during standard play.
- Wave System: After every 8 zombies produced normally, a "Wave" begins. There is a separate thread specifically for managing these waves, which increases difficulty by spawning zombies more frequently and with higher health.
### Technical & UI Features
- Resource Management: You start with a set amount of sun and must manually click on sun produced by Sunflowers to increase your balance.
- State Management: The game includes a Start Menu and the ability to Pause/Resume.
- Save/Load System: When paused, the game state is saved to a  binary file using a SaveData object that allows the player to resume exactly where they left off, including even the positions of active projectiles.
- OOP Design: When starting this project, learning how to design a sophisticated OOP-based project architecture was my goal. Therefore, I used inheritance (e.g., GameObject, Plant, VulnerablePlant, InstantPlant), interfaces (e.g., Producer, Updatable), and polymorphism to manage different plant, zombie and other game object behaviors.

## 🛠 Prerequisites
* **Java Development Kit (JDK) 8:** This project is built and compiled on Java 1.8.

## Project Structure
PlantsVsZombies/
src/        Java source files organized by packages
res/        Game assets (character sprites and textures)
bin/        Output directory for compiled bytecode (.class files) and save data

Build & Run Instructions
To compile and launch the game on linux, open a terminal in the project root directory and run the following commands:

1. Compilation
Create the bin directory if it doesn't exist
`mkdir -p bin`
This command finds all source files across all subdirectories and outputs them to the bin folder. Run it inside the src directory.
`cd src`
`find . -name "*.java" | xargs javac -d ../bin`


3. Execution
Navigate to the bin folder.
`cd bin`
Run this:
`java com.pvz.Main`

*Mustafa Göktürk Binay - TOBB ETÜ*

## Gallery

**A Wave**  
<img width="1386" src="https://github.com/user-attachments/assets/26fe2546-35fd-4e14-97b7-5a4d18b44526" />

---

**The Lose Screen**  
<img width="1386" src="https://github.com/user-attachments/assets/88ac5b4e-94e6-4eee-8c59-7306577f29aa" />

---

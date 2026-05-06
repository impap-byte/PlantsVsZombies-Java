package com.pvz.controller;

import java.io.Serializable;
import com.pvz.model.*;
import com.pvz.view.*;

public class WaveManager implements Runnable, Serializable {
    
    private int zombieCount;
    private int spawnFrequency;
    private int healthBoost;
    private transient GamePanel gp;
    private int iterator;
    // I saved the iterator so that the game knows where to continue the for loop(after loading from a savefile)
    
    public WaveManager(int zombieCount, int spawnFrequency, int healthBoost, GamePanel gp) {
        this.gp = gp;
        this.zombieCount = zombieCount;
        this.spawnFrequency = spawnFrequency;
        this.healthBoost = healthBoost;
    }
    
    public void setGamePanel(GamePanel gp) {
        this.gp = gp;
    }

    private void spawnWaveZombies() {
        int randomType = (int)(Math.random() * 100);
        int randomRow = (int)(Math.random() * GamePanel.ROWS);
        int y = GamePanel.START_Y + (randomRow * GamePanel.TILE_HEIGHT);
        int x = GamePanel.START_X + (GamePanel.COLS * GamePanel.TILE_WIDTH) - 100; 

        Zombie z;
        if (randomType <= 40) {
            z = new BasicZombie(x, y);
        } else if (randomType <= 60) {
            z = new RunZombie(x, y);
        } else if (randomType <= 70) {
            z = new TankZombie(x, y);
        } else {
            z = new FastZombie(x, y);
        }
        
        z.applyHealthBoost(healthBoost);
        gp.addZombie(z);
    }

    @Override
    public void run() {
        //System.out.println("Wave Thread Starting...");
        
        for (; iterator < zombieCount; iterator++) {
            
            if (Thread.currentThread().isInterrupted()) {
                //System.out.println("Wave thread killed.");
                return; 
            }

            int timeWaited = 0;
            while (timeWaited < spawnFrequency) {
                // Checks for interruption
                if (Thread.currentThread().isInterrupted()) return;

                if (gp.isPaused()) {
                    try { 
                        Thread.sleep(100); 
                        continue; 
                    } catch (InterruptedException e) { 
                        return;
                    }
                }
                
                try {
                    Thread.sleep(100);
                    timeWaited += 100;
                } catch (InterruptedException e) { 
                    return;
                }
            }

            spawnWaveZombies(); 
           //System.err.println("Spawned a new mutated one!");
        }

        //System.out.println("The wave's over");
        iterator = 0;
        gp.onWaveComplete();
    }
}
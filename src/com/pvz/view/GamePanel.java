package com.pvz.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.pvz.controller.*;
import com.pvz.model.*;
import com.pvz.util.*;

public class GamePanel extends JPanel implements Runnable {
    
    public static final int TILE_WIDTH = 132;
    public static final int TILE_HEIGHT = 121;
    public static final int START_X = 530;
    public static final int START_Y = 250;
    public static final int ROWS = 5;
    public static final int COLS = 9;
    public static final int CARD_COUNT = 5;
    private final int SPAWN_COOLDOWN = 60 * 10;
    private int mouseX;
    private int mouseY;
    protected boolean shovelVisible;
    protected boolean isPaused;
    private JButton continueBtn, removeButton, restartBtn;
    GameStateManager gameStateManager;
    private WaveManager waveManager;
    private Thread activeWaveThread;
    protected Tile[][] tiles;
    private List<Projectile> activeProjectiles = new CopyOnWriteArrayList<>();
    private List<Sun> activeSuns = new CopyOnWriteArrayList<>();
    private List<Zombie> activeZombies = new CopyOnWriteArrayList<>();
    
    protected Tile hoveredTile;
    private boolean isHovering;
    private Plant selectedPlant;
    private CardHolderPanel shelf;
    
    private Thread gameThread;
    private final int FPS = 60;
    private boolean isRunning = false;
    BasicZombie zombie;
    private int normalSpawnTimer = 0;
    private int normalZombieCount = 0;
    private int currentWave = 0;
    private boolean isWaveActive = false;
    
    {
        tiles = new Tile[ROWS][COLS];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Tile(i, j);
            }
        }
    }

    public GamePanel(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        this.setLayout(null);
        shelf = new CardHolderPanel(gameStateManager, this);
        shelf.setBounds(170, 10, shelf.cards.size() * PlantCard.CARD_WIDTH, 150);
        this.add(shelf);
        
        int shelfEndX = 170 + (shelf.cards.size() * PlantCard.CARD_WIDTH);
        Image scaledShovel = ResourceManager.shovel.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        removeButton = new JButton(new ImageIcon(scaledShovel));
        removeButton.setBounds(shelfEndX, 10, 100, 100);
        removeButton.setContentAreaFilled(false); 
        removeButton.setBorderPainted(false);     
        removeButton.setFocusPainted(false);       
        removeButton.setFocusable(false);
        removeButton.setVisible(true);
        removeButton.addActionListener(e -> {
            if (gameStateManager.getGameState() == GameState.RUN) {
                shovelVisible = true;
                shelf.selectedCardType = null;
                if (shelf.selectedCard != null) {
                    shelf.selectedCard.changeSelectedStatus(false);
                }
            }
        });
        this.add(removeButton);

        Image scaledContinueButton = ResourceManager.continueButtonTexture.getScaledInstance(300, 80, Image.SCALE_SMOOTH);
        continueBtn = new JButton(new ImageIcon(scaledContinueButton));
        continueBtn.setContentAreaFilled(false); 
        continueBtn.setBorderPainted(false);     
        continueBtn.setFocusPainted(false);       
        continueBtn.setFocusable(false);
        continueBtn.setBounds(830, 670, 300, 80);
        continueBtn.addActionListener(e -> {
            isPaused = false;
            continueBtn.setVisible(false);
            restartBtn.setVisible(false);
            gameStateManager.changeGameState(GameState.RUN, this);
        });
        continueBtn.setVisible(false);
        this.add(continueBtn);

        restartBtn = new JButton("RESTART");
        restartBtn.setFont(new Font("Arial", Font.BOLD, 30));
        restartBtn.setForeground(Color.WHITE);
        restartBtn.setBackground(new Color(139, 69, 19)); 
        restartBtn.setBounds(830, 760, 300, 60);
        restartBtn.setFocusable(false);
        restartBtn.setVisible(false);
        restartBtn.addActionListener(e -> restartGame());
        this.add(restartBtn);
        
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (!isPaused) {
                        gameStateManager.changeGameState(GameState.PAUSED, GamePanel.this);
                        continueBtn.setVisible(true);
                        restartBtn.setVisible(true); 
                    }
                }
            }
        });

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                int col = (mouseX - START_X) / TILE_WIDTH;
                int row = (mouseY - START_Y) / TILE_HEIGHT;
                if (row >= 0 && row <= (ROWS - 1) && col >= 0 && col <= (COLS - 1)) {
                    hoveredTile = tiles[row][col];
                    isHovering = true;
                } else {
                    isHovering = false;
                }
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                if (isPaused || gameStateManager.getGameState() != GameState.RUN) return;
                requestFocusInWindow();
                int mouse_x = e.getX();
                int mouse_y = e.getY();
                for (int i = activeSuns.size() - 1; i >= 0; i--) {
                    Sun s = activeSuns.get(i);
                    if (s.isClicked(mouse_x, mouse_y)) {
                        activeSuns.remove(i);
                        gameStateManager.earnSunPoints(s);
                        return;
                    }
                }
                if (SwingUtilities.isRightMouseButton(e)) {
                    if (shelf.selectedCard != null) shelf.selectedCard.changeSelectedStatus(false);
                    shovelVisible = false;
                }
                if (hoveredTile.plant == null && isHovering) {
                    if (shelf.getPlantCardType() == PlantType.PEASHOOTER && gameStateManager.purchasePlant(PlantType.PEASHOOTER.getCost())) {
                        hoveredTile.plant = new PeaShooter(hoveredTile.x, hoveredTile.y, activeProjectiles);
                    } else if (shelf.getPlantCardType() == PlantType.SNOW_PEA && gameStateManager.purchasePlant(PlantType.SNOW_PEA.getCost())) {
                        hoveredTile.plant = new SnowPea(hoveredTile.x, hoveredTile.y, activeProjectiles);
                    } else if (shelf.getPlantCardType() == PlantType.CHERRY_BOMB && gameStateManager.purchasePlant(PlantType.CHERRY_BOMB.getCost())) {
                        hoveredTile.plant = new CherryBomb(hoveredTile.x, hoveredTile.y);
                    } else if (shelf.getPlantCardType() == PlantType.SUNFLOWER && gameStateManager.purchasePlant(PlantType.SUNFLOWER.getCost())) {
                        hoveredTile.plant = new Sunflower(hoveredTile.x, hoveredTile.y, activeSuns);
                    } else if (shelf.getPlantCardType() == PlantType.WALLNUT && gameStateManager.purchasePlant(PlantType.WALLNUT.getCost())) {
                        hoveredTile.plant = new Wallnut(hoveredTile.x, hoveredTile.y);
                    }
                } else if (shovelVisible && hoveredTile.plant != null) {
                    hoveredTile.plant = null;
                }
            }
        };
        this.setDoubleBuffered(true);
        this.addMouseMotionListener(mouseAdapter);
        this.addMouseListener(mouseAdapter);
    }

    private void restartGame() {
        // Killing the old thread
        if (activeWaveThread != null && activeWaveThread.isAlive()) {
            activeWaveThread.interrupt();
        }

        activeZombies.clear();
        activeProjectiles.clear();
        activeSuns.clear();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                tiles[i][j].plant = null;
            }
        }
        currentWave = 0;
        normalZombieCount = 0;
        normalSpawnTimer = 0;
        isWaveActive = false;
        isPaused = false;
        waveManager = null;
        gameStateManager.setSunPoints(400); 
        gameStateManager.changeGameState(GameState.RUN, this);
        restartBtn.setVisible(false);
        continueBtn.setVisible(false);
        repaint();
    }
    
    private void spawnZombies() {
        int randomType = (int)(Math.random() * 2);
        int randomRow = (int)(Math.random() * ROWS);
        int y = START_Y + (randomRow * TILE_HEIGHT);
        int x = START_X + (COLS * TILE_WIDTH) - 100; 
        if (randomType == 0) activeZombies.add(new BasicZombie(x, y));
        if (randomType == 1) activeZombies.add(new FastZombie(x, y));
    }
    
    private void updateGame() {
        shelf.updateAffordability();
        if (currentWave == 3 && !isWaveActive && activeZombies.isEmpty()) {
            gameStateManager.changeGameState(GameState.VICTORY, this);
        }
        if (showWaveAlert) {
            waveAlertTimer--;
            if (waveAlertTimer <= 0) showWaveAlert = false;
        }
        
        for (Projectile p : activeProjectiles) {
            p.update();
            if (p.getX() > START_X + TILE_WIDTH * COLS + 150) activeProjectiles.remove(p);
        }
        
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (tiles[i][j].plant instanceof ShooterPlant) {
                    ((ShooterPlant) tiles[i][j].plant).setActive(false);
                }
            }
        }
        for (Zombie z : activeZombies) {
            if (z.getX() < START_X - 140) {
                gameStateManager.changeGameState(GameState.DEFEAT, this);
                break;
            }
            for (Projectile p : activeProjectiles) {
                if (Math.abs(p.getY() - z.getY()) < 30 && p.getX() >= z.getX() && p.getX() <= z.getX() + 60) {
                    p.attack(z);
                    if (!z.isDead()) activeProjectiles.remove(p);
                    break;
                }
            }
            if (z.getHealth() <= 0 && !z.isDead()) {
                z.setDead(true);
                z.setEating(false);
            }
            if (z.isDead()) {
                z.updateDeath();
                if (z.getDeathTimer() <= 0) activeZombies.remove(z);
                continue;
            }
            int gridCol = (z.getX() - START_X + 60) / TILE_WIDTH;
            int gridRow = (z.getY() - START_Y + 60) / TILE_HEIGHT;
            if (gridRow >= 0 && gridRow < ROWS) {
                for (int i = 0; i <= gridCol && i < COLS; i++) {
                    Plant p = tiles[gridRow][i].plant;
                    if (p instanceof ShooterPlant) ((ShooterPlant) p).setActive(true);
                }
            }
            if (z.currentRow + 1 <= ROWS && z.currentCol + 1 <= COLS && z.currentRow >= 0 && z.currentCol >= 0) {
                Plant p = tiles[z.currentRow][z.currentCol].plant;
                if (p instanceof InstantPlant && ((InstantPlant) p).isDead()) {
                    z.setDead(true);
                    continue;
                }
                if (p instanceof VulnerablePlant) {
                    z.setEating(true);
                    z.attack((VulnerablePlant) p); 
                } else z.setEating(false);
            } else z.setEating(false);
        }
        
        if (normalZombieCount < 8 && !isWaveActive) {
            normalSpawnTimer++;
            if (normalSpawnTimer >= SPAWN_COOLDOWN) {
                spawnZombies();
                normalZombieCount++;
                normalSpawnTimer = 0;       
            }
        } else if(!isWaveActive) startWaveThread();
        for (Zombie z : activeZombies) if (z instanceof Updatable) ((Updatable) z).update();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Plant p = tiles[i][j].plant;
                if (p instanceof VulnerablePlant && ((VulnerablePlant) p).getHealth() <= 0) tiles[i][j].plant = null;
                else if (p instanceof InstantPlant && ((InstantPlant) p).isDead()) tiles[i][j].plant = null;
                else if (p instanceof Updatable) {
                    if (p instanceof ShooterPlant) {
                        if (((ShooterPlant) p).isActive()) ((Updatable) p).update();
                    } else ((Updatable) p).update();
                }
            }
        }
    }
    private boolean showWaveAlert = false;
    private int waveAlertTimer = 0;
    private final int ALERT_DURATION = 60 * 3;
    
    private void startWaveThread() {
        normalZombieCount = 0;
        currentWave++;
        isWaveActive = true;
        showWaveAlert = true;
        waveAlertTimer = ALERT_DURATION;
        int zombieCount = currentWave * 10 + 5;
        int healthBoost = currentWave * 50;
        int spawnFrequency = (SPAWN_COOLDOWN * 1000 / FPS) / (currentWave * 2);
        waveManager = new WaveManager(zombieCount, spawnFrequency, healthBoost, this);
        
        activeWaveThread = new Thread(waveManager);
        activeWaveThread.start();
    }
    
    public void onWaveComplete() {
        isWaveActive = false;
        normalZombieCount = 0;
        waveManager = null;
    }

    public void pauseGame() { isPaused = true; }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ResourceManager.gameBackground, 0, 0, getWidth(), getHeight(), null);
        g.drawImage(ResourceManager.sunCounterTexture, 10, 10, 150, 150, null);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString(String.valueOf(gameStateManager.getSunPoints()), 67, 150);
        
        if (isHovering && shelf.getPlantCardType() != null && hoveredTile.plant == null && !shovelVisible && gameStateManager.getGameState() == GameState.RUN) {
            g.drawImage(ResourceManager.tileSelectEffect, hoveredTile.x, hoveredTile.y, 50, 50, null);
        }
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j].plant != null) tiles[i][j].plant.draw(g);
            }
        }
        for (Sun s : activeSuns) s.draw(g);
        for (Zombie z : activeZombies) z.draw(g);
        for (Projectile p : activeProjectiles) p.draw(g);
        
        if (shovelVisible && !isPaused && gameStateManager.getGameState() == GameState.RUN) {
            g.drawImage(ResourceManager.shovelCursor, mouseX - 30, mouseY - 65, 100, 100, null);
        }
        if (showWaveAlert) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            String msg = "A HUGE WAVE OF ZOMBIES IS APPROACHING!";
            int x = (getWidth() - g.getFontMetrics().stringWidth(msg)) / 2;
            int y = getHeight() / 2;
            g.setColor(Color.BLACK);
            g.drawString(msg, x + 2, y + 2);
            g.setColor(Color.RED);
            g.drawString(msg, x, y);
        }
        if (gameStateManager.getGameState() == GameState.DEFEAT) {
            g.drawImage(ResourceManager.gameOverText, 650, 150, 650, 650, null);
            restartBtn.setVisible(true); 
            pauseGame();
        } else if (gameStateManager.getGameState() == GameState.VICTORY) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 60));
            String msg = "VICTORY";
            int x = (getWidth() - g.getFontMetrics().stringWidth(msg)) / 2;
            int y = getHeight() / 2;
            g.drawString(msg, x, y);
            restartBtn.setVisible(true);
            pauseGame();
        } else if (isPaused) {
            g.drawImage(ResourceManager.pauseMenu, 650, 150, 650, 650, null);
            restartBtn.setVisible(true);
        }
    }
    
    public static class Tile implements Serializable {
        private int x, y;
        private int row, col;
        private Plant plant;
        public Tile(int row, int col) {
            this.row = row;
            this.col = col;
            this.x = START_X + TILE_WIDTH * col;
            this.y = START_Y + TILE_HEIGHT * row;
        }
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS; 
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                if (!isPaused && gameStateManager.getGameState() != GameState.DEFEAT) updateGame();
                repaint();  
                delta--;    
            }
        }
    }
    
    public void startGameThread() {
        checkAndLoadSave();
        gameThread = new Thread(this);
        isRunning = true;
        gameThread.start();
    }
    public void addZombie(Zombie newZombie) { activeZombies.add(newZombie); }
    public boolean isPaused() { return isPaused; }
    public void saveGame() {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save.dat"))){
            SaveData save = new SaveData(gameStateManager.getSunPoints(), normalZombieCount, activeZombies, activeProjectiles, activeSuns, tiles, isWaveActive, currentWave, waveManager);
            out.writeObject(save);
        } catch(IOException e) { e.printStackTrace(); }
    }
    public void checkAndLoadSave() {
        File saveFile = new File("save.dat");
        if (saveFile.exists()) {
            int response = JOptionPane.showConfirmDialog(this, "A save file has been found.", "Continue?", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) loadGame();
        }
    }
    private void loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("save.dat"))) {
            SaveData save = (SaveData) in.readObject();
            this.normalZombieCount = save.zombieCount;
            gameStateManager.setSunPoints(save.sunPoints); 
            this.activeZombies = save.activeZombies;
            this.activeProjectiles = save.activeProjectiles;
            this.activeSuns = save.activeSuns;
            this.tiles = save.tiles;
            this.currentWave = save.currentWave;
            this.isWaveActive = save.isWaveActive;
            if (isWaveActive && save.waveManager != null) {
                this.waveManager = save.waveManager;
                save.waveManager.setGamePanel(this);
                activeWaveThread = new Thread(waveManager);
                activeWaveThread.start();
            }
            repaint();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
package com.pvz.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class ResourceManager {
    // Backgrounds & UI
    public static BufferedImage titleBackground;
    public static BufferedImage gameBackground;
    public static BufferedImage pauseMenu;
    public static BufferedImage tileSelectEffect;
    public static BufferedImage startButtonTexture;
    public static BufferedImage sunCounterTexture;
    public static BufferedImage cardHolderPanel;
    public static BufferedImage genericCardBack;
    public static BufferedImage shovel;
    public static BufferedImage shovelCursor;
    public static BufferedImage gameOverText;

    // Plants
    public static BufferedImage peaShooterSprite;
    public static BufferedImage snowPeaSprite;
    public static BufferedImage cherryBombSprite;
    public static BufferedImage sunflowerSprite;
    public static BufferedImage wallnutSprite;
    public static BufferedImage sun;
    public static BufferedImage pea;
    public static BufferedImage frozenPea;
    public static BufferedImage explosionEffect;
    public static BufferedImage continueButtonTexture;
    
    // Zombies (Manual Animation Frames)
    public static BufferedImage[] basicZombieWalk;
    public static BufferedImage[] runZombieWalk;
    public static BufferedImage[] tankZombieWalk;
    public static BufferedImage[] fastZombieWalk;
    
    public static BufferedImage fastZombieDead;
    public static BufferedImage basicZombieDead;
    public static BufferedImage runZombieDead;
    public static BufferedImage tankZombieDead;
    public static void loadResources() {
        try {
            // Standard UI & Backgrounds
            titleBackground = ImageIO.read(ResourceManager.class.getResource("/resources/MainPage.jpg"));
            gameBackground = ImageIO.read(ResourceManager.class.getResource("/resources/GameBackground.png"));
            tileSelectEffect = ImageIO.read(ResourceManager.class.getResource("/resources/TileSelect.png"));
            startButtonTexture = ImageIO.read(ResourceManager.class.getResource("/resources/StartButton.png"));
            sunCounterTexture = ImageIO.read(ResourceManager.class.getResource("/resources/SunCounter.png"));
            cardHolderPanel = ImageIO.read(ResourceManager.class.getResource("/resources/CardShelf.png"));
            genericCardBack = ImageIO.read(ResourceManager.class.getResource("/resources/GenericCardBack.png"));
            shovel = ImageIO.read(ResourceManager.class.getResource("/resources/Shovel.png"));
            pauseMenu = ImageIO.read(ResourceManager.class.getResource("/resources/menus/PauseMenu.png"));
            continueButtonTexture = ImageIO.read(ResourceManager.class.getResource("/resources/ContinueButton.png"));
            gameOverText = ImageIO.read(ResourceManager.class.getResource("/resources/GameOver.png"));
            // Plants
            peaShooterSprite = ImageIO.read(ResourceManager.class.getResource("/resources/PeaShooterSprite.png"));
            snowPeaSprite = ImageIO.read(ResourceManager.class.getResource("/resources/SnowPeaSprite.png"));
            cherryBombSprite = ImageIO.read(ResourceManager.class.getResource("/resources/CherryBomb.png"));
            sunflowerSprite = ImageIO.read(ResourceManager.class.getResource("/resources/SunflowerSprite.png"));
            wallnutSprite = ImageIO.read(ResourceManager.class.getResource("/resources/WallnutSprite.png"));
            sun = ImageIO.read(ResourceManager.class.getResource("/resources/Sun.png"));
            pea = ImageIO.read(ResourceManager.class.getResource("/resources/projectiles/ProjectilePea.png"));
            frozenPea = ImageIO.read(ResourceManager.class.getResource("/resources/projectiles/ProjectileSnowPea.png"));
            explosionEffect = ImageIO.read(ResourceManager.class.getResource("/resources/effects/Explosion.png"));
            shovelCursor = ImageIO.read(ResourceManager.class.getResource("/resources/ShovelCursor.png"));
            
            fastZombieDead = ImageIO.read(ResourceManager.class.getResource("/resources/zombies/fast_zombie/fast_dead.png"));
            basicZombieDead = ImageIO.read(ResourceManager.class.getResource("/resources/zombies/basic_zombie/basic_dead.png"));
           	runZombieDead = ImageIO.read(ResourceManager.class.getResource("/resources/zombies/run_zombie/run_dead.png"));
            tankZombieDead =ImageIO.read(ResourceManager.class.getResource("/resources/zombies/tank_zombie/tank_dead.png"));
            // Load Basic Zombie Walk Cycle
            basicZombieWalk = new BufferedImage[4];
            for (int i = 0; i < 4; i++) {
                // Paths like: /resources/zombies/basic_zombie/basic_walk1.png
                String path = "/resources/zombies/basic_zombie/basic_walk" + (i + 1) + ".png";
                basicZombieWalk[i] = ImageIO.read(ResourceManager.class.getResource(path));
            }
            
            runZombieWalk = new BufferedImage[4];
            for (int i = 0; i < 4; i++) {
                String path = "/resources/zombies/run_zombie/run_zombie_walk" + (i + 1) + ".png";
                runZombieWalk[i] = ImageIO.read(ResourceManager.class.getResource(path));
            }
            
            tankZombieWalk = new BufferedImage[7];
            for (int i = 0; i < 7; i++) {
                String path = "/resources/zombies/tank_zombie/tank_zombie_walk_" + (i + 1) + ".png";
                tankZombieWalk[i] = ImageIO.read(ResourceManager.class.getResource(path));
            }
            fastZombieWalk = new BufferedImage[7];
            for (int i = 0; i < 7; i++) {

                String path = "/resources/zombies/fast_zombie/fast_zombie_walk_" + (i + 1) + ".png";
                fastZombieWalk[i] = ImageIO.read(ResourceManager.class.getResource(path));
            }
            System.out.println("All resources, including zombie frames, loaded successfully.");

        } catch (IOException | NullPointerException e) {
            System.out.println("Error loading resources: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
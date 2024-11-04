package game;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // Screen settings
    final int originalTileSize = 16; // 16x16
    final int scale = 3;

    final int tileSize = originalTileSize * scale; //48x48
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; // 768
    final int screenHeight = tileSize * maxScreenRow; // 567

    // FPS
    int FPS = 60;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    // Set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = (double) 1000 / FPS; // 0.0166s
        double delta = 0;
        long lastTime = System.currentTimeMillis();
        long currentTime;
        long timer = 0;
        long drawCount = 0;

        while (gameThread != null) {

            currentTime = System.currentTimeMillis();

            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if(delta >= 1) {

                // 1 Update information such as character positions
                update();

                // Draw the screen with the updated information
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update() {

        if(keyHandler.upPressed) {
            playerY -= playerSpeed;
        }
        else if(keyHandler.downPressed) {
            playerY += playerSpeed;
        }
        else if(keyHandler.leftPressed) {
            playerX -= playerSpeed;
        }
        else if(keyHandler.rightPressed) {
            playerX += playerSpeed;
        }
    }
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.WHITE);
        g2d.fillRect(playerX, playerY, tileSize, tileSize);
        g2d.dispose();
    }
}

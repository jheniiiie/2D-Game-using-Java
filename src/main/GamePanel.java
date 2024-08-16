package main;

import javax.swing.JPanel;
import java.awt.*;
import java.security.Key;

public class GamePanel extends JPanel implements Runnable{

    //configurações de tela
    final int tamanhoTileOriginal = 16; // 16x16
    final int escala = 4;

    final int tamanhoTile = tamanhoTileOriginal * escala; // 64x64
    final int maxTelaColuna = 16;
    final int maxTelaLinha = 12;
    final int comprimTela = tamanhoTile * maxTelaColuna; // 1024px
    final int alturaTela = tamanhoTile * maxTelaLinha; // 768px

    int FPS = 60;

    KeyHandler keyll = new KeyHandler();
    Thread gameThread;

    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 5;

    public GamePanel() {

        this.setPreferredSize(new Dimension(comprimTela, alturaTela));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyll);
        this.setFocusable(true);
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();

    }

    public void run() {

        double drawInterval = 1000000000/FPS; // 0.01666 segundos
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread !=null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update() {

        if(keyll.upPressed == true) {
            playerY -= playerSpeed;
            playerY = playerY - playerSpeed;
        }
        else if(keyll.downPressed == true) {
            playerY += playerSpeed;
        }
        else if(keyll.lefPressed == true) {
            playerX -= playerSpeed;
        }
        else if(keyll.rightPressed == true) {
            playerX += playerSpeed;
        }
    }
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.white);

        g2.fillRect(playerX, playerY, tamanhoTile, tamanhoTile);
        g2.dispose();
    }
}

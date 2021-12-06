package snowcastle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class snow_game extends JFrame{
	// buffer �̹���
	Image bufferImage; 
	// �׷��� ȭ��
	Graphics screenGraphic;

	// �ε� ȭ��
	Image loadingScreen = new ImageIcon("src/image/load.gif").getImage();
	// ���� ȭ��
	Image mainScreen = new ImageIcon("src/image/start_back.png").getImage();
	// ���� ȭ��
	Image gameScreen = new ImageIcon("src/image/winter.png").getImage();

	
	// ���� ȭ�� ���� üũ ����
	boolean isMainScreen;
	boolean isLoadingScreen;
	boolean isGameScreen;


	// ���� ��ü
	public static Game game = new Game();
	public snow_game() {
		// â ����
	    setTitle("��*������� ĳ����_*��*"); 
	    // â ũ��
	    setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
	    // â ũ�� ���� ����
	    setResizable(false); 
	    // â ��� ���
	    setLocationRelativeTo(null);
	    // â ������ ���α׷� ����
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    // ȭ�鿡 â ���
	    setVisible(true); 
	    setLayout(null);

	    // �ʱ�ȭ �޼ҵ� ȣ��
	    init();
	}

	// �ʱ�ȭ �޼ҵ�
	void init() { 
		// ���� ���� ȭ�� ���
	    isMainScreen = true;
	    isGameScreen = false; 
	    isLoadingScreen = false; 
	    

	    // [enter] ������ �Ѿ
	    addKeyListener(new KeyListener()); 
	}

	// ���� �ε� ȭ�� 
	void gameStart() {
	    isMainScreen = false; 
	    isLoadingScreen = true;

	    // Ÿ�̸� ����
	    Timer loadingTimer = new Timer(); 
	    TimerTask loadingTask = new TimerTask() {
	        @Override
	        public void run() {
	        	
	            isLoadingScreen = false; 
	            isGameScreen = true; 
	            game.start();
	        }
	    };
	    loadingTimer.schedule(loadingTask, 7000); 

	}

	// ȭ�� ������..
	public void paint(Graphics g) {  
	    bufferImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
	    screenGraphic = bufferImage.getGraphics(); 
	    screenDraw(screenGraphic);
	    g.drawImage(bufferImage, 0, 0, null); 

	}
	
	// ��� ȭ�� ���
	public void screenDraw(Graphics g) { 
	    if (isMainScreen) { 
	        g.drawImage(mainScreen, 0, 0, null);
	    }
	    if (isLoadingScreen) {
	    	g.setColor(Color.white);
	    	g.getColor();
	    	g.drawImage(loadingScreen, 940, 540, null);
	    }
	    if (isGameScreen) {
	        g.drawImage(gameScreen, 0, 0, null);
	        game.gameDraw(g);
	    }
	    this.repaint();
	}
	class KeyListener extends KeyAdapter {
	    @Override
	    public void keyPressed(KeyEvent e) { 
	        switch(e.getKeyCode()) {
	        case KeyEvent.VK_W: 
	            game.setUp(true);
	            break;
	        case KeyEvent.VK_S:
	            game.setDown(true);
	            break;
	        case KeyEvent.VK_A: 
	            game.setLeft(true);
	            break;
	        case KeyEvent.VK_D: 
	            game.setRight(true);
	            break;
	        case KeyEvent.VK_SPACE:
	        	paint(screenGraphic);
	            game.setShooting(true);
	            break;
	        case KeyEvent.VK_ENTER: 
	            if (isMainScreen) {
	            	
	                gameStart();
	            }
	            break;
	        case KeyEvent.VK_ESCAPE: 
	        	
	            System.exit(0);
	            break;
	        }
	        
	    }

	    // Ű�� ������ ���� ���
	    public void keyReleased(KeyEvent e) { 
	    	
	    	// false
	        switch(e.getKeyCode()) {
	        case KeyEvent.VK_W:
	            game.setUp(false);
	            break;
	        case KeyEvent.VK_S:
	            game.setDown(false);
	            break;
	        case KeyEvent.VK_A:
	            game.setLeft(false);
	            break;
	        case KeyEvent.VK_D:
	            game.setRight(false);
	            break;
	        case KeyEvent.VK_SPACE:
	            game.setShooting(false);
	            break;
	        }
	    }
	}
}

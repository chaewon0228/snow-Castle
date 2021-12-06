package snowcastle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class snow_game extends JFrame{
	// buffer 이미지
	Image bufferImage; 
	// 그래픽 화면
	Graphics screenGraphic;

	// 로딩 화면
	Image loadingScreen = new ImageIcon("src/image/load.gif").getImage();
	// 시작 화면
	Image mainScreen = new ImageIcon("src/image/start_back.png").getImage();
	// 게임 화면
	Image gameScreen = new ImageIcon("src/image/winter.png").getImage();

	
	// 실행 화면 여부 체크 변수
	boolean isMainScreen;
	boolean isLoadingScreen;
	boolean isGameScreen;


	// 게임 객체
	public static Game game = new Game();
	public snow_game() {
		// 창 제목
	    setTitle("º*º스노우 캐슬º_*º*"); 
	    // 창 크기
	    setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
	    // 창 크기 조절 막기
	    setResizable(false); 
	    // 창 가운데 띄움
	    setLocationRelativeTo(null);
	    // 창 닫으면 프로그램 종료
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    // 화면에 창 출력
	    setVisible(true); 
	    setLayout(null);

	    // 초기화 메소드 호출
	    init();
	}

	// 초기화 메소드
	void init() { 
		// 게임 시작 화면 출력
	    isMainScreen = true;
	    isGameScreen = false; 
	    isLoadingScreen = false; 
	    

	    // [enter] 누르면 넘어감
	    addKeyListener(new KeyListener()); 
	}

	// 게임 로딩 화면 
	void gameStart() {
	    isMainScreen = false; 
	    isLoadingScreen = true;

	    // 타이머 구현
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

	// 화면 깜빡임..
	public void paint(Graphics g) {  
	    bufferImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
	    screenGraphic = bufferImage.getGraphics(); 
	    screenDraw(screenGraphic);
	    g.drawImage(bufferImage, 0, 0, null); 

	}
	
	// 배경 화면 출력
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

	    // 키를 누르지 않을 경우
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

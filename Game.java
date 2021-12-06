package snowcastle;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Game extends Thread{
	 
	// 현재 시간
	long pretime; 
	// 지연 시간 
	int delay = 20;
	// 이벤트 발생 주기 
	int cnt; 
	
	// 게임 점수
	int score;
	
	// 플레이어 이미지
	Image player = new ImageIcon("src/image/Ryan.gif").getImage();
	// 게임 오버 이미지
	Image over = new ImageIcon("src/image/gameOver.png").getImage();
	
	// 플레이어 위치
	int playerX;
	int playerY; 
	// 플레이어 이미지 너비
	int playerWidth = player.getWidth(null);
	// 플레이어 이미지 높이
	int playerHeight = player.getHeight(null);
	
	// 플레이어 목숨값
	int playerHp = 30; 

	// 방향키 눌렀을 때 이동할 거리
	int playerSpeed = 10; 
	// 방향
	boolean up, down, left, right;
	
	// 공격
	boolean shooting;
	// 게임 실패 확인
	boolean isOver;

	
	// 플레이어 공격  ArrayList
	ArrayList<PlayerAttack> playerAttackList = new ArrayList<PlayerAttack>();
	// 콘  ArrayList
	ArrayList<Conn> connList = new ArrayList<Conn>();
	// 콘 공격 ArrayList
	ArrayList<ConnAttack> connAttackList = new ArrayList<ConnAttack>();

	
	PlayerAttack playerAttack;
	Conn conn;
	ConnAttack connAttack;

	@Override
	public void run() {

		// thread 시작하면 같이 실행
	    reset(); 
	    
	    while(true) {
	    	// 게임 오버 체크
	        while (!isOver) {
	        pretime = System.currentTimeMillis();
	        
	        // 현재 시간 - (cnt가 증가한 시간) < delay  =>  그 차이만큼 Thread에 delay
	        if (System.currentTimeMillis() - pretime < delay) { 
	            try {
	                Thread.sleep(delay - System.currentTimeMillis() + pretime);
	                keyProcess();
	                playerAttackOk();
	                connAppear();
	                connMove();
	                connAttackkkk();
	                cnt++;
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	        try {
	            Thread.sleep(100);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}

	public void reset() {
	    isOver = false;
	    //점수 초기화
	    score = 0;
	    //이벤트 초기화
	    cnt = 0;  
	    
	    //처음 플레이어 위치
	    playerX = (Main.SCREEN_WIDTH - playerWidth) / 2;
	    playerY = 750;

	    // 리스트 초기화
	    playerAttackList.clear();
	    connList.clear();
	    connAttackList.clear();
	}
	void keyProcess() { 
		// 화면 밖으로 나가지 않도록 범위 지정
	    if (up && playerY - playerSpeed > 0) playerY -= playerSpeed; 
	    if (down && playerY + playerHeight + playerSpeed < Main.SCREEN_HEIGHT) playerY += playerSpeed;
	    if (left && playerX - playerSpeed >0) playerX -= playerSpeed;
	    if (right && playerX + playerWidth + playerSpeed < Main.SCREEN_WIDTH) playerX += playerSpeed;
	    if (shooting && cnt % 15==0) {
	        playerAttack = new PlayerAttack(playerX + 222, playerY +25);
	        playerAttackList.add(playerAttack);
	    }
	}

	// 공격 여부 처리
	void playerAttackOk() { 
	    for(int i = 0; i<playerAttackList.size(); i++) {
	        playerAttack = playerAttackList.get(i);
	        playerAttack.snow();
	        
	        for (int j = 0; j<connList.size(); j++) {
	            conn = connList.get(j);
	            if (playerAttack.x > conn.x && playerAttack.x < conn.x + conn.width && playerAttack.y > conn.y && playerAttack.y < conn.y + conn.height) {
	                conn.hp -= playerAttack.attack;
	                playerAttackList.remove(playerAttack);
	                AudioInputStream stream;
	                
	                try {
	    				stream = AudioSystem.getAudioInputStream(new File("./snowcastle/src/image/punch.mp3")); 
	    				Clip clip = AudioSystem.getClip();
	    	            clip.open(stream);
	    	            clip.start();
	    	           
	    			}
	    			catch (Exception e1) {
	    				// TODO Auto-generated catch block
	    				e1.printStackTrace(); 
	    			}
	            }
	            if (conn.hp <= 0) {
	            	// 콘 리스트에서 지우기
	                connList.remove(conn);
	                // 점수 증가
	                score += 50; 
	             }
	        }
	    }
	}

	// 콘을 내보내는 메소드
	void connAppear() { 
	    if(cnt % 80 == 0) {
	    	// 새로운 콘의 위치가 랜덤으로 생김
	        conn = new Conn((int)(Math.random()*1080), 0); 
	        // add 콘
	        connList.add(conn);
	    }
	}

	// 콘 움직임
	void connMove() {
	    for (int i = 0; i<connList.size(); i++) { 
	        conn = connList.get(i);
	        conn.move();
	    }
	}

	// 콘 공격 움직임
	void connAttackkkk() { 
	    if (cnt % 50 == 0) {
	        connAttack = new ConnAttack(conn.x + 110, conn.y + 55);
	        connAttackList.add(connAttack);
	    }

	    for (int i = 0; i<connAttackList.size(); i++) {
	        connAttack = connAttackList.get(i);
	        connAttack.snow();

	        if (connAttack.x> playerX & connAttack.x < playerX + playerWidth && connAttack.y > playerY && connAttack.y < playerY + playerHeight) {
	            
	        	playerHp -= connAttack.attack;
	            connAttackList.remove(connAttack);
	            
	            // 콘에게 공격당했을 때 플레이어의 목숨이 0이하면 게임 오버
	            if (playerHp <= 0) isOver = true; 
	        }
	    }
	}

	// 게임 그래픽
	// 그래픽 클래스 사용함
	public void gameDraw(Graphics g) {
	    playerDraw(g);
	    enemyDraw(g);
	    infoDraw(g);
	}

	// 게임 종료 시 
	public void infoDraw(Graphics g) { 
	    g.setColor(Color.RED);
	    g.setFont(new Font("Agency FB", Font.BOLD, 70));
	    // 점수 텍스트
	    g.drawString("SCORE :  "+ score, 50, 110);
	    
	    // 게임 종료 시 화면
	    if (isOver) { 
	    	//setBackground(new Color(0,0,0,122));
	    	//g.fillRect(0, 0, 1920, 1080);
	    	//g.setColor(new Color(128,128,128));
	    	
	        g.drawImage(over, 480, 200, 1036, 322, null);
	        g.setColor(Color.ORANGE);
	        g.setFont(new Font("Agency FB", Font.BOLD, 250)); 
	        g.drawString(Integer.toString(score), 900, 800);
	        
	    }
	}

	// 플레이어 그래픽
	public void playerDraw(Graphics g) { 
	    g.drawImage(player, playerX, playerY, null);
	    
	    // 플레이어 생명바 색은 그린
	    g.setColor(Color.GREEN); 
	    g.fillRect(playerX - 10, playerY - 40, playerHp * 6, 20); 
	    for(int i = 0; i<playerAttackList.size(); i++) {
	        playerAttack = playerAttackList.get(i);
	        g.drawImage(playerAttack.image, playerAttack.x - 210, playerAttack.y + 100, null);
	    }
	}

	// 콘 그래픽
	public void enemyDraw(Graphics g) { 
	    for (int i = 0; i<connList.size(); i++) {
	        conn = connList.get(i);
	        g.drawImage(conn.image, conn.x - 20, conn.y, null);
	        // 콘 생명바 색깔은 레드
	        g.setColor(Color.RED); 
	        g.fillRect(conn.x + 70, conn.y - 40, conn.hp * 15, 20);
	    }
	    for (int i = 0; i < connAttackList.size(); i++) {
	        connAttack = connAttackList.get(i);
	        g.drawImage(connAttack.image, connAttack.x,connAttack.y, null);
	    }
	}

	public void setUp(boolean up) {
	    this.up = up;
	}

	public void setDown(boolean down) {
	    this.down = down;
	}

	public void setLeft(boolean left) {
	    this.left = left;
	}

	public void setRight(boolean right) {
	    this.right = right;
	}

	public void setShooting(boolean shooting) {
	    this.shooting = shooting;
	}
	public boolean isOver() {
	    return isOver;
	}
	
}

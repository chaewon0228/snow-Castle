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
	 
	// ���� �ð�
	long pretime; 
	// ���� �ð� 
	int delay = 20;
	// �̺�Ʈ �߻� �ֱ� 
	int cnt; 
	
	// ���� ����
	int score;
	
	// �÷��̾� �̹���
	Image player = new ImageIcon("src/image/Ryan.gif").getImage();
	// ���� ���� �̹���
	Image over = new ImageIcon("src/image/gameOver.png").getImage();
	
	// �÷��̾� ��ġ
	int playerX;
	int playerY; 
	// �÷��̾� �̹��� �ʺ�
	int playerWidth = player.getWidth(null);
	// �÷��̾� �̹��� ����
	int playerHeight = player.getHeight(null);
	
	// �÷��̾� �����
	int playerHp = 30; 

	// ����Ű ������ �� �̵��� �Ÿ�
	int playerSpeed = 10; 
	// ����
	boolean up, down, left, right;
	
	// ����
	boolean shooting;
	// ���� ���� Ȯ��
	boolean isOver;

	
	// �÷��̾� ����  ArrayList
	ArrayList<PlayerAttack> playerAttackList = new ArrayList<PlayerAttack>();
	// ��  ArrayList
	ArrayList<Conn> connList = new ArrayList<Conn>();
	// �� ���� ArrayList
	ArrayList<ConnAttack> connAttackList = new ArrayList<ConnAttack>();

	
	PlayerAttack playerAttack;
	Conn conn;
	ConnAttack connAttack;

	@Override
	public void run() {

		// thread �����ϸ� ���� ����
	    reset(); 
	    
	    while(true) {
	    	// ���� ���� üũ
	        while (!isOver) {
	        pretime = System.currentTimeMillis();
	        
	        // ���� �ð� - (cnt�� ������ �ð�) < delay  =>  �� ���̸�ŭ Thread�� delay
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
	    //���� �ʱ�ȭ
	    score = 0;
	    //�̺�Ʈ �ʱ�ȭ
	    cnt = 0;  
	    
	    //ó�� �÷��̾� ��ġ
	    playerX = (Main.SCREEN_WIDTH - playerWidth) / 2;
	    playerY = 750;

	    // ����Ʈ �ʱ�ȭ
	    playerAttackList.clear();
	    connList.clear();
	    connAttackList.clear();
	}
	void keyProcess() { 
		// ȭ�� ������ ������ �ʵ��� ���� ����
	    if (up && playerY - playerSpeed > 0) playerY -= playerSpeed; 
	    if (down && playerY + playerHeight + playerSpeed < Main.SCREEN_HEIGHT) playerY += playerSpeed;
	    if (left && playerX - playerSpeed >0) playerX -= playerSpeed;
	    if (right && playerX + playerWidth + playerSpeed < Main.SCREEN_WIDTH) playerX += playerSpeed;
	    if (shooting && cnt % 15==0) {
	        playerAttack = new PlayerAttack(playerX + 222, playerY +25);
	        playerAttackList.add(playerAttack);
	    }
	}

	// ���� ���� ó��
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
	            	// �� ����Ʈ���� �����
	                connList.remove(conn);
	                // ���� ����
	                score += 50; 
	             }
	        }
	    }
	}

	// ���� �������� �޼ҵ�
	void connAppear() { 
	    if(cnt % 80 == 0) {
	    	// ���ο� ���� ��ġ�� �������� ����
	        conn = new Conn((int)(Math.random()*1080), 0); 
	        // add ��
	        connList.add(conn);
	    }
	}

	// �� ������
	void connMove() {
	    for (int i = 0; i<connList.size(); i++) { 
	        conn = connList.get(i);
	        conn.move();
	    }
	}

	// �� ���� ������
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
	            
	            // �ܿ��� ���ݴ����� �� �÷��̾��� ����� 0���ϸ� ���� ����
	            if (playerHp <= 0) isOver = true; 
	        }
	    }
	}

	// ���� �׷���
	// �׷��� Ŭ���� �����
	public void gameDraw(Graphics g) {
	    playerDraw(g);
	    enemyDraw(g);
	    infoDraw(g);
	}

	// ���� ���� �� 
	public void infoDraw(Graphics g) { 
	    g.setColor(Color.RED);
	    g.setFont(new Font("Agency FB", Font.BOLD, 70));
	    // ���� �ؽ�Ʈ
	    g.drawString("SCORE :  "+ score, 50, 110);
	    
	    // ���� ���� �� ȭ��
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

	// �÷��̾� �׷���
	public void playerDraw(Graphics g) { 
	    g.drawImage(player, playerX, playerY, null);
	    
	    // �÷��̾� ����� ���� �׸�
	    g.setColor(Color.GREEN); 
	    g.fillRect(playerX - 10, playerY - 40, playerHp * 6, 20); 
	    for(int i = 0; i<playerAttackList.size(); i++) {
	        playerAttack = playerAttackList.get(i);
	        g.drawImage(playerAttack.image, playerAttack.x - 210, playerAttack.y + 100, null);
	    }
	}

	// �� �׷���
	public void enemyDraw(Graphics g) { 
	    for (int i = 0; i<connList.size(); i++) {
	        conn = connList.get(i);
	        g.drawImage(conn.image, conn.x - 20, conn.y, null);
	        // �� ����� ������ ����
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

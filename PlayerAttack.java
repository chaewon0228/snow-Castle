package snowcastle;

import javax.swing.*;
import java.awt.*;

public class PlayerAttack {
	// 플레이어 눈덩이
	Image image = new ImageIcon("src/image/snowBall.png").getImage();
	// 눈덩이 좌표
	int x, y;
	
	// 눈덩이 너비
	int width = image.getWidth(null);
	// 눈덩이 높이
	int height = image.getHeight(null);
	// 플레이어 공격력
	int attack = 5; 

	// 플레이어  공격 좌표
	public PlayerAttack(int x, int y ) { 
	    this.x = x;
	    this.y = y;
	}

	// 공격 메소드
	public void snow() { 
		// 속도
	    this.y -= 25; 
	}
}

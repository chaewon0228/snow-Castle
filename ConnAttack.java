package snowcastle;

import javax.swing.*;
import java.awt.*;

public class ConnAttack {
	
	// 콘이 던지는 눈덩이
	Image image = new ImageIcon("src/image/snowBall.png").getImage(); 
	// 눈덩이 좌표
	int x, y;
	
	// 눈덩이 너비
	int width = image.getWidth(null); 
	// 눈덩이 높이
	int height = image.getHeight(null);
	
	// 콘의 공격력
	int attack = 10; 

	public ConnAttack(int x, int y) {
	    this.x = x;
	    this.y = y;
	}

	// 콘이 던지는 눈을 이동이키는 메소드
	public void snow() {  
		// 눈덩이 속도
	    this.y += 10; 
	}
}

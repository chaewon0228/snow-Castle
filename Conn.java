package snowcastle;

import javax.swing.*;
import java.awt.*;

public class Conn {
	
	// 콘 이미지
	Image image = new ImageIcon("src/image/downcon.gif").getImage(); 
	// 콘 좌표
	int x, y;
	
	// 콘 너비
	int width = 400; 
	// 콘 높이
	int height = image.getHeight(null);
	// 콘 생명값
	int hp = 10;

	// 좌표를 인수로 받는 생성자
	public Conn(int x, int y) {
	    this.x = x;
	    this.y = y; 
	}

	// 콘을 움직이게 할 메소드
	public void move() { 
		 // 속도
	     this.y += 3;
	}
}

package snowcastle;

import javax.swing.*;
import java.awt.*;

public class ConnAttack {
	
	// ���� ������ ������
	Image image = new ImageIcon("src/image/snowBall.png").getImage(); 
	// ������ ��ǥ
	int x, y;
	
	// ������ �ʺ�
	int width = image.getWidth(null); 
	// ������ ����
	int height = image.getHeight(null);
	
	// ���� ���ݷ�
	int attack = 10; 

	public ConnAttack(int x, int y) {
	    this.x = x;
	    this.y = y;
	}

	// ���� ������ ���� �̵���Ű�� �޼ҵ�
	public void snow() {  
		// ������ �ӵ�
	    this.y += 10; 
	}
}

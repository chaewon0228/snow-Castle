package snowcastle;

import javax.swing.*;
import java.awt.*;

public class Conn {
	
	// �� �̹���
	Image image = new ImageIcon("src/image/downcon.gif").getImage(); 
	// �� ��ǥ
	int x, y;
	
	// �� �ʺ�
	int width = 400; 
	// �� ����
	int height = image.getHeight(null);
	// �� ����
	int hp = 10;

	// ��ǥ�� �μ��� �޴� ������
	public Conn(int x, int y) {
	    this.x = x;
	    this.y = y; 
	}

	// ���� �����̰� �� �޼ҵ�
	public void move() { 
		 // �ӵ�
	     this.y += 3;
	}
}

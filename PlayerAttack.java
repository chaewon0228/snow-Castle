package snowcastle;

import javax.swing.*;
import java.awt.*;

public class PlayerAttack {
	// �÷��̾� ������
	Image image = new ImageIcon("src/image/snowBall.png").getImage();
	// ������ ��ǥ
	int x, y;
	
	// ������ �ʺ�
	int width = image.getWidth(null);
	// ������ ����
	int height = image.getHeight(null);
	// �÷��̾� ���ݷ�
	int attack = 5; 

	// �÷��̾�  ���� ��ǥ
	public PlayerAttack(int x, int y ) { 
	    this.x = x;
	    this.y = y;
	}

	// ���� �޼ҵ�
	public void snow() { 
		// �ӵ�
	    this.y -= 25; 
	}
}

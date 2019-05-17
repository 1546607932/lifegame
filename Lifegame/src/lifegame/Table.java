package lifegame;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class Table extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	private int horizontal_count;
	private int vertical_count;

	private JButton cell[][]; // ����
	boolean now[][]; // ��ǰ״̬
	private boolean next[][]; // ��һ״̬
	private int count; // ��ǰ��������

	private Color live = Color.decode("#06bbfc"); // ������ɫ
	private Color dead = Color.white; // ��������ɫ

	private int speed = 200; // ˢ���ٶ�
	private boolean doit; // �Ƿ���ͣ

	private Mouse_Ctrl mouse = new Mouse_Ctrl();

	Table(int width, int height, int horizontal_count, int vertical_count) { // ���췽��

		this.horizontal_count = horizontal_count;
		this.vertical_count = vertical_count;
		setSize(width, height);
		setLayout(new GridLayout(horizontal_count, vertical_count));
		cell = new JButton[horizontal_count][vertical_count];
		int cellsize_x, cellsize_y;
		cellsize_x = width / horizontal_count;
		cellsize_y = height / vertical_count;
		for (int i = 0; i < horizontal_count; i++) {
			for (int j = 0; j < vertical_count; j++) {
				cell[i][j] = new JButton();
				cell[i][j].setSize(cellsize_x, cellsize_y);
				cell[i][j].setBackground(dead);
				cell[i][j].setFocusPainted(false);
				cell[i][j].addMouseListener(mouse);
				add(cell[i][j]);
			}
		}
		now = new boolean[horizontal_count][vertical_count];
		next = new boolean[horizontal_count][vertical_count];
	}

	boolean getNextState(int x, int y) {
		int count = 0;
		// �ĸ���
		if (x == 0 && y == 0) { // ����
			count += convert(now[x][y + 1]) + convert(now[x + 1][y]) + convert(now[x + 1][y + 1]);
		} else if (x == 0 && y == vertical_count - 1) { // ����
			count += convert(now[x][y - 1]) + convert(now[x + 1][y]) + convert(now[x + 1][y - 1]);
		} else if (x == horizontal_count - 1 && y == 0) { // ����
			count += convert(now[x - 1][y]) + convert(now[x][y + 1]) + convert(now[x - 1][y + 1]);
		} else if (x == horizontal_count - 1 && y == vertical_count - 1) { // ����
			count += convert(now[x - 1][y]) + convert(now[x][y - 1]) + convert(now[x - 1][y - 1]);
		}
		// ������
		else if (x == 0) { // ��
			count += convert(now[x][y - 1]) + convert(now[x][y + 1]);
			for (int i = y - 1; i <= y + 1; i++)
				count += convert(now[x + 1][i]);
		} else if (y == 0) { // ��
			count += convert(now[x - 1][y]) + convert(now[x + 1][y]);
			for (int i = x - 1; i <= x + 1; i++) {
				count += convert(now[i][y + 1]);
			}
		} else if (x == horizontal_count - 1) { // ��
			count += convert(now[x][y - 1]) + convert(now[x][y + 1]);
			for (int i = y - 1; i <= y + 1; i++)
				count += convert(now[x - 1][i]);
		} else if (y == vertical_count - 1) { // ��
			count += convert(now[x - 1][y]) + convert(now[x + 1][y]);
			for (int i = x - 1; i <= x + 1; i++)
				count += convert(now[i][y - 1]);
		}
		// �м�
		else {
			for (int i = x - 1; i <= x + 1; i++)
				for (int j = y - 1; j <= y + 1; j++) {
					if (i != x || j != y)
						count += convert(now[i][j]);

				}
		}
		// ���ݽ��������һ��״̬
		if (count < 2 || count > 3) {
			return false;
		} else if (count == 2) {
			return now[x][y];
		} else {
			return true;
		}
	}

	public void updateTable() {
		count = 0;
		for (int i = 0; i < horizontal_count; i++) {
			for (int j = 0; j < vertical_count; j++) {
				if (cell[i][j].getBackground() == live) {
					now[i][j] = true;
				} else {
					now[i][j] = false;
				}
			}
		}

		for (int i = 0; i < horizontal_count; i++) {
			for (int j = 0; j < vertical_count; j++) {
				next[i][j] = getNextState(i, j);
				if (next[i][j] == true) {
					cell[i][j].setBackground(live);
					count++;
				} else {
					cell[i][j].setBackground(dead);
				}
			}
		}
	}

	private static int convert(boolean b) {
		if (b)
			return 1;
		else
			return 0;
	}

	public void run() {
		while (true) {
			if (isDoit()) {
				updateTable();
			}
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
			}
		}
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getSpeed() {
		return speed;
	}

	public void start() {
		setDoit(true);
	}

	public void pause() {
		setDoit(false);
	}

	public int randSet(double rate) {
		pause();
		count = 0;
		for (int i = 0; i < horizontal_count; i++)
			for (int j = 0; j < vertical_count; j++) {
				if (Math.random() <= rate) {
					cell[i][j].setBackground(live);
					count++;
				} else
					cell[i][j].setBackground(dead);
			}
		return count;
	}

	public boolean isDoit() {
		return doit;
	}

	public void setDoit(boolean doit) {
		this.doit = doit;
	}

	
	class Mouse_Ctrl extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			JButton bt = (JButton) e.getSource();
			if (bt.getBackground() == live) {
				bt.setBackground(dead);
			} else if (bt.getBackground() == dead) {
				bt.setBackground(live);
			}
		}

	}
}

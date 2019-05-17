package lifegame;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/** */
public class Lifegame extends JFrame {
	private final int TAB_X = 550;
	private final int TAB_Y = 550;
	private final int TAB_X_CNT = 55;
	private final int TAB_Y_CNT = 55;

	private final int FRAME_X = 800;
	private final int FRAME_Y = 700;
  
	private static final int TAB_LOC_X = 230;
	private static final int TAB_LOC_Y = 50;

	private final int LABLE_LEN = 250;
	private final int LABLE_HEI = 40;
	private final int SCRO_LEN = 185;
	private final int SCRO_HEI = 25;

	private static final long serialVersionUID = 1L;
	private Container container;
	private Thread thread;
	private Table table;
	private JLabel speed_tip;
	private JTextField speed_tex;
	private JScrollBar speed_bar;
	private JLabel live_tip;
	private JTextField live_tex;
	private JScrollBar live_bar;
	private JButton start;

	private ButtonList start_listen = new ButtonList();
	private BarList bar_listen = new BarList();

	private static Font font = new Font("微软雅黑", Font.BOLD, 20);
	public static Color col_bt = Color.decode("#06BBFC");

	
	Lifegame(final String title) {
		super(title);
		setIconImage(new ImageIcon("head.jpg").getImage());
		setSize(FRAME_X, FRAME_Y);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		setResizable(false);

		container = getContentPane();
		container.setLayout(null);
		container.setBackground(Color.cyan);

		table = new Table(TAB_X, TAB_Y, TAB_X_CNT, TAB_Y_CNT);
		thread = new Thread(table);
		table.setLocation(TAB_LOC_X, TAB_LOC_Y);
		container.add(table);

		speed_tip = new JLabel("迭代速度:          s/次");
		speed_tip.setFont(font);
		speed_tip.setBounds(20, 200, LABLE_LEN, LABLE_HEI);
		container.add(speed_tip);

		speed_tex = new JTextField(String.valueOf((double) table.getSpeed() / 1000));
		speed_tex.setFont(font);
		speed_tex.setBounds(120, 206, 40, 30);
		speed_tex.setBackground(Color.white);
		speed_tex.setHorizontalAlignment(JTextField.CENTER);
		speed_tex.setEnabled(false);
		speed_tex.setDisabledTextColor(Color.black);
		container.add(speed_tex);

		speed_bar = new JScrollBar(JScrollBar.HORIZONTAL, 200, 10, 100, 2210);
		speed_bar.setBounds(20, 240, SCRO_LEN, SCRO_HEI);
		speed_bar.addMouseListener(bar_listen);
		container.add(speed_bar);

		live_tip = new JLabel("随机           %个体存活");
		live_tip.setBounds(10, 320, LABLE_LEN, LABLE_HEI);
		live_tip.setFont(font);
		container.add(live_tip);

		live_tex = new JTextField("0");
		live_tex.setFont(font);
		live_tex.setHorizontalAlignment(JTextField.CENTER);
		live_tex.setBounds(52, 328, 60, 28);
		container.add(live_tex);

		live_bar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 10, 0, 1010);
		live_bar.setBounds(20, 360, SCRO_LEN, SCRO_HEI);
		live_bar.addMouseListener(bar_listen);
		container.add(live_bar);

		start = new JButton("开始");
		start.setFont(font);
		start.setFocusPainted(false);
		start.setBackground(col_bt);
		start.setBounds(20, 480, 185, 70);
		start.addActionListener(start_listen);
		container.add(start);

		thread.start();
	}

	public void setStart() {
		start.setText("开始");
	}

	public void setPause() {
		start.setText("暂停");
	}

	public void setContinue() {
		start.setText("继续");
	}

	private class ButtonList implements ActionListener {

		public void actionPerformed(final ActionEvent e) {
			JButton bt = (JButton) e.getSource();
			if (bt == start) {
				if (bt.getText() == "暂停") {
					table.pause();
					bt.setText("继续");
				} else if (bt.getText() == "开始") {
					table.start();
					bt.setText("暂停");
				} else if (bt.getText() == "继续") {
					table.start();
					bt.setText("暂停");
				}
			}
		}
	}

	/** */
	private class BarList extends MouseAdapter {
		/** */
		public void mouseReleased(final MouseEvent e) {
			JScrollBar js = (JScrollBar) e.getSource();
			if (js == speed_bar) {
				double value = js.getValue();
				speed_tex.setText(String.format("%.1f", value / 1000));
				table.setSpeed((int) value);
			} else if (js == live_bar) {
				double value = js.getValue();
				live_tex.setText(String.format("%.2f", value / 10));
				setStart();
				table.pause();
				table.randSet(value / 1000);
			}
		}

	}
}

package main.dialog.builder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import board.Board;
import main.BoardBuilder;

public class LoadBoard extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton buttonLoad;
	private JLabel heading;
	private JLabel jLabel2;
	private JTextField txtFile;

	private JDialog frame;

	public LoadBoard(JDialog frame) {
		this.frame = frame;
		initComponents();
	}

	private void initComponents() {
		heading = new JLabel();
		txtFile = new JTextField();
		jLabel2 = new JLabel();
		buttonLoad = new JButton();

		heading.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
		heading.setText("Load or create a board:");

		txtFile.setFont(new java.awt.Font("Tahoma", 0, 14));

		jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel2.setText(".txt");

		buttonLoad.setText("Load");
		buttonLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtFile.getText().equals("")) txtFile.setText("testBoard");
				BoardBuilder.board = new Board(txtFile.getText() + ".txt");
				//				main.current = new BoardBuilder(display, main);
				frame.dispose();
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(19, 19, 19).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(buttonLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addComponent(txtFile, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel2).addGap(6, 6, 6)).addComponent(heading))).addContainerGap(17, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(heading).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(txtFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel2)).addGap(18, 18, 18).addComponent(buttonLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
	}

}

package main.dialog.builder;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.BoardBuilder;

public class Resize extends JPanel {
	private static final long serialVersionUID = 1L;

	private JButton buttonSet;
	private JLabel labelWidth;
	private JLabel labelHeight;
	private JTextField wText;
	private JTextField hText;
	private BoardBuilder bb;

	private int bWidth, bHeight;

	public Resize(BoardBuilder bb, int bWidth, int bHeight) {
		this.bb = bb;
		this.bWidth = bWidth;
		this.bHeight = bHeight;

		initComponents();
	}

	private void initComponents() {
		labelWidth = new javax.swing.JLabel();
		labelHeight = new javax.swing.JLabel();
		buttonSet = new javax.swing.JButton();
		wText = new javax.swing.JTextField();
		hText = new javax.swing.JTextField();

		wText.setText("" + bWidth);
		hText.setText("" + bHeight);

		wText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) resize();
			}
		});
		hText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) resize();
			}
		});

		labelWidth.setText("Width: ");

		labelHeight.setText("Height: ");
		labelHeight.setName("Width: "); // NOI18N

		buttonSet.setText("Set");
		buttonSet.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				resize();
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(labelWidth).addComponent(labelHeight)).addGap(10, 10, 10).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(wText, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE).addComponent(hText)).addContainerGap()).addComponent(buttonSet, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(labelWidth).addComponent(wText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(labelHeight).addComponent(hText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(buttonSet)));
	}

	private void resize() {
		int newWidth = Integer.parseInt(wText.getText()), newHeight = Integer.parseInt(hText.getText());

		UserAction.addResizeAction(bb.getBoardSize(), new Dimension(newWidth, newHeight), bb);

		bb.resize(newWidth, newHeight);
	}
}

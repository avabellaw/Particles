package main.dialog;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import io.res.FileHandler;

public class Help extends JPanel {

	private static final long serialVersionUID = 1L;

	public Help(FileHandler file) {
		JDialog frame = new JDialog();
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setTitle("? Help");
		setPreferredSize(new Dimension(300, 250));
		frame.add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		JScrollPane pane;
		JEditorPane text = new JEditorPane();
		text.setContentType("text/html");
		text.setText(file.getText());

		pane = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		text.setCaretPosition(0);
		frame.getContentPane().add(pane);

	}

	@Override
	public void paintComponent(Graphics g) {
	}

}

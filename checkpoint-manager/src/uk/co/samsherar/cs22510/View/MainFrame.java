package uk.co.samsherar.cs22510.View;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;

public class MainFrame extends JFrame {
	public MainFrame() {
		this.setLayout(new BorderLayout());
		setTitle("Hello World");
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		MainPane p = new MainPane();
		LeftPane lp = new LeftPane();
		this.add(lp, BorderLayout.WEST);	
		this.add(p, BorderLayout.CENTER);
		this.repaint();
		this.validate();
		this.setVisible(true);
	}
}

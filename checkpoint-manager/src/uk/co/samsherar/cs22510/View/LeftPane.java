package uk.co.samsherar.cs22510.View;

import javax.swing.*;
import java.awt.*;

public class LeftPane extends JPanel {
	public LeftPane() {
		GridLayout gl = new GridLayout(0,2);
		this.setPreferredSize(new Dimension(300,0));
		gl.setHgap(10);
		gl.setVgap(10);
		JTextField cpType = new JTextField();
		JTextField cpTime = new JTextField();
		this.setLayout(gl);
		this.add(new JLabel("Blah:"));
		this.add(cpType);
		this.add(new JLabel("Blah:"));
		this.add(cpTime);

	}
}

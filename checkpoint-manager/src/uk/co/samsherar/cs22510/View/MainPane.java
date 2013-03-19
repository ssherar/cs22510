package uk.co.samsherar.cs22510.View;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListDataListener;

public class MainPane extends JPanel {

	public MainPane() {
		super(new BorderLayout());
		this.setBackground(Color.black);
		int arr[] = {1,2,3,4 };
		Model m = new Model();
		m.addElement(1);
		m.addElement(2);
		JList list = new JList(m);
		this.add(list, BorderLayout.CENTER);
	}
	
	class Model extends DefaultListModel {
		
	}
}

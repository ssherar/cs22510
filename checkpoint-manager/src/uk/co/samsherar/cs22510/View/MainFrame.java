package uk.co.samsherar.cs22510.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainFrame extends JFrame {
	private String[] item1 = {
			"One",
			"Two",
			"3"
	};
	
	private String[] item2 = {
			"1",
			"2",
			"3"
	};
	final JCheckBox medical;
	final JTextField depart;
	public MainFrame() {
		this.setLayout(new GridLayout(0,2));
		setTitle("Hello World");
		setSize(300,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(new JLabel("Entrants: "));
		JComboBox entrants = new JComboBox(item1);
		this.add(entrants);
		
		this.add(new JLabel("Checkpoints: "));
		JComboBox checkpoints = new JComboBox(item2);
		this.add(checkpoints);
		
		this.add(new JLabel("Medical Checkpoint? "));
		medical = new JCheckBox();
		medical.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				depart.setEnabled(medical.isSelected());
			}
			
		});
		this.add(medical);
		
		this.add(new JLabel("Arrival: "));
		JTextField arrival = new JTextField();
		this.add(arrival);
		
		this.add(new JLabel("Depart: "));
		depart = new JTextField();
		depart.setEnabled(false);
		this.add(depart);
		
		JButton add = new JButton("Add");
		JButton exit = new JButton("Exit");
		
		this.add(add);
		this.add(exit);
		
		this.pack();
		
		this.repaint();
		this.validate();
		this.setVisible(true);
	}
}

package uk.co.samsherar.cs22510.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import uk.co.samsherar.cs22510.Model.*;

import javax.swing.*;

public class MainFrame extends JFrame {

	final JCheckBox medical;
	final JTextField depart;
	JComboBox entrants;
	JComboBox checkpoints;
	
	public MainFrame() {
		this.setLayout(new GridLayout(0,2));
		setTitle("Hello World");
		setSize(300,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(new JLabel("Entrants: "));
		entrants = new JComboBox();
		this.add(entrants);
		
		this.add(new JLabel("Checkpoints: "));
		checkpoints = new JComboBox();
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
	
	public void populateEntrants(LinkedList<Entrant> entrants) {
		this.entrants.addItem("");
		for(Entrant e : entrants) {
			this.entrants.addItem(e.getName());
		}
	}
	
	public void setCourses(LinkedList<Course> courses) {
//		this.checkpoints.addItem("");
//		LinkedList<Integer> course = courses.get(0).getCheckpoints();
//		for(int node : course) {
//			this.checkpoints.addItem(node);
//		}
	}
	
	public void setCheckpoints(LinkedList<Integer> checkpoints) {
		this.checkpoints.addItem("");
		for(int node : checkpoints) {
			this.checkpoints.addItem(node);
		}
	}
}

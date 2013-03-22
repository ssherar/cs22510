package uk.co.samsherar.cs22510.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import uk.co.samsherar.cs22510.Controller.Manager;
import uk.co.samsherar.cs22510.Model.*;

import javax.swing.*;

/**
 * The main GUI. Nothing more, nothing less
 * @author Samuel B Sherar <sbs1@aber.ac.uk>
 */
public class MainFrame extends JFrame {
	/**
	 * The medical checkbox
	 */
	private final JCheckBox medical;
	
	/**
	 * the departure field
	 */
	private final JTextField depart;
	/**
	 * the entrants combobox
	 */
	private final JComboBox entrants;
	
	/**
	 * the checkpoints combobox
	 */
	private final JComboBox checkpoints;
	
	/**
	 * Draws the Frame to the screen
	 */
	public MainFrame() {
		this.setLayout(new GridLayout(0,2));
		setTitle("Checkpoint Manager");
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
			/*
			 * Allows for the departure field to be enabled/disabled at click
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				depart.setEnabled(medical.isSelected());
			}
			
		});
		this.add(medical);
		
		this.add(new JLabel("Arrival: "));
		final JTextField arrival = new JTextField();
		this.add(arrival);
		
		this.add(new JLabel("Depart: "));
		depart = new JTextField();
		depart.setEnabled(false);
		this.add(depart);
		
		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			/*
			 * Scrapes the data and tries to save it using the Manager
			 * @see uk.co.samsherar.cs22510.Controller.Manager#getInstance()
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Manager m = Manager.getInstance();
				int ret = m.addTime(entrants.getSelectedItem(), checkpoints.getSelectedItem(),
							arrival.getText(), depart.getText());
				if(ret > 0) {
					String message = "";
					if(ret == 1) {
						message = "File lock didn't work. Please try again later";
					} else if (ret == 2) {
						message = "File written, but competitor is now excluded";
					} else if (ret == 3) {
						message = "Competitor is excluded";
					} else {
						message = "Time added successfully";
					}
					m.appendLog(message);
					JOptionPane.showMessageDialog(null, message);
				}
				arrival.setText("");
				depart.setText("");
			}
			
		});
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			/*
			 * Exits out of the program
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
			
		});
		
		this.add(add);
		this.add(exit);
		
		this.pack();
		
		this.repaint();
		this.validate();
		this.setVisible(true);
	}
	
	/**
	 * Populates the entrants combobox
	 * @param entrants the list of entrants
	 */
	public void populateEntrants(LinkedList<Entrant> entrants) {
		this.entrants.addItem("");
		for(Entrant e : entrants) {
			this.entrants.addItem(e.getName());
		}
	}
	
	/**
	 * Populates the checkpoint combobox
	 * @param checkpoints the list of checkpoints
	 */
	public void setCheckpoints(LinkedList<Integer> checkpoints) {
		this.checkpoints.addItem("");
		for(int node : checkpoints) {
			this.checkpoints.addItem(node);
		}
	}
}

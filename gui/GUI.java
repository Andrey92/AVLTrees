package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import alberi.*;

public class GUI extends JFrame {

	private String title = "Alberi AVL";
	private PannelloGrafico panel;
	private JPanel commandPanel; 
	private Dimension screenSize;
	private JTextField txtInserisci;
	private JButton btnInserisci;
	private JButton btnRemove;
	private JLabel lblInfo;

	private AVL<Integer> avl;

	public GUI() {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MyActionListener myListener = new MyActionListener();
		
		JPanel commandPanel = new JPanel();
		txtInserisci = new JTextField(10);
		btnInserisci = new JButton("Inserisci");
		btnRemove = new JButton("Rimuovi");
		btnInserisci.addActionListener(myListener);
		btnRemove.addActionListener(myListener);
		lblInfo = new JLabel("<html>Lo scopo del programma è illustrare il funzionamento degli AVL<br>" +
					"Per semplicità si utilizzano numeri interi e non sono ammessi duplicati.<br>" +
					"Per rimuovere un nodo basta selezionarlo e cliccare su Rimuovi.</html>");
		JPanel flowPanel = new JPanel();
		flowPanel.setLayout(new FlowLayout());
		flowPanel.add(txtInserisci);
		flowPanel.add(btnInserisci);
		flowPanel.add(btnRemove);
		flowPanel.add(lblInfo);
		commandPanel.add(flowPanel, BorderLayout.NORTH);

		panel = new PannelloGrafico(btnRemove);
		JScrollPane scrollPanel = new JScrollPane(panel);
		scrollPanel.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

		add(scrollPanel, BorderLayout.CENTER);
		add(commandPanel, BorderLayout.SOUTH);

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(2 * screenSize.width / 3, 2 * screenSize.height / 3);
		setLocation(screenSize.width / 6, screenSize.height / 6);
		
		avl = new AVL<Integer>();
	}

	private class MyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnInserisci) {
				if (txtInserisci.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Inserire un valore intero!");
					return;
				}
				int val;
				try {
					val = Integer.parseInt(txtInserisci.getText());
				} catch (RuntimeException ex) {
					JOptionPane.showMessageDialog(null, "Stringa non valida!");
					return;
				}
				if (val / 1000000 != 0) JOptionPane.showMessageDialog(null,
							"Il numero inserito contiene più di 6 cifre e potrebbe\n" +
							"non essere visualizzato correttamente nel proprio nodo.");
				if (avl.cerca(val) != null) {
					JOptionPane.showMessageDialog(null, "Valore già presente.");
					return;
				}
				avl.inserisci(val);
			} else if (e.getSource() == btnRemove) avl.rimuovi(panel.selected());
			panel.disegnaAlbero(avl.getRadice());
		}
	}
}

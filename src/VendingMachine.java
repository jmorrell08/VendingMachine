import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class VendingMachine extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3201182068203027645L;
	private VendingMachineUtilities vendingMachineUtilities;
	private ArrayList<Soda> sodas = new ArrayList<Soda>();
	private boolean hasQuarter = false;
	private JLabel lblDispense;
	private JComboBox<String> comboBox;
	private JButton btnPressToInsert;
	private JButton btnVendSelectedSoda;
	private JButton btnCoinReturn;
	
	public VendingMachine() {
		
		vendingMachineUtilities = new VendingMachineUtilities();
		
		sodas = vendingMachineUtilities.readSodaList(Constants.SODA_LIST_FILE_NAME);
		
		lblDispense = new JLabel("");
		lblDispense.setBounds(58, 193, 170, 63);
		this.getContentPane().add(lblDispense);
		
		comboBox = new JComboBox<String>();
		comboBox.addItem("");
		comboBox.setBounds(58, 87, 170, 20);		
		
		for (int i = 0; i < sodas.size(); i++) {
			comboBox.addItem(sodas.get(i).getName());
		}
		this.getContentPane().setLayout(null);
		this.getContentPane().add(comboBox);
		
		btnPressToInsert = new JButton(Constants.DEFAULT_QUARTER_BUTTON);
		btnPressToInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (!hasQuarter) {
					btnPressToInsert.setText(Constants.ACTIVE_QUARTER_BUTTON);
					hasQuarter = true;
				} else {
					lblDispense.setText("");
					JOptionPane.showMessageDialog(null,
						    Constants.EXISTING_QUARTER,
						    Constants.WARNING_TITLE,
						    JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnPressToInsert.setBounds(58, 24, 170, 28);
		this.getContentPane().add(btnPressToInsert);
		
		btnCoinReturn = new JButton(Constants.COIN_RETURN_TEXT);
		btnCoinReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hasQuarter) {
					hasQuarter = false;
					btnPressToInsert.setText(Constants.DEFAULT_QUARTER_BUTTON);
					JOptionPane.showMessageDialog(null, 
							Constants.RETURN_COIN_MESSAGE,
							Constants.MESSAGE_TITLE,
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnCoinReturn.setBounds(58, 58, 169, 23);
		this.getContentPane().add(btnCoinReturn);
		
		btnVendSelectedSoda = new JButton(Constants.VEND_BUTTON_TEXT);
		btnVendSelectedSoda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				if (hasQuarter) {
					if (comboBox.getSelectedItem().toString().isEmpty()) {
						lblDispense.setText("");
						JOptionPane.showMessageDialog(null,
							    Constants.MAKE_A_SELECTION,
							    Constants.WARNING_TITLE,
							    JOptionPane.WARNING_MESSAGE);
					} else {
						if (!isSoldOut(comboBox.getSelectedItem().toString())) {
							deductFromInventory(comboBox.getSelectedItem().toString());
							lblDispense.setText(Constants.SUCCESSFUL_TRANSACTION_PREFIX + comboBox.getSelectedItem() + Constants.SUCCESSFUL_TRANSACTION_SUFFIX);
							btnPressToInsert.setText(Constants.DEFAULT_QUARTER_BUTTON);
							hasQuarter = false;
							vendingMachineUtilities.saveSodaList(sodas, Constants.SODA_LIST_FILE_NAME);
						} else {
							lblDispense.setText(comboBox.getSelectedItem().toString() + Constants.SOLD_OUT_SUFFIX);
						}						
					}
				} else {
					lblDispense.setText("");
					JOptionPane.showMessageDialog(null,
						    Constants.INSERT_QUARTER_FIRST,
						    Constants.WARNING_TITLE,
						    JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnVendSelectedSoda.setBounds(58, 141, 170, 23);
		this.getContentPane().add(btnVendSelectedSoda);
	}

	
	private boolean deductFromInventory(String sodaName) {
		boolean success = false;
		
		for (int i = 0; i < sodas.size(); i++) {
			if (sodas.get(i).getName() == sodaName && sodas.get(i).getQuanity() > 0) {
				
				System.out.println("removing from inventory");
				sodas.get(i).setQuanity(sodas.get(i).getQuanity() - 1);
				success = true;
				break;
			}
		}
		
		return success;
	}
	
	public boolean isSoldOut(String sodaName) {
		boolean retValue = true;
		
		for (int i = 0; i < sodas.size(); i++) {
			if (sodas.get(i).getName().equals(sodaName) && sodas.get(i).getQuanity() > 0) {
				retValue = false;
				break;
			}
		}
		
		return retValue;
	}
	
	public void saveInventory() {
		this.vendingMachineUtilities.saveSodaList(this.sodas, Constants.SODA_LIST_FILE_NAME);
	}
	
}

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;

import com.sun.glass.events.WindowEvent;

public class VendingMachineWindow {

	private VendingMachine vendingMachine;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VendingMachineWindow window = new VendingMachineWindow();
					window.vendingMachine.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VendingMachineWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		vendingMachine = new VendingMachine();
		vendingMachine.setTitle(Constants.APPLICATION_TITLE);
		vendingMachine.setBounds(100, 100, 305, 318);
		vendingMachine.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		vendingMachine.addWindowListener(new WindowAdapter() {
		    public void WindowClosing(WindowEvent e) {
		        System.out.println("test");
		        vendingMachine.dispose();
		    }
		});
	}
}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class VendingMachineUtilities {
	
	private String csvSplitBy = ",";
	
	public VendingMachineUtilities() {
		
	}
	
	public VendingMachineUtilities(String csvSplitBy) {
		this.csvSplitBy = csvSplitBy;
	}
	
	public ArrayList<Soda> readSodaList(String sodaListFileName) {
		
		ArrayList<Soda> sodas = new ArrayList<Soda>();
	
		InputStream in = getClass().getResourceAsStream(sodaListFileName);
		InputStreamReader isr = new InputStreamReader(in);
		
		BufferedReader br = new BufferedReader(isr);
		
		String currentLine = "";
		
		try {
			while ((currentLine = br.readLine()) != null) {
				String[] currentSoda = currentLine.split(csvSplitBy);
				sodas.add(new Soda(currentSoda[Constants.SODA_NAME_INDEX], Integer.parseInt(currentSoda[Constants.SODA_QUANTITY_INDEX].trim())));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sodas;
		    
	}
	
	public void saveSodaList(ArrayList<Soda> sodaList, String sodaListFileName) {
		try {
			PrintWriter writer = 
			           new PrintWriter(
			                 new File(this.getClass().getResource(sodaListFileName).getPath()));
			
			for (int i = 0; i < sodaList.size(); i++) {
				writer.write(sodaList.get(i).getName() + "," + sodaList.get(i).getQuanity());
				writer.write(System.getProperty("line.separator"));
				
			}
			
			writer.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

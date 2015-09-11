/**
 * Author: Quang Vu
 * Date: 03/19/13
 * Class: CS243 Object - Oriented.
 * Purpose: WRITE A CLASS THAT REPRESENT A AM/FM RADIO.
 * Assignment: Lab 02. 
 */
import java.util.ArrayList;

public class CarRadio{
	// Declaring all the fields of the class CarRadio.
	// Private -- denied access from other classes.
	// Public -- allowed other classes to access/use the methods.
	private static int currentVolume;
	private static int maxVolume;
	private static int minVolume;
	private static boolean powerButton;
	private static boolean muteButton;
	private static int maxFreqAM;
	private static int minFreqAM;
	private static int maxFreqFM;
	private static int minFreqFM;
	private static boolean switchAmFm;
	private static int currentFreqAm;
	private static int currentFreqFm;
	private static boolean setUnPrepare;
	private static boolean set1stPrepare;
	private static boolean set2ndPrepare;
	private static ArrayList<Integer> presetButtonPrimaryAM;
	private static ArrayList<Integer> presetButtonSecondaryAM;
	private static ArrayList<Integer> presetButtonPrimaryFM;
	private static ArrayList<Integer> presetButtonSecondaryFM;
	private StationData station;
	
	
	public CarRadio(StationData station){
		//The Constructor initialize/construct the fields of the class CarRadio.
		currentVolume = minVolume;
		maxVolume = 20;
		minVolume = 0;
		powerButton = false;
		muteButton = false;
		maxFreqAM = 1610;
		minFreqAM = 520;
		maxFreqFM = 107900;
		minFreqFM = 87900; 
		switchAmFm = false;
		currentFreqAm = minFreqAM;
		currentFreqFm = minFreqFM;
		setUnPrepare = true;
		set1stPrepare = false;
		set2ndPrepare = false;
		presetButtonPrimaryAM = new ArrayList<Integer>();
		presetButtonSecondaryAM = new ArrayList<Integer>();
		presetButtonPrimaryFM = new ArrayList<Integer>();
		presetButtonSecondaryFM = new ArrayList<Integer>();

		for(int i= 0; i <= 4; i++){
			// Initialize five slots in the array list.
			presetButtonPrimaryAM.add(520);
			presetButtonSecondaryAM.add(520);	
		}
		for(int i = 0; i <= 4; i++){
			// Initialize five slots in the array list.
			presetButtonPrimaryFM.add(87900);
			presetButtonSecondaryFM.add(87900);
		}
		this.station = station;
	}
	
	public void powerBtn(){
		// This method change the boolean statement to the opposite boolean statement.
		// Purpose: change the power on/off.
		powerButton = !powerButton;

		setUnPrepare = true;
		set1stPrepare = false;
		set2ndPrepare = false;
		
	}
	public void volumeUpBtn(){
		// This method increase the volumn of the radio by one. 
		// Only work if the current volumn is smaller than max volumn and bigger or equal to the min volumn.
		if(powerButton == true){
			if (currentVolume < maxVolume && currentVolume >= minVolume ){
				currentVolume++;
			}			
		}
	}
	public void volumeDownBtn(){
		// This method decrease the volumn of the radio by one.
		// Only work if the current volumn is smaller or equal to the max volumn and bigger than the min volumn.
		if(powerButton == true){
			if (currentVolume <= maxVolume && currentVolume > minVolume){
				currentVolume--;	
			}
			
		}
	}
	public void muteBtn(){
		// This method change the boolean statement to the opposite boolean statement.
		// Purpose: Mute or un-mute the radio.
		muteButton = !muteButton;
	}
	public void amfmBtn(){
		// false is AM and true is FM.
		// This method change the boolean statement to the opposite booleean statement.
		// Reset the set to unprepare.
		// Purpose: switch the AM band(false) to FM band(true) or FM band(true) to AM band(false).
		switchAmFm = !switchAmFm;
		setUnPrepare = true;
		set1stPrepare = false;
		set2ndPrepare = false;
		
	}
	public void tuneUpBtn(){
		// Tune the frequency of the radio depend on the current frequency band.
		// Increase by 10 kHz if it is AM frequency.
		// Increase by 200 kHz if it is FM frequency.
		// if the current frequency is bigger than the max, it will wrap back from the min frequency.
		if (switchAmFm == false){
			// Currently is AM frequency band.
			if(currentFreqAm + 10 <= maxFreqAM){
				// Frequency is in bound.
				currentFreqAm += 10;
			}
			else{
				// Frequency is out of bound.
				// Wrap over the min Frequency.
				currentFreqAm = minFreqAM;
			}
		}
		else{
			// Currently is FM frequency band.
			if (currentFreqFm + 200 <= maxFreqFM){
				// Frequency is in bound.
				currentFreqFm += 200;
			}
			else{
				// Frequency is out of bound.
				// Wrap over the min Frequency. 
				currentFreqFm = minFreqFM;
			}
		}
		// Reset set to un-prepare.
		setUnPrepare = true;
		set1stPrepare = false;
		set2ndPrepare = false;
	}
	public void tuneDownBtn(){
		// Tune the frequency of the radio depend on the current frequency band.
		// Decrease by 10 kHz if it is AM frequency.
		// Decrease by 200 kHz if it is FM frequency.
		// if the current frequency is smaller than the mim, it will wrap back from the max frequency.
		if(switchAmFm == false){
			// Currently is AM frequency band.
			if (currentFreqAm - 10 >= minFreqAM){
				// Frequency is in bound.
				currentFreqAm -= 10;
			}
			else{
				// Frequency is out of bound.
				// Wrap over the max frequency.
				currentFreqAm = maxFreqAM;
			}
		}
		else{
			// Currently is FM frequency band.
			if (currentFreqFm - 200 >= minFreqFM){
				// The frequency is not out of bound.
				currentFreqFm -= 200;
			}
			else{
				// The frequency is out of bound.
				// Wrap over the max frequency.
				currentFreqFm = maxFreqFM;
			}
		}
		// Reset set to un-prepare.
		setUnPrepare = true;
		set1stPrepare = false;
		set2ndPrepare = false;
	}
	public void seekUpBtn(){
		// Increase the tuned frequency until a viable station is tuned.
		if(powerButton == true){
			if(switchAmFm == false){
				// Currently in AM frequency band.
				int checkFreqAm = currentFreqAm;
				tuneUpBtn();
				while(station.lookupFreq(FreqBand.AM, currentFreqAm) == null){
					// Search for valid station.
					// No valid station then keep tuning up until reaching the starting frequency.
					tuneUpBtn();
					if(currentFreqAm == checkFreqAm){
						break;
					}
				}
			}
			else{
				// Currently in FM frequency band.
				int checkFreqFm = currentFreqFm;
				tuneUpBtn();
				while(station.lookupFreq(FreqBand.FM,  currentFreqFm) == null){
					// Search for valid station.
					// No valid station then keep tuning up until reaching the starting frequency.
					tuneUpBtn();
					if(currentFreqFm == checkFreqFm){
						break;
					}
				}
			}
		}
		
		setUnPrepare = true;
		set1stPrepare = false;
		set2ndPrepare = false;
	
	}
	
	public void seekDownBtn(){
		// tune down the frequency until find a viable frequency.
		if(powerButton == true){
			// Power is on.
			if(switchAmFm == false){
				// Currently is AM frequency band.
				int checkFreqAm = currentFreqAm;
				tuneDownBtn();
				while(station.lookupFreq(FreqBand.AM, currentFreqAm) == null){
					// Search for valid station.
					// No valid station then keep tuning up until reaching the starting frequency.
					tuneDownBtn();
					if(currentFreqAm == checkFreqAm){
						break;
					}
				}
			}
			else{
				// Currently is FM frequency band.
				int checkFreqFm = currentFreqFm;
				tuneDownBtn();
				while(station.lookupFreq(FreqBand.FM,  currentFreqFm) == null){
					// Search for valid station.
					// No valid station then keep tuning up until reaching the starting frequency.
					tuneDownBtn();
					if(currentFreqFm == checkFreqFm){
						break;
					}
				}
			}
		}
		setUnPrepare = true;
		set1stPrepare = false;
		set2ndPrepare = false;
	}
	public void setBtn(){
		// Prepare to set a primary or secondary preset frequency.
		if(powerButton == true){
			if(setUnPrepare == true){
				//prepare the radio to set a primary preset.
				setUnPrepare = false;
				set1stPrepare = true;
			}
			else if(set1stPrepare == true){
				//prepare the radio to set a secondary preset.
				set1stPrepare = false;
				set2ndPrepare = true;
			}
			else if(set2ndPrepare == true){
				// Un-prepare the radio.
				set2ndPrepare = false;
				setUnPrepare = true;
			}
		}
		
	}
	public void preset1Btn(){
		// Set the current frequency as the primary or secondary.
		// If it is unprepare, set to the current primary or secondary frequency.
		if(powerButton == true){
			// power is on. 
			if(setUnPrepare == true){
				if(switchAmFm == false){
					//Currently in AM frequency band.
					if(currentFreqAm != presetButtonPrimaryAM.get(0)){
						// set to the primary frequency.
						currentFreqAm = presetButtonPrimaryAM.get(0);
					}
					else{
						// set to the secondary frequency.
						currentFreqAm = presetButtonSecondaryAM.get(0);
					}
				}
				else{
					// Currently in FM frequency band.
					if(currentFreqFm != presetButtonPrimaryFM.get(0)){
						// set to the primary frequency.
						currentFreqFm = presetButtonPrimaryFM.get(0);
					}
					else{
						// set to the secondary frequency.
						currentFreqFm = presetButtonSecondaryFM.get(0);
					}
				}
			}
			else if(set1stPrepare == true){
				// Ready to set primary preset.
				if(switchAmFm == false){
					// Currently in AM frequency band.
					presetButtonPrimaryAM.add(0, currentFreqAm);
				}
				else{
					// Currently in FM frequency band.
					presetButtonPrimaryFM.add(0, currentFreqFm);
				}
			}
			else if(set2ndPrepare == true){
				if(switchAmFm == false){
					// Currently in AM frequency band.
					presetButtonSecondaryAM.add(0, currentFreqAm);
				}
				else{
					// Currently in FM frequency band.
					presetButtonSecondaryFM.add(0, currentFreqFm);
				}
			}
		}
		setUnPrepare = true;
		set1stPrepare = false;
		set2ndPrepare = false;
		
	}
	public void preset2Btn(){
		// Set the current frequency as the primary or secondary.
		// If it is unprepare, set to the current primary or secondary frequency.
		if(powerButton == true){
			// power is on. 
			if(setUnPrepare == true){
				if(switchAmFm == false){
					//Currently in AM frequency band.
					if(currentFreqAm != presetButtonPrimaryAM.get(1)){
						currentFreqAm = presetButtonPrimaryAM.get(1);
					}
					else{
						currentFreqAm = presetButtonSecondaryAM.get(1);
					}
				}
				else{
					// Currently in FM frequency band.
					if(currentFreqFm != presetButtonPrimaryFM.get(1)){
						currentFreqFm = presetButtonPrimaryFM.get(1);
					}
					else{
						currentFreqFm = presetButtonSecondaryFM.get(1);
					}
				}
			}
			if(set1stPrepare == true){
				// Ready to set primary preset.
				if(switchAmFm == false){
					// Currently in AM frequency band.
					presetButtonPrimaryAM.add(1, currentFreqAm);
				}
				else{
					// Currently in FM frequency band.
					presetButtonPrimaryFM.add(1, currentFreqFm);
				}
			}
			else if(set2ndPrepare == true){
				if(switchAmFm == false){
					// Currently in AM frequency band.
					presetButtonSecondaryAM.add(1, currentFreqAm);
				}
				else{
					// Currently in FM frequency band.
					presetButtonSecondaryFM.add(1, currentFreqFm);
				}
			}
		}
		setUnPrepare = true;
		set1stPrepare = false;
		set2ndPrepare = false;
	}
	public void preset3Btn(){
		// Set the current frequency as the primary or secondary.
		// If it is unprepare, set to the current primary or secondary frequency.
		if(powerButton == true){
			// power is on.
			if(setUnPrepare == true){
				if(switchAmFm == false){
					//Currently in AM frequency band.
					if(currentFreqAm != presetButtonPrimaryAM.get(2)){
						currentFreqAm = presetButtonPrimaryAM.get(2);
					}
					else{
						currentFreqAm = presetButtonSecondaryAM.get(2);
					}
				}
				else{
					// Currently in FM frequency band.
					if(currentFreqFm != presetButtonPrimaryFM.get(2)){
						currentFreqFm = presetButtonPrimaryFM.get(2);
					}
					else{
						currentFreqFm = presetButtonSecondaryFM.get(2);
					}
				}
			}
			if(set1stPrepare == true){
				// Ready to set primary preset.
				if(switchAmFm == false){
					// Currently in AM frequency band.
					presetButtonPrimaryAM.add(2, currentFreqAm);
				}
				else{
					// Currently in FM frequency band.
					presetButtonPrimaryFM.add(2, currentFreqFm);
				}
			}
			else if(set2ndPrepare == true){
				if(switchAmFm == false){
					// Currently in AM frequency band.
					presetButtonSecondaryAM.add(1, currentFreqAm);
					
				}
				else{
					// Currently in FM frequency band.
					presetButtonSecondaryFM.add(2, currentFreqFm);
					
				}
			}
		}
		setUnPrepare = true;
		set1stPrepare = false;
		set2ndPrepare = false;
	}
	public void preset4Btn(){
		// Set the current frequency as the primary or secondary.
		// If it is unprepare, set to the current primary or secondary frequency.
		if(powerButton == true){
			// power is on. 
			if(setUnPrepare == true){
				if(switchAmFm == false){
					//Currently in AM frequency band.
					if(currentFreqAm != presetButtonPrimaryAM.get(3)){
						currentFreqAm = presetButtonPrimaryAM.get(3);
					}
					else{
						currentFreqAm = presetButtonSecondaryAM.get(3);
					}
				}
				else{
					// Currently in FM frequency band.
					if(currentFreqFm != presetButtonPrimaryFM.get(3)){
						currentFreqFm = presetButtonPrimaryFM.get(3);
					}
					else{
						currentFreqFm = presetButtonSecondaryFM.get(3);
					}
				}
			}
			if(set1stPrepare == true){
				// Ready to set primary preset.
				if(switchAmFm == false){
					// Currently in AM frequency band.
					presetButtonPrimaryAM.add(3, currentFreqAm);
					
				}
				else{
					// Currently in FM frequency band.
					presetButtonPrimaryFM.add(3, currentFreqFm);
					
				}
			}
			else if(set2ndPrepare == true){
				if(switchAmFm == false){
					// Currently in AM frequency band.
					presetButtonSecondaryAM.add(3, currentFreqAm);
				}
				else{
					// Currently in FM frequency band.
					presetButtonSecondaryFM.add(3, currentFreqFm);		
				}
			}
		}
		setUnPrepare = true;
		set1stPrepare = false;
		set2ndPrepare = false;
	}
	public void preset5Btn(){
		// Set the current frequency as the primary or secondary.
		// If it is unprepare, set to the current primary or secondary frequency.
		if(powerButton == true){
			// power is on. 
			if(setUnPrepare == true){
				if(switchAmFm == false){
					//Currently in AM frequency band.
					if(currentFreqAm != presetButtonPrimaryAM.get(4)){
						currentFreqAm = presetButtonPrimaryAM.get(4);
					}
					else{
						currentFreqAm = presetButtonSecondaryAM.get(4);
					}
				}
				else{
					// Currently in FM frequency band.
					if(currentFreqFm != presetButtonPrimaryFM.get(4)){
						currentFreqFm = presetButtonPrimaryFM.get(4);
					}
					else{
						currentFreqFm = presetButtonSecondaryFM.get(4);
					}
				}
			}
			if(set1stPrepare == true){
				// Ready to set primary preset.
				if(switchAmFm == false){
					// Currently in AM frequency band.
					presetButtonPrimaryAM.add(4, currentFreqAm);
				}
				else{
					// Currently in FM frequency band.
					presetButtonPrimaryFM.add(4, currentFreqFm);
					
				}
			}
			else if(set2ndPrepare == true){
				if(switchAmFm == false){
					// Currently in AM frequency band.
					presetButtonSecondaryAM.add(4, currentFreqAm);
				}
				else{
					// Currently in FM frequency band.
					presetButtonSecondaryFM.add(4, currentFreqFm);
				}
			}	
		}
		setUnPrepare = true;
		set1stPrepare = false;
		set2ndPrepare = false;
	}
	public ArrayList<String> display(){
		// Display the information of the radio after using the methods.
		// Return an array list.
		ArrayList<String> output = new ArrayList<String>();
		if(powerButton == false){
			// Power is off. Nothing to display.
			output.add("---------------------");
			output.add("|" + "                   " + " |");
			output.add("|" + "                   " + " |");
			output.add("---------------------");
		}
		else{
			// Power is on.
			// Convert integer, double to string.
			// Convert the frequency of FM from kHz back to mHz.
			String freqAM = Integer.toString(currentFreqAm);
			double a = (double) currentFreqFm/1000;
			String freqFM = Double.toString(a);
			String volume = Integer.toString(currentVolume);
			String set = null;
			if(setUnPrepare == true){
				set = "      ";
			}
			else if(set1stPrepare == true){
				set = "  SET1";
			}
			else if(set2ndPrepare == true){
				set = "  SET2";
			}
			if(switchAmFm == false){
				// Currently is AM frequency band.
				if(station.lookupFreq(FreqBand.AM, currentFreqAm) == null){
					// Currently there is no viable station.
					output.add("---------------------"); // line 1
					output.add("|  " + "AM  " + freqAM + " ****" + "    |"); // line 2
					if(muteButton == true){
						// The mute button is on.
						output.add("|  " + "Volume: " + "--" +  set + "|"); //line 3						
					}
					else{
						// the mute button is off.
						output.add("|  " + "Volume: " + volume + set + " |"); //line 3
					}
					output.add("---------------------"); //line 4
				}
				else{
					// Currently there is a viable station to display. 
					output.add("---------------------");
					output.add("|  " + "AM  " + freqAM + " " + station.lookupFreq(FreqBand.AM, currentFreqAm) + "    |");
					if(muteButton == true){
						output.add("|  " + "Volume: " + " --" + set + " |");						
					}
					else{
						output.add("|  " + "Volume: " + volume + set + " |");
					}
					output.add("---------------------");
				}
			}
 
			else{
				// Currently is FM frequency band.
				if(station.lookupFreq(FreqBand.FM, currentFreqFm) == null){
					// No viable station to display.
					output.add("---------------------");
					output.add("|  " + "FM  " + freqFM + " ****" + "   |");
					if(muteButton == true){
						output.add("|  " + "Volume: " + " --" + set + " |");						
					}
					else{
						output.add("|  " + "Volume: " + volume + set +" |");
					}
					output.add("---------------------");
				}
				else{
					// There is a viable station to display.
					output.add("---------------------");
					output.add("|  " + "FM  " + freqFM + " " + station.lookupFreq(FreqBand.FM, currentFreqFm) + "    |");
					if(muteButton == true){
						output.add("|  " + "Volume: " + " --" + set +  " |");						
					}
					else{
						output.add("|  " + "Volume: " + volume + set +" |");
					}
					output.add("---------------------");
				}
			}
		}
		return output;
	}
}
package weapons.server;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import weapons.Weapons;


public class SaveIceBalls
{
	private File saveFile;
	private Map<String, Integer> fTFuel = new HashMap<String, Integer>();
	public static boolean hasLoaded = false;

	public SaveIceBalls(File saveLocation)
	{

		saveFile = new File(saveLocation, "IceBalls.data");
		if(!saveFile.getParentFile().exists())
			saveFile.getParentFile().mkdirs();
		load();
	}

	private void load()
	{
		boolean isName = true;
		String lastName = "";
		try{
			FileInputStream fstream = new FileInputStream(saveFile.toString());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				if(isName){
				isName = false;
				lastName = strLine;
				}
				else{
					isName = true;
					int fuel = new Integer(strLine);
					fTFuel.put(lastName, fuel);
				}
			}
			in.close();
			Weapons.iceBalls = fTFuel;
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	}





	public Map<String, Integer> getFuelLevel()
	{
		load();
		hasLoaded = true;
		return fTFuel;
	}


	public void setFuelLevel(Map<String, Integer> fuelmap)
	{
		fTFuel = fuelmap;
	}

	@SuppressWarnings("rawtypes")
	public void save()
	{
		try
		{
			File f1 = new File(saveFile.toString());
			f1.delete();
			FileWriter file = new FileWriter(saveFile);
			BufferedWriter out = new BufferedWriter(file);
			Iterator iterator = fTFuel.keySet().iterator();
			while(iterator.hasNext()){
				String tag = iterator.next().toString();
				String fuel = fTFuel.get(tag).toString();
				out.write(tag);
				out.newLine();
				out.write(fuel);
				if(iterator.hasNext()){
					out.newLine();
				}
			}
			out.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


}

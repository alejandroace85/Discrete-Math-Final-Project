import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ReadDataset {
	
	//create protected for inheritance
	protected List<double[]> features = new ArrayList<>();
	protected Map<double[] , Integer> label = new HashMap<>();
	protected static int numberOfFeatures;
	private  BufferedReader readFile;
	
	//create method list libary of labels
	public List<double[]> getFeatures()
	{
		return features;
	}
	
	//create map of labels
	public Map<double[] , Integer> getLabel() 
	{
		return label;
	}
	
	void read(String s) throws NumberFormatException, IOException {
		
		File file=new File(s);
		
	try {
		 readFile = new BufferedReader(new FileReader(file));
		String line;
		
		//read file using while loop
		while((line = readFile.readLine()) != null)
			{
			
			 String[] split = line.split(",");
            		 double[] feature = new double[split.length - 1];
            		 
            		 //number features of loops will include -1
             		numberOfFeatures = split.length-1;
             		
             		//for loop for split elements
            		for (int i = 0; i < split.length - 1; i++)
            			
               			 feature[i] = Double.parseDouble(split[i]);
            		features.add(feature);
             		String labels = split[feature.length];
            		label.put(feature , Integer.parseInt(labels));
			}
		
		//catch error exception for files
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}


	void display()
	{
		Iterator<double[]> itr = features.iterator();
		
		//Iterator<String> sitr = label.iterator();
		while(itr.hasNext())
		{ 
			double db[] = itr.next();
			for(int i = 0 ; i < 4 ; i ++)
		{
			System.out.print(db[i] + " ");
		}	
		
		}
		
	}
}

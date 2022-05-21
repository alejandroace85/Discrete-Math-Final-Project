import java.io.IOException;
import java.util.*;

//class with inheritance read data set class
public class K_Clusterer extends ReadDataset {

//main method
	public static void main(String args[]) throws IOException {
		
		//file
		ReadDataset r1 = new ReadDataset();
		r1.features.clear();
		//input scanner
		Scanner sc = new Scanner(System.in);
		//name file
		System.out.println("Enter the filename with path");
		String file = sc.next();
		r1.read(file); //load data
		int ex = 1;
		
		
		//creating do-while
		do{
			//input cluster(group)
			System.out.println("Enter the no. of clusters");
			int group = sc.nextInt();
			
			System.out.println("Enter maximum iterations");
			int max_iterations = sc.nextInt();

			
			//Hashmap to store centroids with index
			Map<Integer, double[]> center = new HashMap<>();
			// calculating initial centroids
			double[] x1 = new double[numberOfFeatures];
			int r =0;
			for (int i = 0 ; i < group; i++) {
				
				x1 = r1.features.get(r ++);
				center.put(i, x1);
				
			}
			
			//Hashmap for finding cluster indexes
			Map<double[], Integer> clusters = new HashMap<>();
			clusters = kmeans(r1.features, center, group);
			//initial cluster print
			double db[] = new double[numberOfFeatures];
		//reassiging to new cluster (group) using nested loop
			for (int i = 0 ; i < max_iterations; i++) {
				
				for (int j = 0 ; j < group; j ++) {
					
					List<double[]> list = new ArrayList<>();
					
					for (double[] key : clusters.keySet()) {
						
						if (clusters.get(key)==j) {
							
							list.add(key);
						
					}
						
				}//end for loop cluster
					
					//calculator average in center update
					db = centerCalculator(list);
					center.put(j, db);
				
				}
				//clear cluster to clean output
				clusters.clear();
				
				clusters = kmeans(r1.features, center, group);
				
			}//end loop iteraton
			
			//final cluster print line (optional for purpose check)
			System.out.println("\nFinal Clustering of Data file");
			System.out.println("Feature1\tFeature2\tFeature3\tFeature4\tCluster");
			
			for (double[] key : clusters.keySet()) {
				for (int i = 0; i < key.length; i++) {
					System.out.print(key[i] + "\t \t");
				}
				System.out.print(clusters.get(key) + "\n");
			}
			
			
			//calulate Accuracy
			int accrate = 0 , total = 0;
			for (double[] key : clusters.keySet()) {
				if (clusters.get(key) + 1 == r1.getLabel().get(key)) {
					
					accrate ++;
				}
				
				total ++;
			}
			
			
			double accuracy = (double)accrate / (double)total * 100;
			
			
			double distance = 0;
			
			for(int i = 0 ; i < group ; i++){
				
				double sse = 0;
				for (double[] key : clusters.keySet()) {
					if (clusters.get(key)==i) {
						Distance.manhattanDistance(key, center.get(i));
					}
					
					sse++;
				}
				distance = sse;
			}
		
			System.out.println("Iterations: " + max_iterations);
			System.out.println("Count of Clusters: " + group);
			System.out.println("Distance: " + distance);
			System.out.println("Accuracy: " + accuracy + "%");
			System.out.println("Press 1 if you want to continue else press 0 to exit..................................");
			
			
			ex=sc.nextInt();
			
		}while(ex==1);
	}
	
	//method to calculate centroids
	public static double[] centerCalculator(List<double[]> a) {
		
		int count = 0;
		
		double sum=0.0;
		double[] average = new double[ReadDataset.numberOfFeatures];
		for (int i = 0; i < ReadDataset.numberOfFeatures; i++) {
			sum=0.0;
			count = 0;
			for(double[] x:a){
				count++;
				
				sum = sum + x[i];
			}
			
			average[i] = sum / count;
		}
		return average;

	}

	//method for putting features to clusters and reassignment of clusters.
	public static Map<double[], Integer> kmeans(List<double[]> features,Map<Integer, double[]> centroids, int k) {

		Map<double[], Integer> clusters = new HashMap<>();
		int k1 = 0;
		double dist=0.0;

		for( double[] x : features) {
			double minimum = 999999.0;
			for (int j = 0 ; j < k ; j ++) {
				
					dist = Distance.manhattanDistance(centroids.get(j), x);
			
				if (dist < minimum) {
					minimum = dist;
					k1 = j;
				}
			
			}
			clusters.put(x, k1);
		}
		
		return clusters;

	}

}

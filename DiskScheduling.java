import java.util.LinkedHashMap;
import java.util.Arrays;
public class DiskScheduling{

	private final int val1 = 0;
	private final int val2 = 4999;
	boolean [] visited;
	
	//LinkedHashMap maintains key:value pairs based on the order they were inserted in the hashmap
	private LinkedHashMap<Integer, Integer> map = new LinkedHashMap<Integer,Integer>();

	DiskScheduling(int capacity){ //default constructor maintaining the size of hashmap
		for(int i = 0;i<capacity;i++){
			int range = 1000-1+1;
			int x = (int)(Math.random() *range)+1; //generating randomnumbers from one to 1000
			map.put(i,x); //inserting numbers in hashmap
			visited[i] = false;
		}
	
	}
	public int absoluteValDiff(int a, int b) { //calculates the absolute value of difference between 2 numbers
		return Math.abs(a-b);
	}
	public void FCFS(int head){ //First Come First Serve
		int diff = 0; //variable to store difference between each value in hashmap
		int num_cylinders;  //store the number of cylinders
		int first = map.get(0); //retrieving the first value
		head = absoluteValDiff(head,first); //calculating difference between head and first value
		num_cylinders = head; //storing head in num_cylinders
		
		//for(Integer key : map.values()){ 	
		for(int i = 0; i<map.size()-1;i++) { //iterating through size of map and adding differences of values
			diff=absoluteValDiff(map.get(i),map.get(i+1));
			num_cylinders+=diff;		
		}
		System.out.println(num_cylinders + " cylinders");
	}
		
	//public int getMinDiff(int x) {
		
	//}
	//logic for shortest seek time first incomplete
	public void SSTF(int head) {
		int diff = 0;
		int num_cylinders = 0;
		int min =Integer.MIN_VALUE;
		int [] temp = new int [map.size()];
		for(int i = 0;i<temp.length;i++) {
			temp[i] = absoluteValDiff(head,map.get(i));
		}
		Arrays.sort(temp);
		num_cylinders = temp[0];
		//head + num_cylinder
		for(int i =0;i<map.size();i++) {
			if(min > temp[i] && !visited[i]) {
				min = temp[i];
				//visited[i] = true;
				//diff = min;
				//num_cylinders+=diff;

			}
		}
		
	}
	

} 

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
public class DiskSchedulingAlgorithms{

	private final int val1 = 0;
	private final int val2 = 4999;

	//LinkedHashMap maintains key:value pairs based on the order they were inserted in the hashmap
	private ArrayList <Integer> list = new ArrayList<Integer>();
	DiskSchedulingAlgorithms(int capacity){ //default constructor maintaining the size of hashmap
		for(int i = 0;i<capacity;i++){
			int range = 1000-1+1;
			int x = (int)(Math.random() *range)+1; //generating randomnumbers from one to 1000
			list.add(x); //inserting numbers in hashmap
		}
		/*list.add(98);
		list.add(183);
		list.add(37);
		list.add(122);
		list.add(14);
		list.add(124);
		list.add(65);
		list.add(67);*/

	}
	public int absoluteValDiff(int a, int b) { //calculates the absolute value of difference between 2 numbers
		return Math.abs(a-b);
	}
	public void FCFS(int head){ //First Come First Serve
		int diff = 0; //variable to store difference between each value in hashmap
		int num_cylinders;  //store the number of cylinders
		int first = list.get(0); //retrieving the first value
		head = absoluteValDiff(head,first); //calculating difference between head and first value
		num_cylinders = head; //storing head in num_cylinders

		for(int i = 0; i<list.size()-1;i++) { //iterating through size of map and adding differences of values
			diff=absoluteValDiff(list.get(i),list.get(i+1));
			num_cylinders+=diff;		
		}
		System.out.println(num_cylinders + " cylinders");
	}
	
	public void SSTF(int head) {
		int num_cylinders = 0;
		while(!list.isEmpty()) { //while list is full
			
			int closest = findClosestDiff(head); //find the closest next to head
			num_cylinders+=absoluteValDiff(head,closest); //storing distance between head and closest
			head=closest; //update head to be closest
			list.remove(new Integer(closest)); //removing previous closest from the list
			
		}
		
		System.out.println(num_cylinders + " cylinders");
		
	}
	public void SCAN(int head) {
		
		list.add(head); //add head to the list
		int num_cylinders =0 ;
		int headloc; //location of head
		Collections.sort(list); //sorting the list
		int max = list.get(list.size()-1); //maximum element in list
		
		num_cylinders = head+max; //head movement is head plus max element
		
		
		
		System.out.println(num_cylinders + " cylinders");
	}
	
	
	//source for help with this method :
	//https://beginnersbook.com/2014/06/java-iterator-with-examples/
	public int findClosestDiff(int head) { //finds the closest location to the head with shortest difference distance
		
		Iterator<Integer> iterate = list.iterator(); //Iterator for iterating (looping) Arraylist collection class
		int nearest = list.get(0); //setting nearest to be first element of list
		while(iterate.hasNext()) { //checking while they is a consecutive element
			int temp = iterate.next(); //set next element to temp
			//if difference between head and next element is less than the different between head and first
			if(absoluteValDiff(head,temp) < absoluteValDiff(head,nearest)) {
				nearest = temp; //set new nearest to be next element
			}
		}
		return nearest;
		
	}
}

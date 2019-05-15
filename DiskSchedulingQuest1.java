/*
Group Names:
	Peter Farquharson
	Davian Farquharson
	Mahboba Mim	
	Jose Cantres
	
	Cited Source that helped with some implementation : https://github.com/topics/disk-scheduling
*/


import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Collections;



public class DiskSchedulingQuest1{
	
	public static void main(String [] args) {
		
		int x = Integer.parseInt(args[0]);
		
		DiskSchedulingAlgorithms dsa = new DiskSchedulingAlgorithms();
		 dsa.FCFS(x);
		 dsa.SCAN(x);
		 dsa.CSCAN(x);
		 dsa.LOOK(x);
		 dsa.CLOOK(x);
		 dsa.SSTF(x);

	}
	
}

class DiskSchedulingAlgorithms{

	private  int max = 4999;
	//ArrayList maintaining values based on the order they were inserted
	private ArrayList <Integer> list = new ArrayList<Integer>();
	DiskSchedulingAlgorithms(){ //default constructor maintaining the size of hashmap
		for(int i = 0;i<10;i++){
			int range = 1000-1+1;
			int x = (int)(Math.random() *range)+1; //generating random numbers from one to 1000
			list.add(x); //inserting numbers in hashmap
		}
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
		System.out.println("FCFS: Head movement of " + num_cylinders + " cylinders");
	}
	
	public void SSTF(int head) {
		
		
		int num_cylinders = 0;
		while(!list.isEmpty()) { //while list is full
			
			int closest = findClosestDiff(head); //find the closest next to head
			num_cylinders+=absoluteValDiff(head,closest); //storing distance between head and closest
			head=closest; //update head to be closest
			list.remove(new Integer(closest)); //removing previous closest from the list
			
		}
		
		System.out.println("SSTF: Head movement of " + num_cylinders + " cylinders");
		
	
		
	
	}
	
	public void SCAN(int head) {
				
		list.add(head); //add head to the list
		int num_cylinders =0 ;
		Collections.sort(list); //sorting the list
		int max = list.get(list.size()-1); //maximum element in list
		
		num_cylinders = head+max; //head movement is head plus max element
		
		
		
		System.out.println("SCAN:Head movement of " + num_cylinders + " cylinders");
	}



	
	public void CLOOK(int head) {
		int num_cylinders = 0;
		
		for(int i = 0; i<list.size();i++) {
			num_cylinders+=absoluteValDiff(head,list.get(i));
			head = list.get(i);
		}
		
		System.out.println("CLOOK :Head movement of " + num_cylinders +  " number of cylinders");
	}
	
	//Had troubles with implementation
	public void LOOK(int head) {
		int num_cylinders = 0;
		TreeMap<Integer, Integer> map = new TreeMap<Integer,Integer>();
		for(int i = 0; i<list.size();i++){
		   map.put(i, list.get(i)); //inserting values from the list inside the map
		}
		//get values that are less than head
		SortedMap<Integer,Integer> less = map.headMap(head,false);
		
		//get values that are greater than head
		SortedMap<Integer, Integer> great = map.tailMap(head,false);
		
		int direction;
		
		if(less.size()<great.size()) {
			while(!less.isEmpty()) {
				direction = less.lastKey();
				num_cylinders+=absoluteValDiff(head,direction);
				head = direction;
				less.remove(direction);
			}
			while(!great.isEmpty()) {
				direction = great.firstKey();
				num_cylinders+=absoluteValDiff(head,direction);
				head = direction;
				great.remove(direction);
			}
		}else {
			while(!great.isEmpty()){
				direction = great.firstKey();
				num_cylinders+=absoluteValDiff(head,direction);
				head = direction;
				great.remove(direction);
			}
			
			while(!less.isEmpty()) {
				direction = less.lastKey();
				num_cylinders+=absoluteValDiff(head,direction);
				head = direction;
				less.remove(direction);
			}
		}
		
		
		System.out.println("LOOK :Head movement of " + num_cylinders +  " number of cylinders");

	}
	//Had troubles with implementation
	public void CSCAN(int head) {
		int num_cylinders = 0;

		//Treemap
		TreeMap<Integer, Integer> map = new TreeMap<Integer,Integer>();
		for(int i = 0; i<list.size();i++){
		   map.put(i, list.get(i)); //inserting values from the list inside the map
		}
		//get values that are less than head
		SortedMap<Integer,Integer> less = map.headMap(head,false);
		
		//get values that are greater than head
		SortedMap<Integer, Integer> great = map.tailMap(head,false);
		
		//next
		int direction;
		//compare the greater map to see which has most ref
		
		if(less.size() > great.size()) {
			//those less than starting location
			while(!less.isEmpty()) {
				direction = less.lastKey();
				num_cylinders +=absoluteValDiff(head,direction);
				head =direction;
				less.remove(direction);
				
			}
			//go to zero
			num_cylinders+=head;
			head = 0;
			//go to max
			num_cylinders+=absoluteValDiff(head,max);
			head = max;
			//compare greater than starting
			while(!great.isEmpty()) {
				direction = great.lastKey();
				num_cylinders+=absoluteValDiff(head,direction);
				head = direction;
				great.remove(direction);
			}
			
		}else {
			while(!great.isEmpty()) {
				direction = great.firstKey();
				num_cylinders+= absoluteValDiff(head,direction);
				head = direction;
				great.remove(direction);
			}
			num_cylinders+=absoluteValDiff(head,max);
			head = max;
			num_cylinders+=head;
			head = 0;
			while(!less.isEmpty()) {
				direction = less.firstKey();
				num_cylinders+=absoluteValDiff(head,direction);
				head = direction;
				less.remove(direction);
			}
		}
		
		System.out.println("CSCAN: Head movement of " +num_cylinders + " cylinders");
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

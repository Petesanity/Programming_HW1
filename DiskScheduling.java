/*
Group Names:
	Peter Farquharson
	Davian Farquharson
	Mahboba Mim	
	Jose Cantres
*/


import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Collections;



public class DiskScheduling{
	
	public static void main(String [] args) {
		
		int x = Integer.parseInt(args[0]); ////command line input from user parsed as Integer
		DiskSchedulingAlgorithms dsa = new DiskSchedulingAlgorithms(10);
		dsa.FCFS(x);
		dsa.SSTF(x);
		//dsa.SCAN(x);
		//dsa.CSCAN(x);
	//	dsa.LOOK(x);
	}
	
}



class DiskSchedulingAlgorithms{

	private  int max = 199;
	//ArrayList maintaining values based on the order they were inserted
	private ArrayList <Integer> list = new ArrayList<Integer>();
	DiskSchedulingAlgorithms(int capacity){ //default constructor maintaining the size of hashmap
		for(int i = 0;i<capacity;i++){
			int range = 1000-1+1;
			int x = (int)(Math.random() *range)+1; //generating randomnumbers from one to 1000
			list.add(x); //inserting numbers in hashmap
		}
	/*	list.add(98);
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
		System.out.println("Head movement of " + num_cylinders + " cylinders");
	}
	
	public void SSTF(int head) {
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		
		Collections.copy(list2, list);
		int num_cylinders = 0;
		while(!list2.isEmpty()) { //while list is full
			
			int closest = findClosestDiff(head); //find the closest next to head
			num_cylinders+=absoluteValDiff(head,closest); //storing distance between head and closest
			head=closest; //update head to be closest
			list2.remove(closest); //removing previous closest from the list
			
		}
		
		System.out.println("Head movement of "+ num_cylinders + " cylinders");
		
	}
	public void SCAN(int head) {
				
		list.add(head); //add head to the list
		int num_cylinders =0 ;
		Collections.sort(list); //sorting the list
		int max = list.get(list.size()-1); //maximum element in list
		
		num_cylinders = head+max; //head movement is head plus max element
		
		
		
		System.out.println("Head movement of " + num_cylinders + " cylinders");
	}



	/*	

	public void CSCAN(int head) {
		list.add(head);
		Collections.sort(list);
		int curr_val = 0;
		int first = head;
		int diff = 0;
		int num_cylinders = 0;
		
		for(int i =first+1 ; i < list.size(); i++) {

			curr_val = list.get(i);
			diff = absoluteValDiff(head, curr_val);
			num_cylinders += diff;
			head = curr_val;

		}
		num_cylinders += max - head;
		head = 0;
		num_cylinders += 199;

		for(int i = 0; i < first; i++) {

			curr_val = list.get(i)-1;
			diff = absoluteValDiff(curr_val,head);
			num_cylinders += diff;
			head = curr_val;

		}
		
		System.out.println(num_cylinders + " cylinders");

	}
	*/
	
	public void CLOOK(int head) {
		int num_cylinders = 0;
		
		
		
		System.out.println("Head movement of " + num_cylinders +  " number of cylinders");
	}
	
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
		
		
		System.out.println("Head movement of " + num_cylinders +  " number of cylinders");

	}
	public void CSCAN(int head) {
		int num_cylinders = 0;
	//	int start = head;
		//new arraylist
		//copying from old arraylist to new
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

package hw1_Ron_hasan;
import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Stack;


public class AStarAndBFS {


	private ArrayList<int[][]> visited;// arraylist for saving the visited matrixes
	private static HashMap<int[][], int[][]> fathers = new HashMap<int[][], int[][]>(); // for saving the father of each point to give us the path of the solution
	static int i=0;
	public AStarAndBFS() { 
		this.visited = new ArrayList<int[][]>();
	}
	
	public ArrayList<int[][]> startAStar(int[][] start ,int[][] goal, boolean isAStar) {
	
		PriorityQueue<int[][]> pQueue= new PriorityQueue<int[][]>(new Comparator<int[][]>() {		
			@Override
			public int compare(int[][] o1, int[][] o2) {//to sort the matrixes in the priority queue by Manhattan distance and there g
				if(isAStar==true) 
				{
				return ((manhatanForAllMat(o1,o1.length)+findThePath(start,o1).size())-(manhatanForAllMat(o2,o2.length)+findThePath(start,o2).size()));
				}
				else {
					return (findThePath(start,o1).size())-findThePath(start,o2).size();
				}
			}
			
		});
	
		int cost =0; //the number of actions from the inihiliate state to goal state
		int G = 0;
		int expantions=0;
		pQueue.add(start); 
		while(!pQueue.isEmpty()) //while our pQueue have elements
		{
			
			int[][] p= pQueue.poll(); // put in mat p the mat in the
			printMat(p);
			System.out.println("");
			cost ++; // we need to add 1 to our cost
			
			if(equal(p,goal))  //if we polled the goal from the queue
			{
				System.out.println("the number of the nodes that we expantion is:");
				System.out.println(expantions);				
				return findThePath(start, p); //call to function that return the path from initial state to goal state
			}
			
			visited.add(p); // add to data structure of visited state cause we do not want to go back to this state again 
			G = findThePath(start,p).size(); //calculate current G
			ArrayList<int[][]> toVistited=successor(p,isAStar,G);//add all the matrixes that returns from the successor function to an arrayList
			expantions++; // we need to add 1 to our number of expantions
			
			boolean flag1= true; //boolean variable that some mat that are allready visited
			int help = 0;
			int len = toVistited.size();
			
			for (int c = 0 ; c< visited.size() ; c++)
			{
				if (flag1==true) 
				{
					for(int i = 0 ;i < len; i++)
					{
						if (equal(visited.get(c),toVistited.get(i))) 
						{//check if we already visited the matrixes that the successor returns
							flag1= false;
							help = i;
						}
					}
									
					if (flag1 == false) 
					{
						//if we find mat we remove that mat from data structure toVistited
						toVistited.remove(toVistited.get(help));
						//the len of update data structure minus 1
						len--;
					}
				}
				//update the boolean variable before start of another iteration
				flag1 = true;
			}
			
			//add all matrix that are no already exist to the relevant data structures
			for(int[][] x : toVistited) 
			{				
				fathers.put(x, p);
				pQueue.add(x); 
				visited.add(x);
			}
		}
				
		return null;
	}
	
	//function that get initial state and goal state , and return the path
	public ArrayList<int[][]> findThePath(int[][] start,int[][] goal)//function that find the path from the start point to the goal
	{
	
		ArrayList<int[][]> path = new ArrayList<int[][]>();
		Stack<int[][]> stack = new Stack<int[][]>();
		int[][] help;
		help = goal;
		stack.add(goal);
		//while you do not reach your initiale state , add the right state to the stack - LIFO
		while(!help.equals(start) ) 
		{
			stack.add(fathers.get(help));
			help = fathers.get(help);//goes a step back every time
			 
		}

		//now move all the elements from the stack to the arraylist path 
		int len = stack.size();
		for(int i=0 ; i<len ;i++) 
		{
			path.add(stack.pop());
		}
		return path;	

	}
	 
	//function that get state , and return all there childs states
	public ArrayList<int[][]> successor (int[][] s, boolean isAStar,int G)
	{
		// returns an arrayList of int[][] that contains all the abilities to get from one matrix(12 matrixes)
		ArrayList<int[][]> toreturn = new ArrayList<int[][]>();
		int[][] M = s;
		int help[][] = M;
		Point to = new Point();
		Point from = new Point();
		boolean flag = true;
		for(int i= 0 ; i < s.length;i++) 
		{
			for(int j= 0 ; j < s.length;j++) 
			{
				//swap from right to left
				if(j>0) 
				{
					from.setC(i);
					from.setR(j);
					to.setC(i);
					to.setR(j-1);
					int[][] res = swap(help,from,to,isAStar,G);
					for (int[][] s3 : toreturn) {
						if (equal(res,s3)) {
							flag = false;
						}
					}
					if (flag == true)
						toreturn.add(res);
				}
				flag = true;
				//swap from down to up
				if(i>0) 
				{
					from.setC(i);
					from.setR(j);
					to.setC(i-1);
					to.setR(j);
					int[][] res = swap(help,from,to,isAStar,G);
					for (int[][] s3 : toreturn) {
						if (equal(res,s3)) {
							flag = false;
						}
					}
					if (flag == true)
						toreturn.add(res);
				}
				//swap from up to down
                flag = true;
				if(i<s.length-1) 
				{
					from.setC(i);
					from.setR(j);
					to.setC(i+1);
					to.setR(j);
					int[][] res = swap(help,from,to,isAStar,G);
					for (int[][] s3 : toreturn) {
						if (equal(res,s3)) {
							flag = false;
						}
					}
					if (flag == true)
					{
						toreturn.add(res);
					}	
				}
				flag = true;
				//swap from right to left
				if(j<s.length-1) 
				{
					from.setC(i);
					from.setR(j);
					to.setC(i);
					to.setR(j+1);
					int[][] res = swap(help,from,to,isAStar,G);
					for (int[][] s3 : toreturn) {
						if (equal(res,s3)) {
							flag = false;
						}
					}
					if (flag == true)
						toreturn.add(res);
				}	
			} 
		}
		//return data structure with all the childs of the current state
		return toreturn;
	}
	
	//function that get a matrix an 2 point and swap
	public int[][] swap (int[][] matrix,Point from, Point to,boolean isAStar,int g)//returns the result matrix that we get after swapping two elements
	{	
		int temp;
		temp=matrix[from.getC()][from.getR()];
		matrix[from.getC()][from.getR()] =matrix[to.getC()][to.getR()]; 
		matrix[to.getC()][to.getR()]=temp;
		
		int [][] temp1 = new int[matrix.length][matrix.length];
		for (int i = 0 ; i < matrix.length ; i ++) 
		{
			for (int j = 0 ; j < matrix.length ; j ++) 
			{
				temp1[i][j]= matrix[i][j];
			}
		}
			 
		temp=matrix[from.getC()][from.getR()];
		matrix[from.getC()][from.getR()] =matrix[to.getC()][to.getR()];
		matrix[to.getC()][to.getR()]=temp;
		return temp1;
	}
	
	//function that check if 2 matrix are equals
	public boolean equal (int[][] s, int[][] k)
	{ 
		int i,j;
		for(i=0;i<s.length;i++)
		{
			for(j=0;j<s.length;j++)
			{
				
				if(s[i][j]!=k[i][j])
				{
					return false;
				}
			} 
		}
		return true;
	}
	
	//function that find the manhatan distance for all mat elemants
	public int manhatanForAllMat(int[][] matrix, int size )//calculate Manhattan distance from the current matrix to the goal matrix
	{
		int i,j,sumOfmanhatanForAllMat=0;
		for(i=0;i<size;i++)
		{
			for(j=0;j<size;j++)
			{
				sumOfmanhatanForAllMat+=(Math.abs(j-((matrix[i][j]-1)%size))+ Math.abs(i-((matrix[i][j]-1)/size)));
			}
		}
		return sumOfmanhatanForAllMat;
	}
	
	
	public static void printMat(int matIntegerValues[][])// function that prints a matrix
	{
		int i,j;
		for(i=0;i<matIntegerValues.length;i++)
		{
			for(j=0;j<matIntegerValues.length;j++)
			{
				System.out.print(matIntegerValues[i][j]+" ");
			}
			System.out.println();
		}
	}
}
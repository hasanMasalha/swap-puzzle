package hw1_Ron_hasan;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;



public class iddfs {
	
    private static boolean bottomReached = false; //to check if we have reached the bottom.
    private static Queue<int[][]> q = new LinkedList<int[][]>();
	private static HashMap<int[][], int[][]> fathers = new HashMap<int[][], int[][]>(); // for saving the father of each point to give us the path of the solution
	
  
     
    public static int[][] iterativeDeepeningDFS(int[][] start, int[][] goal) {
        int depth = 1;
          int expantions= 0;
        while (!bottomReached) {
            bottomReached = true; 
            int[][] result = iterativeDeepeningDFS(start, goal, 0, depth,expantions);
            if (result != null) {
            	//found the goal while doing DFS 
                return result;
            }

            // didnt find the goal and we are not in the maximum depth
            depth *= 2;
            System.out.println("The depth is : " + depth);
        }

        //we reached the maximum depth but we didnt found the goal
        return null;
    }

    private static int[][] iterativeDeepeningDFS(int[][] state, int[][] target, int currentDepth, int maxDepth,int expantions) {
    	if (equal(state,target)) {//check if we found the target
           
            System.out.println("Found the node we're looking for!");
            System.out.println("the expantions are :");
            System.out.println(expantions);
            return state;
        }

        if (currentDepth == maxDepth) {
            // reached the end for this depth...
            if (!equal(state,target)) {
                //we did not reached the maximum depth
                bottomReached = false;
            }
            return null;
        }

        // Recurse with all children
        expantions ++ ;
        ArrayList<int[][]> res = successor(state ,false);
         
        for (int i = 0; i < res.size(); i++) {
			fathers.put(res.get(i), state);
            int[][] result = iterativeDeepeningDFS(res.get(i), target, currentDepth + 1, maxDepth,expantions);
            if (result != null) {
                return result;
            }
        }

        // checked all the children but did not find the goal
        return null;
    }


    public static ArrayList<int[][]> successor (int[][] s, boolean isAStar)
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
					int[][] res = swap(help,from,to,isAStar);
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
					int[][] res = swap(help,from,to,isAStar);
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
					int[][] res = swap(help,from,to,isAStar);
					for (int[][] s3 : toreturn) {
						if (equal(res,s3)) {
							flag = false;
						}
					}
					if (flag == true)
						toreturn.add(res);

					
					
				}
				flag = true;
				//swap from right to left
				if(j<s.length-1) 
				{
					from.setC(i);
					from.setR(j);
					to.setC(i);
					to.setR(j+1);
					int[][] res = swap(help,from,to,isAStar);
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
	
		return toreturn;
	}

	    	
	        public static int[][] swap (int[][] matrix,Point from, Point to,boolean isAStar)//returns the result matrix that we get after swapping two elements
	    	{	
	    		int temp;
	    		temp=matrix[from.getC()][from.getR()];
	    		matrix[from.getC()][from.getR()] =matrix[to.getC()][to.getR()]; 
	    		matrix[to.getC()][to.getR()]=temp;
	    		
	    		int [][] temp1 = new int[matrix.length][matrix.length];
	    		for (int i = 0 ; i < matrix.length ; i ++) {
	    			for (int j = 0 ; j < matrix.length ; j ++) {
	    				temp1[i][j]= matrix[i][j];
	    		}
	    	}
	    		
	    		 
	    		temp=matrix[from.getC()][from.getR()];
	    		matrix[from.getC()][from.getR()] =matrix[to.getC()][to.getR()];
	    		matrix[to.getC()][to.getR()]=temp;
//	    	

	    		return temp1;
	    	}
			public static Queue<int[][]> getQ() {
				return q;
			}

			public static void setQ(Queue<int[][]> q) {
				iddfs.q = q;
			}
			public static boolean equal (int[][] s, int[][] k)
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
			
		
			public ArrayList<int[][]> findThePath(int[][] start,int[][] goal)//function that find the path from the start point to the goal
			{
		
				ArrayList<int[][]> path = new ArrayList<int[][]>();
				Stack<int[][]> stack = new Stack<int[][]>();
				int[][] help;
				help = goal;
				stack.add(goal);
				while(!help.equals(start) ) 
				{
					stack.add(fathers.get(help));
					help = fathers.get(help);//goes a step back every time
					 
				}


				int len = stack.size();
				for(int i=0 ; i<len ;i++) 
				{
					path.add(stack.pop());
				}
				return path;	

			}
}

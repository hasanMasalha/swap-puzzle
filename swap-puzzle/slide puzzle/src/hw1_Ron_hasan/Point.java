package hw1_Ron_hasan;

public class Point {
	private int r;
	private int c;
	
	public Point(int r, int c) {
		this.r = r;
		this.c = c;
	}
	
	public Point() {
		this.r = 0;
		this.c = 0;
	}
	
	public Point(Point other)
	{
		this.r=other.r;
		this.c=other.c;
	}
	
	public void PointByValue(int value,int matSize) {
		r= (value-1) / matSize;
		c= (value-1) % matSize;	
	}

	/*
	 * def calculateManhattan(initial_state,size):
	    initial_config = initial_state
	    manDict = 0
	    for i,item in enumerate(initial_config):
	        prev_row,prev_col = int(i/ size) , i % size
	        goal_row,goal_col = int(item /size),item % size
	        manDict += abs(prev_row-goal_row) + abs(prev_col - goal_col)
	    return manDict

	initial_state = [1,5,3,4,2,6,7,8,0]
	goal_state = [0,1,2,3,4,5,6,7,8]
	res = calculateManhattan(initial_state,3)
	print(res)
	
	 * 
	 * 
	 */
	
	
	
	
	
	
	
	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}
	
	

}

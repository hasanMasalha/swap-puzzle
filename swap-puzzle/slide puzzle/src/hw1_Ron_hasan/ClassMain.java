package hw1_Ron_hasan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class ClassMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String solve = "solve";
		if(args[0].equals(solve)) {//if we get the word solve in the first argument
	 //   Scanner scan = new Scanner(System.in);  // Create a Scanner object

		String fileName =  args[1];//the file name
		
		String algorithm =  args[2];//witch algorithm we want to use				
		String matValuesByString = readFileAndConvertItToString(fileName);
		
		int matIntegarValues[][] = convertStringToMatOfInt(matValuesByString);
	
		printMat(matIntegarValues);
		int[][] goalStateIntegetrs = new int[matIntegarValues.length][matIntegarValues.length];
		int index= 1;
		for(int i = 0 ; i < matIntegarValues.length; i++) {
			for(int j = 0 ; j < matIntegarValues.length; j++) {
				goalStateIntegetrs[i][j]=index;
				index ++;
		}
		}
		switch( algorithm) {
		case "bfs" :
		ArrayList<int[][]> returnValueStates = new ArrayList<int[][]>();
		AStarAndBFS ourAlgorithm = new AStarAndBFS();
	
		returnValueStates = ourAlgorithm.startAStar(matIntegarValues, goalStateIntegetrs, false);		
		System.out.println("the cost is :");
		System.out.println(returnValueStates.size()-1); 
		if (returnValueStates != null) {
		for(int k = 0 ; k <returnValueStates.size();k++ ) {
	
			printMat(returnValueStates.get(k));
			System.out.println("");
		}
			
		}
		break ;
		case "astar" : {
			ArrayList<int[][]> returnValueStates1 = new ArrayList<int[][]>();
			AStarAndBFS ourAlgorithm1 = new AStarAndBFS();
		
			returnValueStates1 = ourAlgorithm1.startAStar(matIntegarValues, goalStateIntegetrs, true);		
			System.out.println("the cost is :");
			System.out.println(returnValueStates1.size()-1); 
			if (returnValueStates1 != null) {
			for(int k = 0 ; k <returnValueStates1.size();k++ ) {
		
				printMat(returnValueStates1.get(k));
				System.out.println("");
			}
				
			}
		}
			break ;
			case "id":{
				iddfs n = new iddfs();
			    int[][] result =  iddfs.iterativeDeepeningDFS(matIntegarValues, goalStateIntegetrs) ;
			   ArrayList<int[][]> finalRes = n.findThePath(matIntegarValues, result);
			   int counter= 0;
			   for(int i1=0;i1<finalRes.size();i1++) {
				   printMat(finalRes.get(i1));
				   System.out.println();
				   counter ++;
			   }
			   System.out.println("the size is:");
			   System.out.println(counter-1);
				
			}
			
		}
		}else {
			System.out.println("ERROR please enter the word solve first");
		}
		
		}

		
		
			
		
		
		
		
		
	public static String readFileAndConvertItToString(String path)//return the String that in the file 
	{
			
		File file = new File(path);
		String str ="";
		Scanner scan = null;
		
		try {
			scan = new Scanner(file);
			while(scan.hasNextLine())
			{
				str+=scan.nextLine();
				str+=" ";

			}
		} 
		
		catch (FileNotFoundException e) {
			// TODO: handle exception
			e.getStackTrace();
		}
		finally 
		{
			if(scan!=null)
				scan.close();
		}
		
		return str;
	}
	
	public static int[][] convertStringToMatOfInt(String matValuesByString) //converting the text that we got from the file to a matrix
	{
		int i ,j,k=0 , length , matSize , convertedNum =0;
		
		String[] numbesByStrings = matValuesByString.split(" ");
		length = numbesByStrings.length;
		
		matSize=(int) Math.sqrt(length);
		System.out.println("matSize= " + matSize);
		
		int matIntegarValues[][] = new int[matSize][matSize];
		
		for(i=0;i<matSize;i++)
		{
			for(j=0;j<matSize;j++)
			{
				convertedNum = Integer.parseInt("" +numbesByStrings[k++]);
				matIntegarValues[i][j] = convertedNum;
			}
		}
		
		return matIntegarValues;
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
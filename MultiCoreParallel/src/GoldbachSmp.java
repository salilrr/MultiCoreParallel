import java.math.BigInteger;
import edu.rit.pj2.Loop;
import edu.rit.pj2.Task;


/* * 
 * Class GoldbachSmp is a parallel program that calculates the prime factors of all even Numbers in a given range.
 * (i.e. Verifying Goldbach's Conjecture for all even integers in a given range.)
 * The program takes two command line arguments which are the lower-bound and upper-bound of the range.
 * 
 * The program finds i = p + q(i is an even number and p and q are its prime factors.)
 * For every iteration the program attempts to find the smallest p.
 * Finally, the program prints i = p + q such that p is the largest among all the numbers evaluated.
 * If more than one integer i evaluates the same p,we consider the maximum integer(i).
 * 
 * Usage: java pj2 GoldbachSmp <lb> <ub>
 * 		: java pj2 cores=<K> GoldbachSmp <lb> <ub>(Explicitly specifying number of cores.)
 * 
 * @author:Salil Rajadhyaksha
 * @version:20-September-2015.
 *  
 */

public class GoldbachSmp extends Task{

	//lower bound and Upper bound variables.
	BigInteger lb;
	BigInteger ub;
	
	/*
	 * main method of the program.
	 * Execution of the entire program is carried out in this method.
	 * @param: args[0]:Lower bound of the range.
	 * 		   args[1]:Upper bound of the range.
	 */
	@Override
	public void main(String[] args) throws Exception {

		//check if correct number of arguments have been provided.
		if(args.length<2)
		{
			System.out.println("Invalid number of arguments.Number of arguments needed 2.");
			return;
		}
		//checks if the arguments contain any other characters other than numbers.
		try
		{
		lb=new BigInteger(args[0]);
		ub=new BigInteger(args[1]);
		}
		catch(NumberFormatException e)
		{
			System.out.println("The application expects numbers as commanad line arguments");
			return;
		}
		
		//checks if the lower bound is greater than 2.
		if(lb.compareTo(new BigInteger("2"))<=0)
		{
			System.out.println("Lower Bound should be an even integer greater than 2.");
			return;
		}
		
		//checks if the arguments contain any other characters other than numbers.
		boolean isLbEven=checkisEven(lb);
		boolean isUbEven=checkisEven(ub);
		
		if(isLbEven==false)
		{
			System.out.println("LowerBound should be even");
			return;
		}
		
		if(isUbEven==false)
		{
			System.out.println("UpperBound should be even");
			return;
		}
		//checking if upper bound is greater than lower bound.
		if(lb.compareTo(ub)==1)
		{
			System.out.println("Invalid arguments argument:1 should be greater than or equal to argument:0");
			return;
		}
		
		
		Integer diff=ub.subtract(lb).intValue(); //range of numbers to be inspected.
		//Global maximum variable initialized to the lower bound.
		BigInteger maxI=lb ;
		BigInteger maxP=new BigInteger("2");
		BigInteger maxQ=maxI.subtract(maxP);
		
		//Object of GoldbachVbl class.To be used as global reduction variable.
		GoldbachVbl globalVbl=new GoldbachVbl(maxI, maxP, maxQ);
		
		
		
		//Parallel for loop to create parallel team threads.
		parallelFor(0,diff).schedule(guided).exec(new Loop() {
			
			GoldbachVbl threadV;
			BigInteger localLb;
			
			//method to initialize the local variables for each thread.
			@Override
			public void start(){
				threadV=threadLocal(globalVbl);//assign threadV variable to the globalVbl.So the pj2 library can carry out reduction.
				localLb=lb;	
			}
			/*
			 * The method that handles the execution of each thread.
			 * @param number:the numbers that is passed to method from the parallelFor
			 * 				:Will be a subset of the given range(diff)
			 */
			@Override
			public void run(int number) throws Exception {
				
				BigInteger i;
				BigInteger p;
				BigInteger q;
				
				if(number%2==0)
				{
					 i=localLb.add(new BigInteger(number+""));//add the "number" variable to lowerBound to generate the i for current iteration.
					 p=new BigInteger("2");//set p to 2 as it is the minimum possible prime
				     q=i.subtract(p);
					
				     //loop until we find a prime p and prime q
					while(!(q.isProbablePrime(100)))
					{
						p=p.nextProbablePrime();
						q=i.subtract(p);
					}
				
					//Check if current p is greater than p of threadV(the local max per thread)
					threadV.reduce(new GoldbachVbl(i, p, q));
				
				}
			}
		});
		
		System.out.println(globalVbl.getI()+" = "+globalVbl.getP()+" + "+globalVbl.getQ());//print the answer to the console.
	}
	
	/*
	 * Method:checkisEven(number)
	 * This method checks if the number passed is even.
	 * @param: number-The BigInteger number to be tested for being even.
	 * @return:Boolean indicating if the number is even.
	 * @author:Salil Rajadhyaksha
	 * 
	 */
	private boolean checkisEven(BigInteger lb) {
		
		if(lb.mod(new BigInteger("2")).intValue()==0)
			return true;
		else
			return false;
	}
 

}

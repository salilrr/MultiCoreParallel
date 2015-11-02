import java.math.BigInteger;

import edu.rit.pj2.Vbl;

/*
 * Class GoldbachVbl:This class implements the edu.rit.pj2.Vbl interface 
 * 
 * This class supports parallel reduction.It is used in the GoldbachSmp class.
 * The reduction is performed with the help of the  reduce(Vbl gVbl ) method.
 * 
 * @author:Salil Rajadhyaksha
 * @version:20-Sept-2015
 */
class GoldbachVbl implements Vbl{

	//BigInteger variables
	private BigInteger i;
	private BigInteger p;
	private BigInteger q;

	/*
	 * Construct a new GoldbachVbl reduction  variable with i,p and q being initialized to null.
	 * 
	 */
	public GoldbachVbl()
	{

	}

	/*
	 * Construct a new GoldbachVbl reduction  variable using given BigInteger values.
	 * @param i:BigInteger Variable
	 * @param p:BigInteger Variable
	 * @param q:BigInteger Variable
	 */
	public GoldbachVbl(BigInteger i,BigInteger p,BigInteger q)
	{
		this.i=i;
		this.p=p;
		this.q=q;
	}

	/*
	 * Construct a new GoldbachVbl reduction  variable copying the contents of the passed GoldbachVbl to new reduction variable.
	 * @param :vbl-Object of type GoldbachVbl to be copied to the new reduction variable.
	 */
	public GoldbachVbl(GoldbachVbl vbl)
	{

		this.Copy(vbl);
	}

	/*
	 * Method to make the current GoldbachVbl a deep copy of the given GoldbachVbl
	 * 
	 * @param : vbl-The GoldbachVbl which is to be copied.
	 */
	public void Copy(GoldbachVbl vbl)
	{
		this.i=vbl.i;
		this.p=vbl.p;
		this.q=vbl.q;
	}
	/*
	 * This method reduces the given variable into this  variable.
	 * The result of the reduction is stored in this variable.
	 * 
	 * The method compares the p in the given vbl and if greater copies the given variable to this variable.
	 * if the value is same it compares the i of the given vbl to this  and if it is greater copies the given variable to this .
	 * 
	 * @param :gVbl :the reduction variable that is to be compared to the current variable.
	 * 
	 * @exception : ClassCastException(unchecked exception)
	 * 				 Thrown if the class of gVbl is not compatible with the class of type this reduction variable.
	 */
	@Override
	public void reduce(Vbl gVbl) {

		GoldbachVbl temp=(GoldbachVbl)gVbl;
		if(temp.p.compareTo(this.p)==1)
		{
			this.Copy(temp);

		}
		else
			if(temp.p.compareTo(this.p)==0)
			{
				if(temp.i.compareTo(this.i)==1)
				{

					this.Copy(temp);
				}
			}

	}

	/*
	 * Method to set the current reduction variable to the given reduction variable.
	 * @param:gVbl -The reduction variable that is to be set.
	 * 
	 * @exception : ClassCastException(unchecked exception)
	 * 			  Thrown if the class of gVbl is not compatible with the class of type this reduction variable.
	 */
	@Override
	public void set(Vbl gVbl) {

		GoldbachVbl temp;

		temp=(GoldbachVbl)gVbl;
		this.Copy(temp);

	}

	/*
	 * Method that makes a clone of this reduction variable.
	 * 
	 * @return:The reduction variable after being cloned.
	 * @exception:CloneNotSupportedException
	 * 			  Thrown if the super class is unable to clone this reduction variable.Handled in the method itself.
	 */
	@Override
	public Object clone() 
	{
		GoldbachVbl tempVbl=null;
		try {
			tempVbl = (GoldbachVbl)super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("Clone not supported.");
		}	
		tempVbl.Copy(this);
		return tempVbl;
	}


	/* Method to return the i field of this reduction variable.
	 * @return :The i field of the reduction variable.(BigInteger Variable)
	 */
	public BigInteger getI() {
		return i;
	}

	
	/*Method to set the i field of this reduction variable to the given value.
	 *
	 *@param :i-The variable that is to be assigned to this.i(BigInteger Variable)
	 */
	public void setI(BigInteger i) {
		this.i = i;
	}

	/* Method to return the p field of this reduction variable.
	 * @return :The p field of the reduction variable.(BigInteger Variable)
	 */
	public BigInteger getP() {
		return p;
	}
	
	/*Method to set the p field of this reduction variable to the given value.
	 *
	 *@param :p-The variable that is to be assigned to this.p(BigInteger Variable)
	 */
	public void setP(BigInteger p) {
		this.p = p;
	}

	/* Method to return the q field of this reduction variable.
	 * @return :The q field of the reduction variable.(BigInteger Variable)
	 */
	public BigInteger getQ() {
		return q;
	}

	/*Method to set the q field of this reduction variable to the given value.
	 *
	 *@param :q-The variable that is to be assigned to this.q(BigInteger Variable)
	 */
	public void setQ(BigInteger q) {
		this.q = q;
	}
	

}
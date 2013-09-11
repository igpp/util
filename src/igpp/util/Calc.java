package igpp.util;

import java.util.ArrayList;
/**
 * Perform simple mathematical operations.
 *
 * @author Todd King
 * @author UCLA/IGPP
 * @version     1.0.0
 * @since     1.0.0
 **/
public class Calc extends java.lang.Object {
	
	public Calc()
	{
	}
	
	/**
	 * Perform an operation on two arguments. All match is done as double.
	 * 
	 * @param v1	the first operand
	 * @param operation the symbol for the operation. Allowed values are: -, +, *, /, %
	 * @param v2	the second operand
	 
	 * @return the result of performing the operation on arg1 and arg2.
	 */
	 static public double perform(double v1, String operation, double v2) 
	 {
		 double result = 0.0;
		 
		 if(operation.equals("-")) result = (v1 - v2);
		 if(operation.equals("+")) result = (v1 + v2);
		 if(operation.equals("*")) result = (v1 * v2);
		 if(operation.equals("/")) result = (v1 / v2);
		 if(operation.equals("%")) result = (v1 % v2);
		 
		 return result;
	}
	 
	 /**
	 * Perform an operation on two arguments. All match is done as double.
	 * 
	 * @param v1	the first operand
	 * @param operation the symbol for the operation. Allowed values are: -, +, *, /, %
	 * @param v2	the second operand
	 
	 * @return the result of performing the operation on arg1 and arg2.
	 */
	 static public double perform(String v1, String operation, String v2) 
	 {
		 return perform(Double.parseDouble(v1), operation, Double.parseDouble(v2));
	 }

	 /**
	 * Perform an operation on two arguments. All match is done as double.
	 * 
	 * @param v1	the first operand
	 * @param operation the symbol for the operation. Allowed values are: -, +, *, /, %
	 * @param v2	the second operand
	 
	 * @return the result of performing the operation on arg1 and arg2.
	 */
	 static public double perform(long v1, String operation, String v2) 
	 {
		 double dv1 = v1;
		 return perform(dv1, operation, Double.parseDouble(v2));
	 }
	 
	 /**
	 * Perform an operation on two arguments. All match is done as double.
	 * 
	 * @param v1	the first operand
	 * @param operation the symbol for the operation. Allowed values are: -, +, *, /, %
	 * @param v2	the second operand
	 
	 * @return the result of performing the operation on arg1 and arg2.
	 */
	 static public double perform(int v1, String operation, String v2) 
	 {
		 double dv1 = v1;
		 return perform(dv1, operation, Double.parseDouble(v2));
	 }
	 
	 /**
	 * Perform an operation on two arguments. All match is done as double.
	 * 
	 * @param v1	the first operand
	 * @param operation the symbol for the operation. Allowed values are: -, +, *, /, %
	 * @param v2	the second operand
	 
	 * @return the result of performing the operation on arg1 and arg2.
	 */
	 static public double perform(double v1, String operation, String v2) 
	 {
		 return perform(v1, operation, Double.parseDouble(v2));
	 }
	 
	 /**
	 * Perform an operation on two arguments. All match is done as double.
	 * 
	 * @param v1	the first operand
	 * @param operation the symbol for the operation. Allowed values are: -, +, *, /, %
	 * @param v2	the second operand
	 
	 * @return the result of performing the operation on arg1 and arg2.
	 */
	 static public double perform(String v1, String operation, double v2) 
	 {
		 double dv2 = v2;
		 return perform(Double.parseDouble(v1), operation, dv2);
	 }

	 /**
	 * Perform an operation on two arguments. All match is done as double.
	 * 
	 * @param v1	the first operand
	 * @param operation the symbol for the operation. Allowed values are: -, +, *, /, %
	 * @param v2	the second operand
	 
	 * @return the result of performing the operation on arg1 and arg2.
	 */
	 static public double perform(String v1, String operation, long v2) 
	 {
		 double dv2 = v2;
		 return perform(Double.parseDouble(v1), operation, dv2);
	 }
	 
	 /**
	 * Perform an operation on two arguments. All match is done as double.
	 * 
	 * @param v1	the first operand
	 * @param operation the symbol for the operation. Allowed values are: -, +, *, /, %
	 * @param v2	the second operand
	 
	 * @return the result of performing the operation on arg1 and arg2.
	 */
	 static public double perform(String v1, String operation, int v2) 
	 {
		 double dv2 = v2;
		 return perform(Double.parseDouble(v1), operation, dv2);
	 }

	 /**
	 * Perform an operation on two arguments. All match is done as double.
	 * 
	 * @param v1	the first operand
	 * @param operation the symbol for the operation. Allowed values are: -, +, *, /, %
	 * @param v2	the second operand
	 
	 * @return the result of performing the operation on arg1 and arg2.
	 */
	 static public double perform(long v1, String operation, long v2) 
	 {
		 double dv1 = v1;
		 double dv2 = v2;
		 return perform(dv1, operation, dv2);
	 }

	 /**
	 * Round a value down to nearest integer and return value as a long.
	 * 
	 * @param v	the value
	 
	 * @return the value rounded down.
	 */
	 static public long floor(double v) 
	 {
		 return (long) Math.floor(v);
	 }


	 /**
	 * Round a value up to nearest integer and return value as a long.
	 * 
	 * @param v	the value
	 
	 * @return the value rounded down.
	 */
	 static public long ceil(double v) 
	 {
		 return (long) Math.ceil(v);
	 }


	/**
	 * Sum all the values in a list.
	 * 
	 * @param list	an set of value to sum
	 
	 * @return the result of performing the operation on arg1 and arg2.
	 */
	 static public double sum(ArrayList<String> list) 
	 {
		 double result = 0.0;
		 
		 for(String val : list) {
			 double v = Double.parseDouble(val);
			 result += v;
		 }
		 
		 return result;
	}

}


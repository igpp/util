package igpp.util;

/**
 * Utility class to store terms and definitions.
 *
 * @author Todd King
 * @version 1.00 2006
 */
public class TermDef {
	String	mTerm;
	String	mDefinition;
	boolean mSelected;
	
	public TermDef(String term, String definition) 
	{
		mTerm = term;
		mDefinition = definition;
		mSelected = false;
	}
	
	public void setTerm(String value) { mTerm = value; }
	public String getTerm() { return mTerm; }
	
	public void setDefinition(String value) { mDefinition = value; }
	public String getDefinition() { return mDefinition; }
	
	public void setSelected(boolean value) { mSelected = value; }
	public boolean getSelected() { return mSelected; }
	
	public String checked() { if(mSelected) return "checked"; return ""; }
}

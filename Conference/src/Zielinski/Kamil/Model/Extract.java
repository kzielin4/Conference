/**
 * 
 */
package Zielinski.Kamil.Model;

/**
 * @author k.zielinski
 *
 */
public class Extract
{
	private String line;
	private int valid;

	public Extract(String line, int valid)
	{
		super();
		this.line = line;
		this.valid = valid;
	}

	public String getLine()
	{
		return line;
	}

	public void setLine(String line)
	{
		this.line = line;
	}

	public int getValid()
	{
		return valid;
	}

	public void setValid(int valid)
	{
		this.valid = valid;
	}
}

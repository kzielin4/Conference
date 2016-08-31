package Zielinski.Kamil.Model;

public class Logger
{
	public Logger(String logLine, String path)
	{
		super();
		this.logLine = logLine;
		this.path = path;
	}
	private String logLine;
    private String path;
	public void writeLog(String line)
	{
       /*TODO
        * File log = new File("log.txt")
    try{
    if(log.exists()==false){
            System.out.println("We had to make a new file.");
            log.createNewFile();
    }
    PrintWriter out = new PrintWriter(log);
    out.append("******* " + timeStamp.toString() +"******* " + "\n");
    out.close();
    }catch(IOException e){
        System.out.println("COULD NOT LOG!!");
    }
        */
	}
	public String getPath()
	{
		return path;
	}
	public void setPath(String path)
	{
		this.path = path;
	}
}

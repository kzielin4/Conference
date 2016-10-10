package Zielinski.Kamil.App;

import java.sql.Timestamp;
import Zielinski.Kamil.Model.ExtractLoader;
import Zielinski.Kamil.Model.Validator;
import javafx.*;
public class App
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		ExtractLoader loader = new ExtractLoader();
		// loader.read("wyklad.txt");
		Validator vali = new Validator();
		// System.out.println("aa: "+ vali.validateLastName("aa"));
		// System.out.println("aa1: "+ vali.validateLastName("aa1"));
		// System.out.println(vali.validateDates("1920-01-01 12:11", "1920-01-01
		// 12:13"));
		// System.out.println(vali.validateDates("1920-01-01 12:12", "1920-01-01
		// 12:11"));
        //System.out.println(vali.isStringDate("11aa111111"));
        //loader.executeLoading();
	}
}

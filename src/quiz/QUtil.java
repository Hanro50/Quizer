package quiz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import bot.boot.FileObj;

public class QUtil {
	static final String path = "Quizes/";
	static final String extention = "qzo";
	
	public static boolean exists(String Name) {
		return FileObj.Fetch(path, Name, extention).exists();
	}
	
	public static File FetchFile(String Name) {
		return FileObj.Fetch(path, Name, extention); 
	}
	
	public static boolean erase(String Name) throws FileNotFoundException, IOException{
		return Fetch(Name).Erase();
	}
	
	public static Quizz Fetch(String Name) throws FileNotFoundException, IOException {
		File F = FetchFile(Name);
		String SQ = FileObj.read(F)[0];
		return FileObj.fromjson(SQ, Quizz.class);
			
	}
	
	public static String List() {
		try {
			String[] lst = FileObj.FileListString(path, extention);
			String R = "";
			for (int i = 0; i < lst.length; i++ ) {
				R = R + "" + (i+1) + ") \"" + lst[i] + "\"\n"; 
			}
			return R;
		} catch (FileNotFoundException e) {
			return "No Quiz available";
		}
	}
}

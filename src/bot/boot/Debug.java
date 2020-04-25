package bot.boot;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Debug {
	static Debug DB;
	

	static public final boolean Debug = true; 
	static public boolean Asciidebug =true;
	static private String Lastclass = "";
	static private ASCII_CODES HD = ASCII_CODES.RESET;
	
	static{
		
		//JDALogger;
		//JDALogger
		//LoggerFactory.getLogger(Debug.class);
	}
	
	private static boolean getAsciidebug() {
		return Asciidebug;
	}
	
	public static void Version() {
		out("Printing Debug Information:");
		System.setProperty("author.username", "Hanro50");
		
		List<String> keys = new ArrayList<String>();
		Comparator<String> cmp = (String.CASE_INSENSITIVE_ORDER).reversed().reversed();
		keys.addAll(System.getProperties().stringPropertyNames());
		keys.sort(cmp);
		
		for (int i = 0; i < keys.size();i++) {
			out(String.format("%-30s",keys.get(i))+" : "+System.getProperty(keys.get(i)));
		}
		out("Continuing on with rest of application->");
	}
	
	public static void err(String Line) {
		if (Debug) System.err.println(Format(Line, ASCII_CODES.Bright_YELLOW, ASCII_CODES.Bright_WHITE, ASCII_CODES.Tab));
	}
	
	
	//public void print(String x) {
	//	out(x);
	//}
	
	
	
	public static void out(String Line) {
		if (Debug) System.out.println(Format(Line, ASCII_CODES.Bright_GREEN, ASCII_CODES.Bright_WHITE, ASCII_CODES.Tab));
		//System.out.print(e.getStackTrace()[0].getClassName().split("\\.")     );
				
				
				//"("+e.getStackTrace()[0].+ ":" + e.getStackTrace()[0].getLineNumber() +") | "+ e.getStackTrace()[0] + "\n(boot.Debug.java:35)") ;
	}
	
	public static void Trace(Throwable e) {
		System.err.println(Caller(ASCII_CODES.Bright_RED)+ ASCII_CODES.Bright_YELLOW +ASCII_CODES.Tab +"<" +e.toString().replace(": ", "> [")+"] {"+ ASCII_CODES.RESET );
		for (StackTraceElement STE : e.getStackTrace()) {
			System.err.println(
					ASCII_CODES.Tab.toString().repeat(2)+
					ASCII_CODES.Bright_RED+"<E>"+ASCII_CODES.Bright_WHITE+" "+
					STE.toString().replaceFirst("\\(", ASCII_CODES.CYAN +"(")+ 
					ASCII_CODES.YELLOW + " "+ (STE.getModuleVersion() == null?"":"version of class: "+STE.getModuleVersion())+
					ASCII_CODES.RESET);
		}
		//e.printStackTrace();
		System.err.println(ASCII_CODES.Bright_YELLOW + (ASCII_CODES.Tab + "}") + ASCII_CODES.RESET );
		//if (Debug) System.err.println
	}
	
	
	
	
	private static String Caller(ASCII_CODES headerc) {
		String ls = "";
		CHK:{
			Exception e =  new Exception();
			for (StackTraceElement STE : e.getStackTrace()) {
				if (STE.getClassName() != Debug.class.getName()) {
					ls = STE.getClassName();
					break CHK;
				}
			}
			ls = "UNKNOWN";
		}
		
		//String ls = new Exception().getStackTrace()[3].getClassName();
		if (Lastclass.equals(ls) && (HD == headerc)) return "";
		Lastclass = ls;
		HD =headerc;
		return headerc +"["+Lastclass+"]"+ASCII_CODES.RESET+"\n";
		
	}
	
	private static String Format(String Line, ASCII_CODES headerc, ASCII_CODES bodyc, ASCII_CODES indent) {
		return Caller(headerc)+bodyc+indent+ Line.trim().replaceAll("\n",ASCII_CODES.RESET+ "\n"+bodyc+indent)+ASCII_CODES.RESET;
	}
	
	public static enum ASCII_CODES {
		//Normal colours|Bright versions       |Custom characters
		RESET(0),							   Tab("    "),
		BLACK(30)		,Bright_BLACK(30,1),
		RED(31)			,Bright_RED(31,1),
		GREEN(32)		,Bright_GREEN(32,1),
		YELLOW(33)		,Bright_YELLOW(33,1),
		BLUE(34)		,Bright_BLUE(34,1),
		PURPLE(35)		,Bright_PURPLE(35,1),
		CYAN(36)		,Bright_CYAN(36,1),
		WHITE(37)		,Bright_WHITE(37,1)
		;
		ASCII_CODES(String value){
			Key2 = value;Key = value;
		}
		
		
		ASCII_CODES(int value) {
			 Key = "\033[" + value + "m";
			 Key2 = "";
		}
		
		ASCII_CODES(int value1, int value2) {
			 Key = "\033[" + value1+ ";" +value2+  "m";
			 Key2 = "";
		}

		//String ANSI_RESET = "\033[0m";
		 //String ANSI_BLACK = "\033[30m";
		 //String ANSI_RED = "\u001B[31m";
		 //String ANSI_GREEN = "\u001B[32m";
		 //String ANSI_YELLOW = "\u001B[33m";
		 //String ANSI_BLUE = "\u001B[34m";
		 //String ANSI_PURPLE = "\033[35m";
		 //String ANSI_CYAN = "\u001B[36m";
		 //String ANSI_WHITE = "\u001B[37m";
		  
		 final  String Key2;
		 final String Key;
		 public String toString() {
			 if (!getAsciidebug()) {
				 return Key2;
			 }
			 
			 
			 return Key;};
		}
	
}

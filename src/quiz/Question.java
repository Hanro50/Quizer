package quiz;

import com.google.gson.annotations.Expose;

public class Question{

	@Expose String Qstatement;
	@Expose String[] choices;
	@Expose String usrAwn = null;
	@Expose int type;
	@Expose char awnser = 'Z';
	static final String Alphabet = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	
	public String usrAwn(String Awn) {
		//Multiple choice
		if (type == 0) {
			String T = Awn.trim().toUpperCase();
			String Alpha = Alphabet.substring(0, choices.length);
			if ((T.length() != 1) || (!Alpha.contains(T))){
				char[] A = Alpha.toCharArray();
				String A2 = "";
				for (int i = 0; i < A.length; i++) {
					A2 = A2 + "\""+ A[i] + "\" ";
				}
				
				return "(This is multiple choice)\n"
						+ "Invalid option selected U% \n"
						+ "Options are: ```\n"
						+ A2 + "```";
			} //end of length check
			else usrAwn = T;
		}
		else usrAwn = Awn;
		
		return null;
	}
	
	public String as_string() {
		String ret = "**" + filter(Qstatement) + "**\n```" ;
		for (int i = 0; i<choices.length;i++) {
			ret = ret + filter(choices[i]) + "\n";
		}
		return ret + "```";
	}	
	
	public boolean isRight() {
		return usrAwn.contains(awnser +"");
	}
	
	public String Awnser() {
		String Q = "";
		if (type == 0) {
			if (Alphabet.contains(usrAwn)) {
				int c = Alphabet.indexOf(usrAwn);
				if (choices.length> c) {
					Q = choices[c];
				}	
			}
		}
		return Qstatement + "\n\t("+ usrAwn + ")" + Q;
	}
	
	private String filter(String F) {
		return F.replaceAll("�n", "'n").replaceAll("�", "\"").replaceAll("", "'").replaceAll("", "\"");
	}
	
	
}

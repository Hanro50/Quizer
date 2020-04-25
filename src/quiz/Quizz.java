package quiz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.google.gson.annotations.Expose;
import bot.boot.FileObj;
import bot.main.Msg;
import bot.usr.UsrObj;

public class Quizz /*implements Serializable*/{
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	@Expose public boolean open = true;
	@Expose public final String QName;
	@Expose private long Author; 
	@Expose private int type; 
	@Expose private boolean automark;
	@Expose private boolean retakeable;
	@Expose private Question[] Q;
	
	static final String awnPath = "Awn/";
	static public final String awnext = "awn";
	//Header() {};
	
	static {
		FileObj.FileChk(awnPath);
	}
	
	
	Quizz(long Author, int QNum, int type, boolean automark, boolean retakeable, String Name){
		this.Author = Author;
		this.type = type;
		this.automark = automark;
		this.retakeable = retakeable;
		this.QName = FileObj.SafeStr(Name);
	}

	public long getAuthor() {
		return Author;
	}

	public int getQNum() {
		return Q.length;
	}

	public boolean isAutomark() {
		return automark;
	}

	public int gettype() {
		return type;
	}
	
	public void setQuestion(List<Question> Qin) {
		Q = new Question[Qin.size()];
		for (int i = 0; i < Qin.size(); i++) {
			Q[i]=Qin.get(i);
		}
	}

	public Question getQuestion(int index) {
		if (index >= Q.length) return Q[Q.length-1];
		return Q[index];
	}
	
	public Boolean hasindex(int index) {
		return (Q.length > index) && (index >= 0);
	}

	public boolean isRetakeable() {
		return retakeable;
	}
	
	public void Save() throws IOException {
		File F = FileObj.Fetch(QUtil.path, QName, QUtil.extention);
		String[] json = {FileObj.tojson(this)};
		FileObj.write(json, F, "Quizz");
	}
	
	
	
	public boolean Hasbeentaken(Msg m) {
		if (retakeable) return false;
		File F = FileObj.Fetch(awnPath+QName+"/", m.sender.getId(), ".awn");
		return F.exists();
		
	}
	
	public void SaveAwn(Msg m, UsrObj parent) throws IOException {
		FileObj.FileChk(awnPath+QName+"/");
		File F = FileObj.Fetch(awnPath+QName+"/", m.sender.getId(), ".awn");
		
		String[] s = {"Done on: "+System.getProperty("java.version.date"),
				  "Done by: "+parent.getName(),
				  "Discord data: ",
				  m.sender.getId(),
				  m.sender.getAsTag(),
			      "Data: ",
			      FileObj.tojson(this)
				  };
	
		FileObj.write(s, F, "Awnser");
	}
	
	public boolean Erase() throws FileNotFoundException {
		File F1 = QUtil.FetchFile(QName);
		File F2 = new File(AwnPath());
		if (F1.exists())
			if (F2.exists()) F2.delete();
			else throw new FileNotFoundException();
		return  F1.delete();
	}
	
	public String AwnPath() {
		return awnPath+QName+"/";
	}
	
	public boolean isAwnsered(Msg m) {
		return FileObj.Fetch(AwnPath(), m.sender.getId(), awnext).exists();
		//	File F1 = new File("Awn/");
		//	File F2 = new File("Awn/"+QName+"/");
		//	if (!F1.exists()) F1.mkdir();
		//	if (!F2.exists()) F2.mkdir();
		//	return new File(ObjFile.ClassPath+  "Awn/"+QName+"/"+m.Sender.getId()+".Score");
		}
}

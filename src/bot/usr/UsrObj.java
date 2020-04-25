package bot.usr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

import bot.boot.Debug;
import bot.boot.FileObj;
import bot.main.Msg;
import bot.usr.act.Dosetname;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

public class UsrObj {
	@Expose String ActClass = "";
	@Expose String ActData = "";
	Activity Act;
	boolean isnew = false;
	@Expose String UsrID;
	@Expose String Path;
	@Expose private List<String> DiscordTag;
	@Expose private List<String> UserName;
	
	
	static final String path = "usr/"; 
	static {
		FileObj.FileChk(path);

	}
	
	UsrObj (User Usr, String Path) {
		this.Path = Path;
		this.DiscordTag = new ArrayList<String>();
		this.UserName = new ArrayList<String>();
		DiscordTag.add(Usr.getAsTag());
		UserName.add(Usr.getName());
		UsrID = Usr.getId();
	}
	
	public void Save() throws IOException {
		Debug.out("Saving...");
		if (Act != null) {
			ActClass = Act.getClass().getName();
			ActData = FileObj.tojson(Act);
		}
		else {
			ActClass = "";
			ActData = "";
		}
		String[] fstr = {FileObj.tojson(this)};
		File ffile = new File(Path);
		FileObj.write(fstr, ffile, this.getClass().getName(), false);
	}
	
	public void setAct(Msg m,Activity Act) throws IOException {
		this.Act = Act;
		this.Act.init(m, this);
		Save();
	}
	
	public void clrAct() throws IOException {
		this.Act = null;
		Save();
	}
	
	public void setName(String Name) throws IOException {
		UserName.add(Name);
		Save();
	}
	
	public String getName() {
		return UserName.get(UserName.size()-1);
	}
	
	public void doAct(Msg m) throws IOException {
		if(!DiscordTag.contains(m.sender.getAsTag()))DiscordTag.add(m.sender.getAsTag());
		if (!isnew || m.isServer()) {
			if (Act != null) {
				Act.Do(m, this);
				Save();
			}
		}
	}
	
	public String GetAct(Msg m) throws IOException {
		if (Act != null) return ActClass;
		else return "";
	}
	
	public void CancelAct(Msg m) throws IOException {
		if (Act != null) {
			Act.CancelCall(m, this);
			Save();
		}
	}
	
	static public UsrObj Load(Msg m) throws IOException{
		if (m.isServer()) return Load(m.sender, m.server);
		else {
			UsrObj Obj=Load(m.sender, null);
			if (Obj.isnew) {
				Obj.setAct(m, new Dosetname());
			}
			return Obj;
		}
	}
	
	static public UsrObj Load(User usr) throws IOException{
		return Load(usr, null);
	}
	
	static public UsrObj Load(User usr, Guild serv) throws IOException{
		String MPath = "private/";
		
		
		if (serv != null) {
			MPath = serv.getId() +"/";
		}
		MPath = path+MPath;
		FileObj.FileChk(MPath);
		File UsrObjFile = FileObj.Fetch(MPath, usr.getId(), "usr");
		try {
			String S = FileObj.read(UsrObjFile, "");
			UsrObj Obj =  FileObj.fromjson(S, UsrObj.class);
			if ((Obj.ActClass != null && Obj.ActData != null) && !(Obj.ActClass.equals("")||Obj.ActData.equals(""))  ) {
				try {
					Obj.Act = FileObj.fromjson(Obj.ActData, Class.forName(Obj.ActClass).asSubclass(Activity.class));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					Debug.err("Deserialization error");
					Obj.Act = null;
					Obj.ActClass = "";
					Obj.ActData = "";
				}
				
			}
			return Obj;
		}catch (ArrayIndexOutOfBoundsException | FileNotFoundException e) {
			//e.printStackTrace();
			
		
		} 
		
		UsrObj Obj = new UsrObj(usr,UsrObjFile.getPath());
		Obj.Save();
		Obj.isnew = true;
		
		return Obj;
	}
	
	
}

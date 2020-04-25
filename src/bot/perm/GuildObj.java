package bot.perm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

import bot.boot.FileObj;
import bot.main.Msg;
import net.dv8tion.jda.api.entities.Role;

public class GuildObj {
	static final String usrPath = "usr/";
	static final String GOFN = "PermFile";
	static final String GOFNext = "prm";
	static {
		FileObj.FileChk(usrPath);
	}
	
	@Expose final public String ID;
	@Expose public List<Role> ModRoles;
	@Expose public List<String> UserCache;
	@Expose public List<String> Modusers;
	String Me = "";
	GuildObj( String ID){
		this.ID = ID;
	}
	
	public static GuildObj GetGuildObj(Msg m) throws IOException{
		
		String ID;
		if (!m.isServer()) { 
			ID = "private/";
		}
		else {
			ID = m.server.getId();
		}
		
		String path = usrPath+ m.server.getId() +"/";
		FileObj.FileChk(path);
		File F = FileObj.Fetch(path, GOFN, GOFNext);
		
		if (F.exists()) {
			String SRV = FileObj.read(F)[0];
			GuildObj GO = FileObj.fromjson(SRV, GuildObj.class);
			GO.Me = SRV;
			if (GO.ModRoles == null) GO.ModRoles = new ArrayList<Role>();
			if (GO.UserCache == null) GO.UserCache = new ArrayList<String>();
			if (GO.Modusers == null) GO.Modusers = new ArrayList<String>();
			return GO;
		}
		
		GuildObj SG = new GuildObj(ID);
		SG.ModRoles = new ArrayList<Role>();
		SG.UserCache = new ArrayList<String>();
		SG.Modusers = new ArrayList<String>();
		
		SG.Save();
		//String SRV = ObjFile.tojson(RG);
		
		return SG;
	}
	public void Save() throws IOException {
		String SC = FileObj.tojson(this);
		if (Me != SC) {
			String[] SRV = {FileObj.tojson(this)};
			String path = usrPath + ID + "/";
			FileObj.FileChk(path);
			
			File F = FileObj.Fetch(path, GOFN, GOFNext);
			FileObj.writeNF(SRV, F, "permObj");
		}
	}
	
	
}

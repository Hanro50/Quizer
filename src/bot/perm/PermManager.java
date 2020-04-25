package bot.perm;

import java.io.IOException;
import java.util.List;

import bot.boot.Debug;
import bot.main.Msg;
import bot.main.Print;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

public class PermManager {

	
	//public class ServGuild 
	
	
	public static boolean HasMod(Msg m) {
		try {
			if (!m.isServer()) return m.isBotOwner();
			
			GuildObj SRV = GuildObj.GetGuildObj(m);
			if (m.isAdmin()) return true;
			if (SRV.UserCache.contains(m.sender.getId())) return false; 
			if (SRV.Modusers.contains(m.sender.getId())) return true;
			
			List<Role> senderroles = m.message.getMember().getRoles();
			for (int i = 0; i < SRV.ModRoles.size();i++) {
				if (senderroles.contains(SRV.ModRoles.get(i))) {
					return true;
				}
			}
			
			SRV.UserCache.add(m.sender.getId());
			SRV.Save();
			return false; 
		}catch (IOException e) {
			Debug.Trace(e);
			
		}
		return false;
	}
	
	//ID 0
	public static void AddRole(Msg m,List<Role> rls) throws IOException{
		if (!m.isServer()) return;
		if (m.isAdmin()) {
			GuildObj SRV = GuildObj.GetGuildObj(m);
			String A = "```";
			for (Role role : rls) {
				if (!SRV.ModRoles.contains(role)) {
					SRV.ModRoles.add(role);
					//if (SRV.UserCache.contains(user.getId())) {SRV.UserCache.remove(user.getId());}
					A = A +role.getName() + " role oped Successfully\n";
				}
				else A = A +role.getName() + " role already held mod permisions\n";
			}
			
			
			//else Print.Err(m, "User already added");
			SRV.UserCache.clear();
			SRV.Save();
			new Print(m, " %U Output log: ```" + A);
			
		}
	}
	//ID 1
	public static void AddUser(Msg m,List<User> usrs) throws IOException{
		if (!m.isServer()) return;
		if (m.isAdmin()) {
			GuildObj SRV = GuildObj.GetGuildObj(m);
			String A = "```";
			for (User user : usrs) {
				if (!SRV.Modusers.contains(user.getId())) {
					SRV.Modusers.add(user.getId());
					if (SRV.UserCache.contains(user.getId())) {SRV.UserCache.remove(user.getId());}
					A = A +user.getName() + " Oped Successfully\n";
				}
				else A = A +user.getName() + " already held mod permisions\n";
			}
			
			
			//else Print.Err(m, "User already added");
			SRV.Save();
			new Print(m, " %U Output log: ```" + A);
		}
	}
	//ID 2
	public static void RemUser(Msg m,List<User> usrs) throws IOException{
		if (!m.isServer()) return;
		if (m.isAdmin()) {
			GuildObj SRV = GuildObj.GetGuildObj(m);
			String A = "```";
			for (User usr : usrs) {
				if (SRV.Modusers.contains(usr.getId())) {
					SRV.Modusers.remove(usr.getId());
					SRV.UserCache.add(usr.getId());
					A = A +usr.getName() + " Deoped Successfully\n";
				}
				else A = A +usr.getName() + " didn't hold mod permisions\n";
			}
		
			SRV.Save();
			new Print(m, " %U Output log: ```" + A);
			
			
			
				
				
				//SRV.Save();
				//Print.Suc(m);
			
			//else Print.Err(m, "User doesn't hold mod permisions");
		}
	}
	//ID 3
	public static void RemRole(Msg m,List<Role> role) throws IOException{
		if (!m.isServer()) return;
		if (m.isAdmin()) {
			GuildObj SRV = GuildObj.GetGuildObj(m);
			String A = "```";
			for (Role rle : role) {
				if (SRV.ModRoles.contains(rle)) {
					SRV.ModRoles.remove(rle);
					A = A +rle.getName() + " Deoped Successfully\n";
				}
				else A = A +rle.getName() + " didn't hold mod permisions\n";
			}
			SRV.Save();
			new Print(m, " %U Output log: ```" + A);
		}
		
		
		
		
		
		
		
		//if (m.isAdmin()){
		//	GuildObj SRV = GuildObj.GetGuildObj(m);
		//	if (SRV.ModRoles.contains(role)){
		//		SRV.ModRoles.remove(role);
		//		SRV.Save();
		//		Print.Suc(m);
		///	}
		//	else Print.Err(m, "Role doesn't hold mod permisions");
		//}
	}
}

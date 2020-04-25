package bot.com;

import bot.main.Msg;
import bot.perm.PermManager;

public abstract class ComObj {
	public enum Type {
		React()	{@Override public String getCom(String com) {return com;}public Boolean isEmotCom() {return false;}},
		Text()	{@Override public String getCom(String com) {return com.toLowerCase().trim();}};
		public String getCom(String com) {return null;}
		public Boolean isEmotCom() {return false;}
	}
	
	public static enum Place {
		Any 	{@Override public boolean chk(Msg m) {return true;};},
		
		Server 	{@Override public boolean chk(Msg m) {return m.isServer();};},
		Private {@Override public boolean chk(Msg m) {return m.isPrivate();};},
		Group 	{@Override public boolean chk(Msg m) {return m.isGroup();};},
		
		ServerGroup   {@Override public boolean chk(Msg m) {return m.isServer() || m.isGroup();};},
		ServerPrivate {@Override public boolean chk(Msg m) {return m.isServer() || m.isPrivate();};},
		PrivateGroup  {@Override public boolean chk(Msg m) {return m.isGroup () || m.isPrivate();};};
		public boolean chk (Msg m) {return false;};
	}
	
	public static enum Permlv {
		all {},
		Mod{public boolean chk (Msg m) {return PermManager.HasMod(m);};},
		Admin{public boolean chk (Msg m) {return m.isAdmin();};},
		BotOwn{public boolean chk (Msg m) {return m.isBotOwner();};};
		public boolean chk	(Msg m) {return true;};
	}
	public static enum Visible {
		yes,
		no{public boolean chk	() {return false;}};
		public boolean chk	() {return true;};
	}
	
	public final Type type; 
	public final Place place;
	public final Permlv permlv;
	public final Visible visible;
	
	public ComObj(Type Type, Place Place, Permlv Permlv) {
		this.type = Type;
		this.place = Place;
		this.permlv = Permlv;
		this.visible = Visible.yes;
	}
	
	public ComObj(Type Type, Place Place, Permlv Permlv,Visible Visible ) {
		this.type = Type;
		this.place = Place;
		this.permlv = Permlv;
		this.visible = Visible;
	}
	
	abstract protected String com();
	abstract public void Run(Msg m);
	abstract public String Help();
	
	public String getCom() {return this.type.getCom(com());}
}
	
	
	
	/*
	
	
	
	
	
	
	public Place p 
	
	public enum Place {
		Any 	{@Override public boolean check(Msg m) {return true;};},
		Server 	{@Override public boolean check(Msg m) {return m.isServer();};},
		Private {@Override public boolean check(Msg m) {return m.isPrivate();};},
		Group 	{@Override public boolean check(Msg m) {return m.isGroup();};},
		
		ServerGroup   {@Override public boolean check(Msg m) {return m.isServer() || m.isGroup();};},
		ServerPrivate {@Override public boolean check(Msg m) {return m.isServer() || m.isPrivate();};},
		PrivateGroup  {@Override public boolean check(Msg m) {return m.isGroup () || m.isPrivate();};};
	}
	
	
	
	//Server 	{@Override public boolean check(Msg m) {return m.isServer();};},
	//Private {@Override public boolean check(Msg m) {return m.isPrivate();};},
	//Group 	{@Override public boolean check(Msg m) {return m.isGroup();};},
	//Any 	{@Override public boolean check(Msg m) {return true;};},
	
	//Hidden		  {@Override public boolean visible(Msg m) {return true;};},
	//ServerGroup   {@Override public boolean check(Msg m) {return m.isServer() || m.isGroup();};},
	//ServerPrivate {@Override public boolean check(Msg m) {return m.isServer() || m.isPrivate();};},
	//PrivateGroup  {@Override public boolean check(Msg m) {return m.isGroup () || m.isPrivate();};};
	
	public boolean visible	(Msg m) {return true;};
	public boolean check	(Msg m) {return false;};
	
	
	
	
	
	
	
}
	
	
		
	//Any(Server Private Group);
	
	
	
	//public String getCom(Msg m) {return null;};
	
	
	
	//public boolean ishidden() {return false;};
	
	//Hidden(){public boolean ishidden() {return true;};},
	
	

	
	
	
	//public static Set<CType> Any = Set.of(Server, Group, Private);
    
	
	
	
	
	//enum Place{
//		Server,
//		Group,
//		Private,
//		Hidden
//	};
//	enum Type {
//		Text,
//		React
//	};
	
	
	
	
	//  Server,
	//  Group,
	//  Private,
	//  React,
	//  Hidden,
	//  Any
	
*/
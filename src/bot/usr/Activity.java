package bot.usr;

import bot.main.Msg;


public abstract class Activity {
	
	
	
	public boolean error = false;
	protected boolean Shouldsave = false;
	public boolean Shouldsave() {return Shouldsave;}
	
	public abstract void init(Msg m, UsrObj parent);
	public abstract void Do  (Msg m, UsrObj parent);
	public abstract void CancelCall (Msg m, UsrObj parent);
	
}

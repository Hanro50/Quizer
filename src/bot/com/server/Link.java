package bot.com.server;

import bot.com.ComObj;
import bot.main.Msg;
import quiz.react.RHandler;

public class Link extends ComObj{
	public Link() {
		super(Type.Text, Place.Server, Permlv.Mod);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String com() {
		// TODO Auto-generated method stub
		return "link";
	}

	@Override
	public void Run(Msg m) {
		RHandler.LinkQ(m);
		
	}
	@Override
	public String Help() {
		// TODO Auto-generated method stub
		return "Link to a Quiz within this channel";
	}
	
	

}

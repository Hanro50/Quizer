package bot.com.server;

import bot.com.ComObj;
import bot.main.Msg;
import bot.timer.Tracker;

public class Update_Feed extends ComObj{

	public Update_Feed() {
		super(Type.Text, Place.Server, Permlv.BotOwn);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String com() {
		// TODO Auto-generated method stub
		return "update";
	}

	@Override
	public void Run(Msg m) {
		// TODO Auto-generated method stub
		Tracker.AddChannel(m.channel);
	}

	@Override
	public String Help() {
		// TODO Auto-generated method stub
		return "(Bot Owner only) Place where the bot can push it's stats";
	}

}

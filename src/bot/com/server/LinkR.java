package bot.com.server;

import java.io.IOException;

import bot.boot.Debug;
import bot.com.ComObj;
import bot.main.Msg;
import quiz.react.RHandler;

public class LinkR extends ComObj{

	public LinkR() {
		super(Type.React, Place.Server, Permlv.all, Visible.no);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String com() {
		// TODO Auto-generated method stub
		return RHandler.emote;
	}

	@Override
	public void Run(Msg m) {
		// TODO Auto-generated method stub
		Debug.out("Running");
		try {
			RHandler.ChkQ(m);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Debug.Trace(e);
		}
	}

	@Override
	public String Help() {
		// TODO Auto-generated method stub
		return "(React emote) For Quizzes and stuff";
	}

}

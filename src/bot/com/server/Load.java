package bot.com.server;

import bot.com.ComObj;
import bot.main.Msg;
import quiz.QLoader;

public class Load extends ComObj {

	public Load() {
		super(Type.Text, Place.Server, Permlv.Mod);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String com() {
		// TODO Auto-generated method stub
		return "load";
	}

	@Override
	public void Run(Msg m) {
		// TODO Auto-generated method stub
		new QLoader(m);
	}

	@Override
	public String Help() {
		// TODO Auto-generated method stub
		return "Load a Quiz";
	}

}

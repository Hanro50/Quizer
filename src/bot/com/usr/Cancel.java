package bot.com.usr;

import java.io.IOException;

import bot.boot.Debug;
import bot.com.ComObj;
import bot.main.Msg;
import bot.usr.UsrObj;

public class Cancel extends ComObj{

	public Cancel() {
		super(Type.Text, Place.Private, Permlv.all);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String com() {
		
		// TODO Auto-generated method stub
		return "cancel";
	}

	@Override
	public void Run(Msg m) {
		try {
			UsrObj usr = UsrObj.Load(m);
			usr.CancelAct(m);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Debug.err("IOException");
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public String Help() {
		// TODO Auto-generated method stub
		return "Cancel an active Quiz";
	}

}

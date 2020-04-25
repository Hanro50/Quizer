package bot.com.usr;

import java.io.IOException;

import bot.com.ComObj;
import bot.main.Msg;
import bot.main.Print;
import bot.usr.UsrObj;
import bot.usr.act.Dosetname;

public class SetName extends ComObj{

	public SetName() {
		super(Type.Text, Place.Private, Permlv.all);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String com() {
		// TODO Auto-generated method stub
		return "SetName";
	}

	@Override
	public void Run(Msg m) {
		// TODO Auto-generated method stub
		UsrObj usr;
		try {
			usr = UsrObj.Load(m);
		
			if (m.getText().length() <=3) {
				if (!usr.GetAct(m).equals("")) {
					new Print(m," %U Please complete current activity first \n or specify a name after the "+Msg.comline+this.com()+" command");
					return;
				}
				usr.setAct(m, new Dosetname());
			}
			else {
				Dosetname.setName(m, usr);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String Help() {
		// TODO Auto-generated method stub
		return "Please provide a name and surname so teachers can see who you are when marking your stuff";
	}
}

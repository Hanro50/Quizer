package bot.usr.act;

import java.io.IOException;

import bot.boot.Debug;
import bot.main.Msg;
import bot.main.Print;
import bot.usr.Activity;
import bot.usr.UsrObj;

public class Dosetname extends Activity {

	@Override
	public void init(Msg m, UsrObj parent) {
		// TODO Auto-generated method stub
		if (!(m.isCom() && m.getCom().contains("name")))
			new Print(m, "Please provide a name and surname so you can be more easily identified");
		else
			try {
				parent.clrAct();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

	public static boolean setName(Msg m, UsrObj parent) {
		if (m.getText().length() <3) {
			new Print(m, "A name must be more than 3 characters");
			return false;
		}
		if (m.getText().length() >100) {
			new Print(m, "A name must be under a 100 characters long");
			return false;
		}
		try {
			parent.setName(m.getText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Debug.Trace(e);
			Print.Err(m, "Internal IO error, Please try again");
			return false;
		}
		m.message.addReaction("U+1F44D");
		new Print(m, "Success: Changed name!");
		return true;
		//try {
		//	parent.clrAct();
		//} catch (IOException e) {
		//	// TODO Auto-generated catch block
		//	Debug.Trace(e);
		//}
	}
	
	
	@Override
	public void Do(Msg m, UsrObj parent) {
		// TODO Auto-generated method stub
		//if (m.getText().length() <3) {
		//	new Print(m, "A name must be more than 3 characters");
		//	return;
		//}
		//if (m.getText().length() >100) {
		//	new Print(m, "A name must be under a 100 characters long");
		//	return;
		//}
		//parent.setName(m.getText());
		if (setName(m,parent)) {
			try {
				parent.clrAct();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Debug.Trace(e);
			}
		}
	}

	@Override
	public void CancelCall(Msg m, UsrObj parent) {
		// TODO Auto-generated method stub
		new Print(m, "Cancelling name change");
		try {
			parent.clrAct();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Debug.Trace(e);
		}
	}

}

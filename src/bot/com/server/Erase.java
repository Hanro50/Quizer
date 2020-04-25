package bot.com.server;

import java.io.FileNotFoundException;
import java.io.IOException;

import bot.com.ComObj;
import bot.main.Msg;
import bot.main.Print;
import quiz.QUtil;

public class Erase extends ComObj {

	public Erase() {
		super(Type.Text, Place.Server, Permlv.Mod);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String com() {
		// TODO Auto-generated method stub
		return "erase";
	}

	@Override
	public void Run(Msg m) {
	try {
		// TODO Auto-generated method stub
		QUtil.erase(m.getText()); 
		new Print(m,"Succesfully deleted \"" + m.getText() + "\" %U ");
		}catch (FileNotFoundException e) {
			Print.Err(m,"Quiz not found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Print.Err(m,"Could not delete Quizz");
		}
	}

	@Override
	public String Help() {
		// TODO Auto-generated method stub
		return "Delete a quizz";
	}

}

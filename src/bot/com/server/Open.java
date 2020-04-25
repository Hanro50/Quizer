package bot.com.server;

import java.io.FileNotFoundException;
import java.io.IOException;

import bot.com.ComObj;
import bot.main.Msg;
import bot.main.Print;
import quiz.QUtil;
import quiz.Quizz;

public class Open extends ComObj {

	public Open() {
		super(Type.Text, Place.Server, Permlv.Mod);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String com() {
		// TODO Auto-generated method stub
		return "open";
	}

	@Override
	public void Run(Msg m) {
		// TODO Auto-generated method stub
		try {
			Quizz Q = QUtil.Fetch(m.getText());
			if (!Q.open) {
				Q.open = true;
				Q.Save();
				new Print(m,"Quizz is now Open");
				return;
			}
			new Print(m,"Already Open");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Print.Err(m,"Could not find Quizz");
		} catch (IOException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			Print.Err(m,"Could not save changes");
		}
	}

	@Override
	public String Help() {
		// TODO Auto-generated method stub
		return "(Open by default) open a Quizz for students";
	}

}

package bot.com.usr;

import java.io.FileNotFoundException;
import java.io.IOException;

import bot.boot.Debug;
import bot.com.ComObj;
import bot.main.Msg;
import bot.main.Print;
import bot.usr.UsrObj;
import bot.usr.act.DoQuizAct;
import quiz.QUtil;
import quiz.Quizz;

public class DoQuiz extends ComObj{

	public DoQuiz() {
		super(Type.Text, Place.Private, Permlv.all);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String com() {
		// TODO Auto-generated method stub
		return "do";
	}

	@Override
	public void Run(Msg m) {
		// TODO Auto-generated method stub
		UsrObj usr;
		try {
			usr = UsrObj.Load(m);
			if (!usr.GetAct(m).equals("")) {
				new Print(m," %U Please complete current activity first");
				return;
			}
			Quizz Q = QUtil.Fetch(m.getText());
			
			
			
			usr.setAct(m, new DoQuizAct(Q));
			
		} catch (FileNotFoundException e) {
			Debug.Trace(e);
			Print.Err(m, "Invalid Quiz option");
			Debug.err("Invalid Quiz option selected by user: "+ m.DebugMen());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Debug.Trace(e);
			Debug.err("[Internal IOException] \n" + "Could not read Quizz file :" + m.getText() + "\n" + "Asked by: " + m.DebugMen());
		} catch (Exception e) {
			Debug.Trace(e);
			Print.Err(m, "Please complete your current activity");
		}
	}

	@Override
	public String Help() {
		// TODO Auto-generated method stub
		return "Select a quiz and do it";
	}

}

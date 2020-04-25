package bot.com.any;

import java.io.FileNotFoundException;
import java.io.IOException;

import bot.boot.Debug;
import bot.com.ComObj;
import bot.main.Msg;
import bot.main.Print;
import quiz.QUtil;
import quiz.Quizz;


public class IsOpen extends ComObj {

	public IsOpen() {
		super(Type.Text, Place.Any, Permlv.all);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String com() {
		// TODO Auto-generated method stub
		return "isopen";
	}

	@Override
	public void Run(Msg m) {
		// TODO Auto-generated method stub
		try {
			Quizz Q = QUtil.Fetch(m.getText());
			if (Q.open) {new Print(m ," %U ```This Quizz is open```");}
			else {new Print(m ," %U ```This Quizz is closed```");}
		}catch (FileNotFoundException e) {
			Print.Err(m, "Could not find Quizz");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Print.Err(m, "Could not read Quizz file");
			Debug.err("IOException");
		}
	}

	@Override
	public String Help() {
		// TODO Auto-generated method stub
		return "Check if a set Quizz is doable";
	}

}

package bot.com.server;

import java.io.IOException;

import bot.com.ComObj;
import bot.main.Msg;
import quiz.Collect;
import quiz.QUtil;
import quiz.Quizz;

public class QCollect extends ComObj{

	public QCollect() {
		super(Type.Text, Place.Server, Permlv.Mod);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String com() {
		// TODO Auto-generated method stub
		return "collect";
	}

	@Override
	public void Run(Msg m) {
		// TODO Auto-generated method stub
		try {
			Quizz Q = QUtil.Fetch(m.getText());
			new Collect(m.sender, Q);
			m.message.addReaction("U+274C").queue();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String Help() {
		// TODO Auto-generated method stub
		return "Collect the results of a set test";
	}

}

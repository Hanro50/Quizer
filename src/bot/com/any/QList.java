package bot.com.any;

import bot.com.ComObj;
import bot.main.Msg;
import bot.main.Print;
import quiz.QUtil;

public class QList extends ComObj{

	public QList() {
		super(Type.Text, Place.Any, Permlv.all);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String com() {
		// TODO Auto-generated method stub
		return "list";
	}

	@Override
	public void Run(Msg m) {
		// TODO Auto-generated method stub
		new Print(m,"Current Quizzes Available %U :\n```" + QUtil.List() + "```");
		//channel.sendMessage("Current Quizzes Available "+AMention()+":\n" + Loader.List()).queue();
	}

	@Override
	public String Help() {
		// TODO Auto-generated method stub
		return "Lists all available Quizes";
	}

}

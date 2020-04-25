package bot.com.any;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import bot.com.ComObj;
import bot.com.Handler;
import bot.main.Msg;
import bot.main.Print;

public class CHelp extends ComObj{

	public CHelp() {
		super(Type.Text, Place.Any, Permlv.all);
		// TODO Auto-generated constructor stub
	}

	protected String com() {
		// TODO Auto-generated method stub
		return "help";
	}

	@Override
	public void Run(Msg m) {
		// TODO Auto-generated method stub
		
		
		boolean all =  m.getText().contains("all");
		List<String> keys = new ArrayList<String>();
		Comparator<String> cmp = (String.CASE_INSENSITIVE_ORDER).reversed().reversed();
		keys.addAll(Handler.ComObjMap.keySet());
		keys.sort(cmp);
		String res = " %U Help commands: **```";
		for (String key : keys) {
			ComObj comobj = Handler.ComObjMap.get(key);
			if (!comobj.type.isEmotCom()) {
				if ((all || (comobj.permlv.chk(m) && (comobj.visible.chk()))) && comobj.place.chk(m)) {
					res = res + String.format("%-12s", key) + " : "+ comobj.Help() + "\n";
				}
			}
		}
		res = res+ "```**";
		new Print(m, res);
	}

	@Override
	public String Help() {
		// TODO Auto-generated method stub
		return "Shows this Dialogue";
	}
	
}
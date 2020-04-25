package bot.com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bot.boot.Debug;
import bot.com.any.*;
import bot.com.server.*;
import bot.com.usr.*;
import bot.main.Msg;
public class Handler {
	public static Map<String,ComObj> ComObjMap = new HashMap<String,ComObj>();
	static {
		List<ComObj> ComObjList = new ArrayList<ComObj>(); 
		//Any
		ComObjList.add(new CHelp());
		ComObjList.add(new Stop());
		ComObjList.add(new IsOpen());
		ComObjList.add(new QList());
		//Server
		ComObjList.add(new Update_Feed());
		ComObjList.add(new Load());
		ComObjList.add(new Open());
		ComObjList.add(new Erase());
		
		ComObjList.add(new Close());
		ComObjList.add(new Notify());
		ComObjList.add(new Link());
		ComObjList.add(new QCollect());
		
		ComObjList.add(new PermAdd());
		ComObjList.add(new PermRem());
		//Private or user
		ComObjList.add(new DoQuiz());
		ComObjList.add(new Cancel());
		ComObjList.add(new SetName());
		//react 
		ComObjList.add(new LinkR());
		
		
		ComObjMap.clear();
		for (ComObj COMObj : ComObjList) {
			ComObjMap.put(COMObj.getCom(), COMObj);
		}
	}
	static public void com(Msg m) {
		
		
		ComObj ComObj = null;
		
		if (m.isReactCom()) {
			Debug.out(m.reactemote.getAsCodepoints());
			
			if (m.reactemote.isEmoji() && ComObjMap.containsKey(m.reactemote.getAsCodepoints())) {
				ComObj = ComObjMap.get(m.reactemote.getAsCodepoints());
			}
			else if (ComObjMap.containsKey(m.reactemote.getName())) {
				ComObj = ComObjMap.get(m.reactemote.getName());
			}
			else return;
		}
		else if (m.isCom() && ComObjMap.containsKey(m.getCom())) {
			ComObj = ComObjMap.get(m.getCom());
		}
		else return;
		if (ComObj != null && ComObj.place.chk(m) && (ComObj.permlv.chk(m))) {
			ComObj.Run(m);
		}
	}

	
//bot = new ;
}
//<>
package quiz.react;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.annotations.Expose;

import bot.boot.Debug;
import bot.boot.FileObj;
import bot.com.usr.DoQuiz;
import bot.main.Msg;
import bot.main.Print;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.internal.utils.EncodingUtil;
import quiz.QUtil;

public class RHandler {
	final static String Link = "usr/";
	static {
		FileObj.FileChk(Link);
	}
	public static final String emote = "U+2705";
	private static File chk(Msg m) {
		String PH = Link + m.server.getId()+"/";
		FileObj.FileChk(PH);
		return FileObj.Fetch(PH, "React_Emote_List", "lst");
	}
	
	
	
	static public void LinkQ(Msg m) {
		try {
			File Q = QUtil.FetchFile(m.getText());
			if (!Q.exists()) throw new FileNotFoundException("Invalid Quiz option selected by user: "+ m.DebugMen());
			File RL = chk(m);
			MAPC RQMC;
			if (RL.exists()) {
				String json = FileObj.read(RL, "");
				RQMC = FileObj.fromjson(json, MAPC.class);
			}
			else {
				RQMC = new MAPC();
			}
			//new Print(m, "Press \u2705 to Quizz: " + m.getText());
			Message ID = m.channel.sendMessage("Press the "+EncodingUtil.decodeCodepoint(emote) +" button down below to do Quizz: " + m.getText()).complete();
			RQMC.RM.put(ID.getId(), m.getText());
			
			String[] json = {FileObj.tojson(RQMC)};
			FileObj.write(json, RL, "ReactList",false);
			
			ID.addReaction(emote).queue();
			m.message.delete().queue();
		} catch (FileNotFoundException e) {
			Debug.Trace(e);
			Print.Err(m.getDM(),m.sender, "Invalid Quiz option");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Debug.Trace(e);
			Print.Err(m.getDM(),m.sender, "Failed to read Quizz option");
		}
		
	}
	
	static public void ChkQ(Msg m) throws FileNotFoundException, IOException {
		File RL = chk(m);
		if (RL.exists()) {
			String json = FileObj.read(RL, "");
			MAPC RQMC = FileObj.fromjson(json, MAPC.class);
			if (RQMC.RM.containsKey(m.message.getId())) {
				String QName = RQMC.RM.get(m.message.getId());
				m.message.clearReactions().complete();
				m.message.addReaction(emote).queue();
				Msg m1 = new Msg(m.sender, QName);
				new DoQuiz().Run(m1);
			}
		}
	}
	
	static class MAPC {
		@Expose public LinkedHashMap<String,String> RM = new LinkedHashMap<String,String>() {
			private static final long serialVersionUID = 1L;
			@Override
	        protected boolean removeEldestEntry(final Map.Entry<String,String> eldest) {
	            return size() > 50;
	        }
		};
	}
}

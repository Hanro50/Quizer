package bot.timer;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;

import bot.boot.Debug;
import bot.boot.FileObj;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDA.Status;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.sharding.ShardManager;
//State-Tracker. Mostly for the /update command. Forgot this name might seem scetchy to others
public class Tracker {
	public static boolean alive = true;
	public static void shutdown(){
		alive = false;
		updatelist("```Bot is currently offline```");
	}
	
	static public boolean boot = true;
	static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	static ShardManager SH;
	static MSGHLIST MSL;
	static File f = FileObj.Fetch("", "status", "tmp");
	static {
		try {
			MSL = FileObj.fromjson(FileObj.read(f, ""), MSGHLIST.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Debug.Trace(e);
			MSL = new MSGHLIST();
			MSL.Save();
		}
	}
	
	static public void SetshardManger(ShardManager setSH) {
		SH = setSH;

	}
	
	static public void AddChannel(MessageChannel Channel) {
		Message MSG =Channel.sendMessage("Temp Text. Bot will change this once the timer updates").complete();
		if (MSL.RM.containsKey(Channel.getId())) {
			updatemessage(Channel.getId(), MSL.RM.get(Channel.getId()), "Only one update message allowed per channel");
			MSL.RM.remove(Channel.getId());
		}
		MSL.RM.put(Channel.getId(), MSG.getId());
		MSL.Save();
	}
	static public void Update() {
		int Running = 0;
		List<JDA>jdalist = SH.getShardCache().asList();
   	  	for (JDA jda : jdalist) {
   	  		if (jda.getStatus() == Status.CONNECTED) Running++;
   	  	}
   	  	long size = jdalist.size();
   	  	LocalDateTime now = LocalDateTime.now();  
		String Out ="Updated message on "+dtf.format(now)+"```"+
					"\nNumber of active shards: [" +Running +"/"+ size + "]"+ 
					"\nAverage Gateway Ping :" + SH.getAverageGatewayPing() + "ms```";
		updatelist(Out);
	}
	
	
	static class MSGHLIST {
		@Expose public Map<String,String> RM = new HashMap<String,String>() ;
		//public MSGHLIST() {
		//	Save();
		//}

		public void Save() {
			Debug.out("Saving");
			try {
				FileObj.write(new String[] {FileObj.tojson(this),""}, f, "State traker",false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Debug.Trace(e);
				//e.printStackTrace();
			}
		}
	}
	static public boolean updatelist(String Text) {
		boolean chk = false;
		for (String Item : MSL.RM.keySet()) {
			chk = (updatemessage(Item, MSL.RM.get(Item), Text)) || chk ;
		}
		return chk;
	}
	
	static public void updatelist(JDA jda,String Text) {
		for (String Item : MSL.RM.keySet()) {
			updatemessage(jda, Item, MSL.RM.get(Item), Text);
		}
	}
		
	static private boolean updatemessage(String ChannelID, String MessageID, String Text) {
		try {
			SH.getTextChannelById(ChannelID).editMessageById(MessageID, Text).complete();
			return true;
		} catch (Throwable e) {
			if (!boot) Debug.out("Non functional:"+ChannelID + ":" + MessageID);
		}
		
		return false;
	}

	static private boolean updatemessage(JDA jda,String ChannelID, String MessageID, String Text) {
		try {
			jda.getTextChannelById(ChannelID).editMessageById(MessageID, Text).complete();
			return true;
		} catch (Throwable e) {
			Debug.out("Non functional:"+ChannelID + ":" + MessageID);
		}
		
		return false;
	}
	
}
	
	
	/*
	
	ShardManager SH;
	Random rand;
	boolean booting = true;
	int i = 0;
	public Tracker(ShardManager SH) {
		this.SH = SH;
		rand = new Random();
		rand.setSeed(System.currentTimeMillis());
	}
	enum Type {
		Playing,
		Streaming,
		Listening{public String toString() {return "Listening to";}},
		Watching;
	}
	String text;
		
    public void run() {
    	if (SH != null) {
	    	  int Running = 0;
	    	  List<JDA>jdalist = SH.getShardCache().asList();
	    	  for (JDA jda : jdalist) {
	    		  if (jda.getStatus() == Status.CONNECTED) Running++;
	    	  }
	    	  long size = jdalist.size();
	    	  
	    	  if ((Running != size)&&booting) {
	    		  text = ("startup chime: [" +Running +"/"+ size + "]");
	    		  SH.setActivity(Actf);
	    	  }
	    	  else if (booting) {
	    		  booting  = false;
	    		  Activity act = f[rand.nextInt(f.length)];
	    		  Debug.out("Done Booting, changing activity to: \"" +Type.values()[act.getType().getKey()]+" "+act.getName()+"\"");
	    		  SH.setActivity(act);
	    	  }
	    	  else {
	    		  Activity act = f[rand.nextInt(f.length)];
	    		  SH.setActivity(act);
	    	  }
	    	  i++;
	    	  //long Running = size - SH.getShardsQueued();
	    	  if ((i>=12)||(Running != size)) Debug.out("Number of active shards: [" +Running +"/"+ size + "]\nAverage Gateway Ping :" + SH.getAverageGatewayPing() + "ms");
	    	}
		}
		Activity Actf = new Activity() {
	    	@Override public boolean isRich() {return false;}
	    	@Override public RichPresence asRichPresence() {return null;}
	    	@Override public String getName() {return text;}
	    	@Override public String getUrl() {return null;}
	    	@Override public ActivityType getType() {return ActivityType.DEFAULT;}
	    	@Override public Timestamps getTimestamps() {return null;}
	    	@Override public Emoji getEmoji() {return new Emoji("U+1F4BF");}};  
	}
*/


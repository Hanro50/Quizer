package bot.boot;


import javax.security.auth.login.LoginException;

import bot.main.Event;
import bot.main.Msg;
import bot.timer.ticker;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
public class Main {
	
	
	public static String token = "Put bot token here";
	
	public static void main(String[] args) {
		try {
			if (args!=null && args.length > 0) {
				
				for (String str : args) {
					if (str.toLowerCase().contains("nocolour")) {
						Debug.Asciidebug = false;
						break;
					}
				}
			}
			if (Debug.Asciidebug) Debug.out("If you see Ascii controll characters. Launch with the perameter \"nocolour\", after the bot token, to turn them off\nJDA ignores formatting regardless.");
			
			if (args.length >= (Debug.Asciidebug?1:2)) {
				
				token = args[0];
				
				Debug.out("Starting Bot");
				DefaultShardManagerBuilder builder = 
						new DefaultShardManagerBuilder()
			    			.setToken(token)
			    			.addEventListeners(new Event())
			    			.setShardsTotal(-1);
		
			    ShardManager SH = builder.build();
			    
			    JDA jda =SH.getShardCache().asList().get(0);
			   	jda.awaitReady();
			   	SH.setStatus(OnlineStatus.ONLINE);
				Msg.AppInfo = SH.retrieveApplicationInfo().complete();
				Debug.out(Msg.AppInfo.getInviteUrl(Permission.ALL_TEXT_PERMISSIONS, Permission.MANAGE_CHANNEL)+"\n"+
						jda.getSelfUser().getName()+" has been started\n");
				
				ticker.start(SH);
			}
			else {
				Debug.err("Please provide a bot token as a Luanch option");
			}
		}catch (LoginException e) {
			Debug.err("Please ensure Login Token is valid");
			Debug.Trace(e);
		}catch (Throwable e) {
			Debug.Trace(e);
		}
	}
	
 
}

package bot.boot;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import bot.main.Event;
import bot.main.Msg;
import bot.timer.Tracker;
import bot.timer.ticker;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;

//import org.javacord.api.util.logging.ExceptionLogger;

//import net.dv8tion.jda.api.JDA;
//import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
public class Main {
	
	
	public static String token = "Put bot token here";
	
	public static void main(String[] args) {
		try {
			if (args!=null && args.length > 0) {
				for (String string : args) {
					if (string.trim().toLowerCase().contains("nocolour")) {
						Debug.Asciidebug = false;
					}
				}
			}
			if (Debug.Asciidebug) Debug.out("If you see Ascii controll characters. Launch with the perameter \"nocolour\" to turn them off\nJDA ignores formatting regardless.");
			
			
			
			Debug.out("Starting Bot");
			DefaultShardManagerBuilder builder = 
					new DefaultShardManagerBuilder()
		    			.setToken(token)
		    			.addEventListeners(new Event())
		    			.setShardsTotal(-1);
	
		    ShardManager SH = builder.build();
		    
		    
		    
		    
		   // List<JDA>jdalist = 
			//JDA jda =jdalist.get(0);
			
			// Note: It is important to register your ReadyListener before building
			//JDA jda = new JDABuilder(token)
			//			//.useSharding(0, 1)
			//			.setActivity(f[(int) Math.abs(System.currentTimeMillis() % f.length)])
			//           .addEventListeners(new Event())
			//            .build();
		    
			// optionally block until JDA is ready
		    JDA jda =SH.getShardCache().asList().get(0);
		   	jda.awaitReady();
		   	SH.setStatus(OnlineStatus.ONLINE);
			Msg.AppInfo = SH.retrieveApplicationInfo().complete();
			Debug.out(Msg.AppInfo.getInviteUrl(Permission.ALL_TEXT_PERMISSIONS, Permission.MANAGE_CHANNEL)+"\n"+
					jda.getSelfUser().getName()+" has been started\n");
			
			ticker.start(SH);
			
			//PrintStream PS = System.console().writer().;
			Runtime.getRuntime().addShutdownHook(
					new Thread(){
						public void run(){
							try {
								//System.console().writer().print("Please use /stop");
								//System.out.print("Please use /stop");
								//System.out.flush();
								if (Tracker.alive) Runtime.getRuntime().exec("java -jar stop.jar "+Main.token).getOutputStream();
								
								//Scanner Log = new Scanner(Runtime.getRuntime().exec("java -jar stop.jar "+Main.token).);
								//String A = "";
								//while (Log.hasNext()) {
								//	A = A + Log.nextLine() + "\n";
								//}
								//Debug.out(A);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				); 
			
			
			
			
			//Timer timer = new Timer();
			//timer.schedule(new ChangeAct(SH), 0, 20000);
		}catch (LoginException e) {
			Debug.err("Please ensure Login Token is valid");
			Debug.Trace(e);
		}catch (Throwable e) {
			Debug.Trace(e);
		}
	}
	
 
}

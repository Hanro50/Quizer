package bot.com.any;

import bot.com.ComObj;
import bot.main.Msg;
import bot.main.Print;
import bot.timer.Tracker;
import net.dv8tion.jda.api.JDA;

import static bot.boot.Debug.*;

public class Stop extends ComObj {
	public Stop() {
		super(Type.Text, Place.Any, Permlv.BotOwn);
		// TODO Auto-generated constructor stub
	}

	protected String com() {
		// TODO Auto-generated method stub
		return "stop";
	}

	@Override
	public void Run(Msg m) {
		// TODO Auto-generated method stub
		if (m.isBotOwner()) {
			
			m.channel.sendMessage("Shutting Down").complete();
			
			SHUTDOWN(m.jda); 
		}
		else {
			new Print(m, " %U Normal User may not shut down bot!");
			out("("+m.sender.getId()+") " + m.sender.getAsMention() + "tried to shut down the server");
		}
	}

	@Override
	public String Help() {
		// TODO Auto-generated method stub
		return "(Bot Owner Only) Terminate Bot";
	}
	static boolean  shutdown = false;
	
	
	public static void SHUTDOWN(JDA jda) {
		
		//System.exit(0);
		if (!shutdown) {
			Tracker.shutdown();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				 //TODO Auto-generated catch block
				e.printStackTrace();
			}
			jda.shutdown();
			out("\nShutting Down!");
			shutdown = true;
			System.exit(0);
		}
	}

}

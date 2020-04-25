package bot.timer;

import java.util.Random;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.ShardManager;

public class ActivityChanger {
	static Random rand;
	static ShardManager SHGlobal;
	static {
		rand = new Random();
		rand.setSeed(System.currentTimeMillis());
	}
	
	final static Activity[] f = {	
		Activity.watching("oblivion"), Activity.listening("nothing"), Activity.playing("with the void"), 
		Activity.watching("humans"), Activity.listening("passage of time"), Activity.playing("with the laws of physics"),
		Activity.watching("reality"), Activity.listening("music"), Activity.playing("with the unknown"),
		Activity.watching("a new series"), Activity.listening("a new jam"), Activity.playing("a new song"),
		Activity.watching("a cow"), Activity.listening("a cat's pur"), Activity.playing("with a puppy"),
		Activity.watching("C"), Activity.listening("Object Pascal"), Activity.playing("with Java"),
		Activity.watching("Windows"), Activity.listening("Mac OSX"), Activity.playing("with Linux"),
		Activity.watching("the world sleep"), Activity.listening("the world's song"), Activity.playing("within reality"),
		Activity.watching("for birds"),Activity.listening("still Alive"),Activity.playing("portal")};
	static public void SetSH(ShardManager setSH) {
		SHGlobal=setSH;
	}
	static public void ChangAct() {
		SHGlobal.setActivity(f[rand.nextInt(f.length)]);
	}
	
	static public void ChangAct(ShardManager SH) {
		SH.setActivity(f[rand.nextInt(f.length)]);
	}
	
	static public void ChangAct(JDA jda) {
		jda.getPresence().setActivity(f[rand.nextInt(f.length)]);
		
	}
}

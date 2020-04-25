package bot.timer;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bot.boot.Debug;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDA.Status;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.ShardManager;

public class ticker extends TimerTask {
	public static ticker start(ShardManager SH) throws InterruptedException {
		return new ticker(SH);
	}
	
	public ticker(ShardManager SH) throws InterruptedException {
		ActivityChanger.SetSH(SH);
		Tracker.SetshardManger(SH);
		Timer timer = new Timer();
		boolean booting = true;
		while (booting) {
			int Running = 0;
	    	  List<JDA>jdalist = SH.getShardCache().asList();
	    	  for (JDA jda : jdalist) {
	    		  if (jda.getStatus() == Status.CONNECTED) Running++;
	    	  }
	    	  long size = jdalist.size();
	    	  
	    	  if (Running >= (size/2)) {
	    		  if (!Tracker.updatelist("```starting up : [" +Running +"/"+ size + "]```")) {
	    			  Debug.out("starting up : [" +Running +"/"+ size + "]");
	    		  };
	    		  SH.setActivity(Activity.playing("startup chime : [" +Running +"/"+ size + "]"));
	    	  }
	    	  if (Running >= size) {
	    		  booting  = false;
	    		  Debug.out("Booted");
	    		  ActivityChanger.ChangAct();
	    	  }
	    	  Thread.sleep(1000);
		}
		Tracker.boot = false;
		timer.schedule(this, 1000, 10000);
	}
	int i = 0;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (i>30) {ActivityChanger.ChangAct(); i =0;};
		i++;
		Tracker.Update();
		
	}

}

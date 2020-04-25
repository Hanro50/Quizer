package bot.main;

import java.io.IOException;

import javax.annotation.Nonnull;

import bot.boot.Debug;
import bot.com.Handler;
import bot.com.any.Stop;
import bot.usr.UsrObj;
import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.ExceptionEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ReconnectedEvent;
import net.dv8tion.jda.api.events.ResumedEvent;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.events.StatusChangeEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class Event extends ListenerAdapter{
	
	public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
		try{
			Msg m = new Msg(event);
			if (m.isMe()) return;
			if (!m.isCom()) {
				try {
					UsrObj usr = UsrObj.Load(m);
					usr.doAct(m);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Debug.err("IOException");
				}
			}
			Handler.com(m);
		}catch (Throwable e) {
			Debug.Trace(e);
		}
	}
	
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
    	try{
	    	Msg m = new Msg(event);
	    	if (m.isMe()) return;
	    	Debug.out("React");
	    	Handler.com(m);
    	}catch (Throwable e) {
			Debug.Trace(e);
		}
    }
    public void onReady(@Nonnull ReadyEvent event) {
    	
    }
    public void onResume(@Nonnull ResumedEvent event) {
    	
    }
    public void onReconnect(@Nonnull ReconnectedEvent event) {
    	
    }
    public void onDisconnect(@Nonnull DisconnectEvent event) {
    	
    }
    public void onShutdown(@Nonnull ShutdownEvent event) {
    	
    	Stop.SHUTDOWN(event.getJDA());
    }
    public void onStatusChange(@Nonnull StatusChangeEvent event) {
    	
    }
    public void onException(@Nonnull ExceptionEvent event) {
    	if (!event.isLogged()) Debug.Trace(event.getCause());
    } 
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	//@Override
	//public void onMessageCreate(MessageCreateEvent event) {

	//	Msg m = new Msg(event);	
		
	//		Handler.list();
		
	//}

	//@Override
	//public void onReactionAdd(ReactionAddEvent event) {
	//	Msg m = new Msg(event);
	//}

	//@Override
	//public void onEvent(GenericEvent event) {
	//	// TODO Auto-generated method stub
		
	//}

	

}

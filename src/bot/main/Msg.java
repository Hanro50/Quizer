package bot.main;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ApplicationInfo;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageReaction.ReactionEmote;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public class Msg {
	static public final String comline = "/";
	static public  ApplicationInfo AppInfo; 
	
	public final JDA jda;
	public final User sender;
	public final Guild server;
	public final Message message;
	public final MessageChannel channel;
	public final ReactionEmote reactemote;
	
	//static public Msg Make(MessageCreateEvent event) {
	//	event.getMessageAuthor()
	//	return	new Msg(event);
	//}
	//static public Msg Make(ReactionAddEvent event) {
	//	event.getUser()
	//	return	new Msg(event);
	//}
	public String DebugMen() {
		return "<"+sender.getId()+"> @"+sender.getAsTag();
	}
	
	
	public Msg(User sender,MessageChannel channel, Message message, ReactionEmote reactemote, Guild server){
		this.jda = sender.getJDA();
		this.sender = sender;
		this.message = message;
		this.server = server;
		this.channel = channel;
		this.reactemote = reactemote;
	}
	
	public Msg(User sender,MessageChannel channel, Message message, ReactionEmote reactemote){
		this.jda = sender.getJDA();
		this.sender = sender;
		this.message = message;
		this.channel = channel;
		this.reactemote = reactemote;
		this.server = null;
	}
	
	public Msg(User sender,MessageChannel channel, Message message, Guild server){
		this.jda = sender.getJDA();
		this.sender = sender;
		this.message = message;
		this.server = server;
		this.channel = channel;
		this.reactemote = null;
	}
	
	public Msg(User sender,MessageChannel channel, Message message){
		this.jda = sender.getJDA();
		this.sender = sender;
		this.message = message;
		this.channel = channel;
		this.reactemote = null;
		this.server = null;
	}
	
	public Msg(User sender, String message_Text){
		this.jda = sender.getJDA();
		this.sender = sender;
		this.message = new MessageBuilder().setContent(message_Text).build();
		this.channel = getDM();
		this.reactemote = null;
		this.server = null;
	}
	
	public Msg(MessageReactionAddEvent event){
		this.jda = event.getJDA();
		this.sender = event.getUser();
		this.channel = event.getChannel();
		this.message = channel.retrieveMessageById(event.getMessageId()).complete();
		this.reactemote = event.getReactionEmote();
		if (isServer()) this.server = event.getGuild(); 
		else this.server = null;
	}
	
	public Msg(MessageReceivedEvent event){
		this.jda = event.getJDA();
		this.sender = event.getAuthor();
		this.message = event.getMessage();
		this.channel = event.getChannel();
		if (isServer()) this.server = event.getGuild(); 
		else this.server = null;
		this.reactemote = null;
	}
	
	public MessageChannel getDM() {
		try {if (isPrivate()) return channel;}catch (UnsupportedOperationException e) {}
		if (sender.getIdLong() == jda.getSelfUser().getIdLong()) return AppInfo.getOwner().openPrivateChannel().complete();
		else return sender.openPrivateChannel().complete();
	}
	
	public static MessageChannel getDM(User user) {
		if (isMe(user)) return AppInfo.getOwner().openPrivateChannel().complete();
		else return user.openPrivateChannel().complete();
	}
	
	public Member getMember() {
		if (!isServer()) return null;
		return server.getMember(sender);
	}
	
	public boolean isReactCom() {
		return (reactemote != null);
	}
	
	public boolean isCom() {
		if (message.getContentRaw() == null || message.getContentRaw().length() < comline.length()) return false;
		return (message.getContentRaw().substring(0, comline.length()).equals(comline));
	}
	
	public boolean chkCom(String text) {
		if (!isCom()) return false; 
		return message.getContentRaw().trim().toLowerCase().substring(comline.length()).contains(text.toLowerCase().trim());
	}
	
	public String getCom() {
		if (!isCom()) return ""; 
		return message.getContentRaw().split(" ", 2)[0].trim().toLowerCase().substring(comline.length());
	}
	
	public String getText() {
		if (!isCom()) return message.getContentRaw(); 
		String[] s = message.getContentRaw().split(" ", 2);
		if (s ==null || s.length < 2) return "";
		
		return message.getContentRaw().split(" ", 2)[1];
	}
	
	public String getRaw() {
		return message.getContentRaw(); 
	}
	
	public boolean isBot() {
		return sender.isBot();
	}
	
	public boolean isServer() {
		return  channel.getType().isGuild();
	}
	
	public boolean isPrivate() {
		return message.isFromType(ChannelType.PRIVATE);
	}
	
	public boolean isGroup() {
		return message.isFromType(ChannelType.GROUP);
	}
	
	static public boolean isMe(User user) {
		return user.getIdLong() == AppInfo.getJDA().getSelfUser().getIdLong();
	}
	
	public boolean isMe() {
		return sender.getIdLong() == jda.getSelfUser().getIdLong();
	}
	
	public boolean hasPerms(Permission Perms) {
		if (!isServer()) return false;
		return getMember().getPermissions().contains(Perms);
	}
	
	public boolean isAdmin() {
		if (!isServer()) return isBotOwner();
		return (server.getOwnerIdLong() == sender.getIdLong()) || hasPerms(Permission.ADMINISTRATOR);
	}
	
	public boolean isBotOwner() {
		return (sender.getIdLong() == AppInfo.getOwner().getIdLong());
	}
	
	
}

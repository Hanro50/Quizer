# Quizer v0.0.1-ALPHA
A simple Discord bot I wrote in my free time for my local High School. 

<h3> Required Dependencies </h3> 
<ul>
 <li>JDA (<a href="https://github.com/DV8FromTheWorld/JDA">4.1.1_101</a>)</li>
 <li>GSON (<a href="https://github.com/google/gson">2.8.6</a>)</li>
</ul>
<h3> Optional Dependencies </h3> 
<ul>
 <li>LOG4J (Tested with version <a href="https://logging.apache.org/log4j/2.x/">1.13.1</a>. Read JDA's <a href="https://github.com/DV8FromTheWorld/JDA/wiki/10)-FAQ#why-is-there-a-warning-from-slf4j-when-starting-up">documentation</a> for more detail)</li>
</ul>
<h3> Setup </h3> 
<ol>
 <li>Open the package bot.boot</li>
 <li>Open Main.java</li>
 <li>Change "Put bot token here" for a discord bot token (<a href = "https://github.com/reactiflux/discord-irc/wiki/Creating-a-discord-bot-&-getting-a-token">click here for more info</a>)</i>
 <li>Hook up the required dependencies and compile</li> 
</ol>
<h3> Usage </h3> 
 <p>This bot will save it's files to a subfolder within the same directory it's jar is located in that carries the same name as the Application's name the bot belongs to.</p>
 <p>Quizes have no limit to the amount of questions they can contain. Although multiple choice questions are limited to 26 choices per question.</p>
 <p>Do /help for a list of commands. By default no "mod role" is assigned. So do /permadd (role/user as a mention) to add a moderator. Commands you cannot use are hidden by default. (Do /help all to see them)</p>
 <p> Sending the command "/load" with a quizz file as an attachment to that message will cause the bot to compile that quiz. See "Template.cqf" for an example of a quizz file. (These files are checked and the bot will not save incorrectly formated quizzes)</p>
 <p> A student can do a quiz by going into the bot's DMs and sending it a "/do" command followed by the quizz's name . You can also link to a quiz within a text channel by doing "/link" followed by the quizz name</p>
 <p> The bot will prompt for a student's name when said student first interacts with the bot. Discord tags and name changes are tracked to prevent identity theft. If a student incorrectly filled in their information. Then that can be corrected by doing "/SetName" in the bot's DMs </p>
 <p> "/notify" will send a DM to a mentioned user or everyone with a mentioned role. This command is restricted to people with the admin Discord permission due to the potential for abuse and the risk of slowing the bot as a whole down due to rate limits. This is meant to give students a push to start interacting with the bot within it's DMs</p> 
 <p> This bot is not designed for students to take full blown exams and stuff on it. It has no way of checking if a student is actually answering the questions or if someone else is.</p>
<h3> Architecture </h3> 
<h4> Overview </h4> 
 <p>The Bot takes in "onMessageReceived '' and "onMessageReactionAdd" events from JDA. The information from these events create the Msg object. Which carries a bunch of some common data used by various functions as data passes thru the entire bot. The first thing that gets called after that is the User state machine (Will be ignored if the message is detected to be a command). After that it gets passed onto the command handler </p>
<h4> User State Machine </h4>
 <p>By default this comes preloaded with 2 possible states (DoQuizAct and Dosetname) plus a third null state the machine defaults to when it has no data to process. If you're planning to add your own states. Please ensure you give said state a unique class name. Gson by default cannot properly save abstract classes well, so the names of the classes the states are used to reference them. Said stats are kept in Package "bot.usr.act"</p> 
<h4> Command Handler </h4>
 <p>You can find the Handler in package bot.com. Within the same package you will find "ComObj". This abstract class is the base class for all the commands the bot is capable of understanding. It stores 4 enum objects. enum "Type" tells the command handler what type of command (A Reaction command or a Text command). Next is place, this can be 1 of 7 values depending on where you want a set command to be usable. Next is Permlv (all, Mod, Admin, BotOwn). "all" means everyone can use it. Mod means people who  are marked as a mod can use it. Admin restricts the command to only those with the "Administrator" permissions within Discord and BotOwn will limit it to the owner of the bot. Last is "Visible" (yes/no) Just determines if /help will pick it up. </p> 
<p>Ti add a command you need to add an instance of it to the "List < ComObj > ComObjList" within the command handler's class (bot.com.Handler.java). Command classes are split into 4 packages, to maintain some level of organisation, based on where they can be used(Private->bot.com.usr|Guild/Server->bot.com.server|Groups->bot.com.group). Although supported, no command present implemented uses hybrid locations, such as say "Server and Private only". Groups are also supported, but no command provided with this bot implements it. </p> 
<p>Some data fields of Msg can be null. For instance in case the bot receives a request from a group or private message channel. the "server" property will be null. Run the appropriate checks as needed to avoid null pointer exceptions.</p> 
<h4> Output </h4>
<p>Check out "Print.java" in bot.main. It handles basic message output, making sure to split up messages that go over the 2000 character limit. As for debug output, that's handled by "Debug.java". File Handling is kept to FileObj.java</p>
<h4> The Msg object </h4>
<p>This object maintains a fair amount of common data sent around during a message request and acts as a partial binder to the "event" objects present in JDA."comline"(static) is the command parameter of the bot. Like the slash you see in front of various commands listed here. "AppInfo"(static) is a copy of a "ApplicationInfo'' object from jda(contains some data about the bot)</p>  
<p>"jda" the JDA object of the event. "sender" the user object of the user responsible for a set event.(Author of the message if it is a text message or the person who sent a reaction).The "server" property is the Guild/server the event took place in(is null if the event took place somewhere else). The "message" property is a copy of the message object from the event. Channel is the channel object from JDA the message was sent in and finally "reactemote" is the reaction emote sent by a reaction event(null when it comes to a message event).</p>
<h3> General notes </h3> 
 <ul>
 <li>This was programed and tested on <a href ="https://ubuntu.com/">Ubuntu 20.04</a></li>
 <li>I have a public test server: (<a href="https://discordapp.com/invite/B63GHsW">HTSPI</a>) </li>
 <li>I might have messed up with spelling here and there.</li>
</ul>




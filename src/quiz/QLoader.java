package quiz;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static quiz.QUtil.*;

import bot.boot.Debug;
import bot.boot.FileObj;
import bot.main.Msg;
import bot.main.Print;
import net.dv8tion.jda.api.entities.Message.Attachment;

public class QLoader implements Runnable {
	
	private Msg m;
	private long T;
	private long timestamp() {return (long) ((System.nanoTime()-T) * 0.000001);}
		
	public QLoader(Msg m){
		this.m = m;
		this.T = System.nanoTime();
		(new Thread(this)).start();
	}
		
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Attachment Atch = m.message.getAttachments().get(0);
		//Turns out my new reader algorithm reads anything...So this check should keep stuff stable
		if (Atch.isImage() || Atch.isVideo()) {
			m.message.addReaction("U+274C").queue();
			Print.Err(m,("Invalid file format detected\n"
				+ "You cannot upload videos or Images!\n"
				+ "Line 1 also needs to contain \"head:\"\n"
				+ "This is done to prevent internal errors"));
			return;
		}
		String path = FileObj.ClassPath + "Downloads/";
		FileObj.FileChk(path);
		
		m.message.addReaction("U+1F44D").queue();
		Atch.downloadToFile(path + Atch.getFileName())
			.thenAccept(file -> fileread(file))
			.exceptionally(t ->
				{ // handle failure
					t.printStackTrace();
					Print.Err(m,"Critical Error loading file : \n" + t + " \n(Error handler failed to pick it up)");
		   
					//m.error(e);
					//m.err("Critical Error loading file : \n" + t + " \n(Error handler failed to pick it up)");
					//(L.getChannel()).sendMessage("Error").queue();
					return null;
				}
	     );
	}
		
	public void fileread(File file) {
		try {
			String F = FileObj.read(file, " \n ") + " \n Q<Dummy Qeustion to fix annoying bug>";
			String Name = (m.getText().length() < 3)? (file.getName()+".l.l").split("\\.")[0] : m.getText();
				
			System.out.println("\n["+this.getClass().getName()+"}\n"+  F);
			//System.out.print((F.split(" \n ")[0].toLowerCase().contains("head")?F:"awaiting error:"+F.split(" \n ")[0]));
				
				
			Compile(m.sender.getIdLong(), F.split(" \n "), Name);
			//T = (long) ((System.nanoTime()-T) * 0.000001) ;
			new Print(m," %U **Succesfully added Quizz** \""+Name.trim()+ "\" in " + timestamp() + " ms");
			
		} catch (IOException e) {
			e.printStackTrace();
			Print.Err(m,"[Internal IO Exception] \nCould not save file" + e.getMessage());
		}
		catch (Exception e) {
			Print.Err(m,"Quiz didn't compile ("+timestamp() + " ms). \n" + e.getMessage());
			Debug.err("Exception");
		}
		if (!file.delete()) file.deleteOnExit();
	}
	
	public static void Compile(long author, String[] data, String Name) throws Exception, IOException {
		boolean valid = true;
		boolean automark = false;
		boolean retakeable = true;
		String error = "";
		int type = 0;
		
		
		//Basic data checks
		if (data.length <= 6) {
			error = error + "\nFile needs to be 6 lines or more";
			valid = false;
		}
	
		if (!data[0].toLowerCase().contains("head:")) {
			error = error + "\n(L:1)format error \"Header section invalid\"";
			valid = false;
		}
		
		//Reading the header file
		int ih = 0;
		Headerloop: {
			for (; ih <data.length; ih++) {
				try  {
					if (!((data[ih] == null)||(data.length <=0))) {
						if (data[ih].toLowerCase().contains("data:")){
							ih++;
							break Headerloop;
						}
						else if (data[ih].toLowerCase().split(" ")[0].contains("type")) {
							type = Integer.valueOf(data[ih].toLowerCase().split(" ")[1]);
						}
						else if (data[ih].toLowerCase().split(" ")[0].contains("automark")) {
							automark = Boolean.valueOf(data[ih].toLowerCase().split(" ")[1]);
						}
						else if (data[ih].toLowerCase().split(" ")[0].contains("retakable")) {
							retakeable = Boolean.valueOf(data[ih].toLowerCase().split(" ")[1]);
						}
					}
				}
				catch (Error e) {
					error = error + "\n(L:"+(ih+1)+") format error: \"Invalid parameter value\"";
					valid = false;
				}
			}
		}
		
		//Second stage Variables
		Question temp = null;
		String choices = "";
		char L = 'A'; 
		List<Question> Compiler = new ArrayList<Question>();
		
		
		//Second stage
		for (int i = ih; i < data.length; i++) {
			String line = data[i];
			if (line.length() >= 1) {
				if ((line.toLowerCase().charAt(0) == 'q') && (L != 'Q')) {
					//Save previous Question 
					if (temp != null) {
						//Saving choices
						temp.choices = choices.split("\n");
						//Error checking 
						if ((temp.choices.length < 1) && (type == 0)) {
							error = error + "\n(L:"+(i+1)+")format error (Multiple choice Question has no awnsers provided)";
							valid = false;
						}
						//Finally saving temp Question
						temp.type = type;
						Compiler.add(temp);
					}
					temp = new Question();
					//Auto Mark functionality
					if (automark && (type == 0)) {
						temp.awnser = line.toUpperCase().charAt(1);
						temp.Qstatement = line.substring(2);
					}
					else
						temp.Qstatement = line.substring(1);
					//Resetting L
					L = 'A';
					choices = "";
				}
				//Adding choices 
				else if ((line.toUpperCase().charAt(0) == L) && (type == 0)) {
					L++;
					choices = choices + line + "\n";
				}
			}
		}
		
		if (valid) {
			if (exists(Name)) {
				String T = Name;
				int i = 1;
				do {
					Name = T + "_" + i;
				}while (exists(Name));
			}
			
			Quizz Q = new Quizz(author,Compiler.size(),type,automark, retakeable, Name);
			Q.setQuestion(Compiler);
			System.out.print("\n");
			Q.Save();
		}
		else throw new Exception(error);
	}
}

	
		
		
	


	

	

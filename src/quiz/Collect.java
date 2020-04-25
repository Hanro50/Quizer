package quiz;



import java.io.File;
import java.io.IOException;

import bot.boot.FileObj;
import bot.main.Msg;
import bot.main.Print;
import net.dv8tion.jda.api.entities.User;

public class Collect{

	Thread Qcol = null;
	static {
		FileObj.FileChk("Temp/");
	}
	Runnable R = new Runnable() {
		@Override
		public void run() {
			try {
				// TODO Auto-generated method stub
				String A = "";
				if (D!=null && D.length > 0) {
					for (int i = 0; i < D.length; i++) {
						String[] Res = FileObj.read(D[i]);
						Quizz Q = FileObj.fromjson(Res[Res.length-1], Quizz.class);
						A = A + "Basic Data about Quizz taker";
						for (int i2 = 0; i2 < Res.length-2; i2++) {
							A = A + "\n\t" + Res[i2];
						}
						A = A + "\n\n Results";	
						for (int i2 = 0; i2 < Q.getQNum(); i2++) {
							A = A + "\n\t" + Q.getQuestion(i2).Awnser();
						}
					}
						
					//end of file compile;
					//char[] C = A.toCharArray();
					//int size = 
				
				
					File[] F = new File[(int) ((float) A.length()/(7864320))+1];
					for (int i = 0; i < F.length; i++) {
						String AB[] = {""};
						if (A.length()>7864320) {
							AB[0] = A.substring(0, 7864320);
							A = A.substring(7864320);
						}
						else AB[0] =A;
						
						F[i] = FileObj.Fetch("Temp/", Q.QName + "split_" + i, ".txt");
						
						FileObj.writeNF(AB, F[i],"temp");
						
						//final int ifin = i;
			
						
						Msg.getDM(usr).sendMessage("Final result files").addFile(F[i]).complete();
						//m
						//openPrivateChannel()
						//	.flatMap((channel) -> 
						//		channel	.sendMessage("Final files")
						//				.addFile(F[ifin]))
						//	.complete(); // send message
						F[i].delete();
							
					}
					//m.msg().addReaction("U+274C").queue();
				}
				else new Print(Msg.getDM(usr),usr,"No results")  ;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	};
	File[] D;
	File T;
	Quizz Q;
	User usr;
	public Collect(User usr,Quizz quiz) {
		// TODO Auto-generated method stub
			//T = ObjFile.Fetch("Temp/", Q.QName, "tmp");
			this.usr = usr;
			D = FileObj.FileList(Q.AwnPath(), Quizz.awnext);
			Qcol = new Thread(R);
			Qcol.start();
	}
		
		
		
	

}


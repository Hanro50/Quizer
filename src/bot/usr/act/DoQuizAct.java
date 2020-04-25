package bot.usr.act;

import java.io.IOException;

import com.google.gson.annotations.Expose;

import bot.boot.Debug;
import bot.main.Msg;
import bot.main.Print;
import bot.usr.Activity;
import bot.usr.UsrObj;
import quiz.QUtil;
import quiz.Quizz;

public class DoQuizAct extends Activity {
	@Expose Quizz currQ;
	@Expose int currQnum;
	@Expose boolean CanChange = false;
	
	public DoQuizAct(Quizz quizz) {
		currQ =quizz;
		currQnum = 0;
	}
	
	
	@Override
	public void init(Msg m,UsrObj parent) {
		if (currQ.Hasbeentaken(m)) {
			try {
				parent.clrAct();
				new Print(m,"This Quiz is not retakeable");
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				new Print(m,"Due to an unforseen error happened.\n Warning: Instability is likely");
				Debug.Trace(e);
			}
		}
		// TODO Auto-generated method stub
		askQ(m);
	}

	@Override
	public void Do(Msg m,UsrObj parent) {
		//System.out.println("DO "+m.getRaw());
		// TODO Auto-generated method stub
		if (!QUtil.exists(currQ.QName)) {
			Print.Err(m, "Quiz no longer exists.\nResetting...");
			try {
				parent.clrAct();
			} catch (IOException e) {
				Debug.Trace(e);
			}
		}
		
		if (CanChange && m.getText().toLowerCase().trim().contains("yes")) {
			try {parent.clrAct();} 
			catch (Exception e) {e.printStackTrace();}
			new Print(m,"Cancelling Quiz.");
			return;
		}
		CanChange = false;
		if (currQ == null) {
			Print.Err(m, "Save error");
			System.err.print("Save error");
			try {parent.clrAct();} 
			catch (Exception e) {e.printStackTrace();Debug.err("IOException");}
		}
		
		if (currQ == null) System.err.print("E");

		String Q = currQ.getQuestion(currQnum).usrAwn(m.getText());
		if (Q != null) {
			new Print(m,Q + "\nType \""+Msg.comline+"Cancel\" to Quite\nHere is the Question again");
			askQ(m);
			return;
		}
			
		currQnum++;
		if (currQnum >= currQ.getQNum()) {
			new Print(m," %U Congrats. You're done!");
			try {
				currQ.SaveAwn(m, parent);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				Debug.Trace(e1);
				Print.Err(m, "Could not save files");
				//e1.printStackTrace();
			}
			//File F = Fileget(m); //new File("Awn/"+QName+"/"+m.Sender.getId()+".Score");
			
			
			
			//File F =new File("Awn/"+QName+"/"+user.user.getId()+".usr");
			
			//if (F.exists()) {
					//error = "Quizz already exists";
			//	return false;
			//}
				
			//if (!F.createNewFile()) {
			//	//error = "Couldn't save Quizz file";
			//	return false;
			//}
				
			//FileWriter writer = new FileWriter(F);
				
			//writer.write(json);
			//writer.flush();
			//writer.close();
				
			try {
				parent.clrAct();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Debug.err("Quiz compiler exception");
			} 	
			return;
		}
		askQ(m);
	}
	
	private void askQ(Msg m) {
		new Print(m," %U Question "+(currQnum+1)+"\n" + currQ.getQuestion(currQnum).as_string());
	}


	@Override
	public void CancelCall(Msg m, UsrObj parent) {
		// TODO Auto-generated method stub
		new Print(m,"Type \"Yes\" to Quit current Quiz");
		CanChange = true;
	}



}

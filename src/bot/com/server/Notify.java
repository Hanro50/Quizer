package bot.com.server;

import java.util.List;

import bot.boot.Debug;
import bot.com.ComObj;
import bot.main.Msg;
import bot.main.Print;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

public class Notify extends ComObj{
	static private List<Member> Mem;
	static int Count = 0;
	public Notify() {
		super(Type.Text, Place.Server, Permlv.Admin);
		
		
		
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String com() {
		// TODO Auto-generated method stub
		return "notify";
	}

	@Override
	public void Run(Msg m) {
		
		//Permission check
		if (!m.isAdmin()) {
			Print.Err(m,"Insufficient permissions");
			return;
		}
		System.out.println("");
		
		//Initial checks
		List<Role> RM = m.message.getMentionedRoles();
		//Did the user mention someone
		String Mes;
		if (RM.isEmpty()) {
			List<Member> MM = m.message.getMentionedMembers();
			//No...throw and error and return
			if (MM.isEmpty()){
				Print.Err(m,"No roles/user mentioned");
				return;
			}
			//Yes?...k
			try {Mes = mesgen(MM.size(), m);
			}catch (ArrayIndexOutOfBoundsException e){
				Print.Err(m, "No text provided");
				return;
			}
			
			
			for (Member member : MM) {
				User UM = member.getUser();
				Debug.out("Notifying: ("+UM.getId()+") <"+UM.getAsTag()+">");
				if (!(UM.isBot()||UM.isFake())) new Print(Msg.getDM(UM), UM, Mes );
			}	
			return;
		}
		//Lets update the member list...JDA said this was a bad idea to do at random
		if ((Mem == null) || (Mem.isEmpty()) || m.server.getMemberCount() != Count) {
			Mem = m.server.getMembers();
			Count = m.server.getMemberCount();
		}
		//Generate mess string
		
		try {
			Mes = mesgen(RM.size(), m);
		}catch (ArrayIndexOutOfBoundsException e){
			Print.Err(m, "No text provided");
			return;
		}
		for (int i = 0; i < Mem.size(); i++) {
			User Umem = Mem.get(i).getUser();
			List<Role> RU = Mem.get(i).getRoles();
			for (int i2 =0; i2<RM.size();i2++) {
				if (RU.contains(RM.get(i2))){
					Debug.out("Notifying: ("+Umem.getId()+") <"+Umem.getAsTag()+">");
					new Print(Msg.getDM(Umem), Umem, Mes);
					break;
				}
			}
		}
		
		
		//for (int i =0; i<R.size();i++);
	}

	@Override
	public String Help() {
		// TODO Auto-generated method stub
		return "(Admin only): Notifies everyone of a set role or a person that is mentioned";
	}
	private String mesgen(int num, Msg m) throws ArrayIndexOutOfBoundsException{
		return (m.getText().split(" ", 1+num))[num];
	}

}


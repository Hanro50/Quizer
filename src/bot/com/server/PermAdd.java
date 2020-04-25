package bot.com.server;

import java.io.IOException;

import bot.boot.Debug;
import bot.com.ComObj;
import bot.main.Msg;
import bot.main.Print;
import bot.perm.PermManager;

public class PermAdd extends ComObj {

	public PermAdd() {
		super(Type.Text, Place.Server, Permlv.Admin);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String com() {
		// TODO Auto-generated method stub
		return "permadd";
	}

	@Override
	public void Run(Msg m) {
		boolean worked = false;
		try {
		
		if (!m.message.getMentionedUsers().isEmpty()) {
			PermManager.AddUser(m, m.message.getMentionedUsers());
			worked = true;
		}
		
		if (!m.message.getMentionedRoles().isEmpty()) {
			PermManager.AddRole(m, m.message.getMentionedRoles());
			worked = true;
		}
	}catch (IOException e) {
		Debug.Trace(e);
	}
	if (!worked) {
		Print.Err(m,"Please mention a role or user");
	}
		
	}

	@Override
	public String Help() {
		// TODO Auto-generated method stub
		return "(Admin only) Make a user/role a mod";
	}

}

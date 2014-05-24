package x.client.frame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import x.client.ClientService;
import x.common.frame.BaseFrame;

public class LoginFrame extends BaseFrame {
	private static final long serialVersionUID = -757010185570699296L;
	public ClientService service;

	public LoginFrame() {
		super("µÇÂ¼");
		put("host", "127.0.0.1");
		put("port", 8888);
	}

	protected void create() {
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.RIGHT);
		setLayout(layout);
		add(new JLabel("ÕË    ºÅ£º"));
		add(new JTextField(20), "id");
		add(new JLabel("ÃÜ    Âë£º"));
		add(new JTextField(20), "password");
		add(new JButton("×¢    ²á"), "register");
		add(new JButton("Éè    ÖÃ"), "setting");
		add(new JButton("µÇ    Â¼"), "login");
		setCenter(320, 140);
	}

	protected void addHandler() {
		select("setting", JButton.class).addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						new SettingFrame(LoginFrame.this).setVisible(true);
					}
				});
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		select("register", JButton.class).addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						new RegisterFrame(LoginFrame.this).setVisible(true);
					}
				});
		select("login", JButton.class).addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						put("id", Integer.valueOf(select("id", JTextField.class).getText()));
						put("password", select("password", JTextField.class).getText());
						new FriendListFrame(LoginFrame.this).setVisible(true);
						LoginFrame.this.setVisible(false);
					}
				});
	}

}

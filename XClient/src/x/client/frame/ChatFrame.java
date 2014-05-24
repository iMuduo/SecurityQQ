package x.client.frame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import x.client.ClientService;
import x.common.frame.BaseFrame;
import x.common.info.MessageInfo;
import x.common.util.CheckHelper;

public class ChatFrame extends BaseFrame {

	private static final long serialVersionUID = -301580056847162134L;
	private final SimpleDateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private final int id;
	private final String name;

	public ChatFrame(String title, int id, BaseFrame parent) {
		super(title, parent);
		this.id = id;
		this.name = title;
	}

	@Override
	protected void create() {
		FlowLayout layout = new FlowLayout(5);
		layout.setAlignment(FlowLayout.RIGHT);
		setLayout(layout);
		ScrollTextArea content = new ScrollTextArea(12, 35);
		content.setEditable(false);
		add(content, "content");
		ScrollTextArea msg = new ScrollTextArea(5, 35);
		add(msg, "msg");
		add(new JButton("·¢ËÍ(ctrl+enter)"),"send");
		setSize(420, 390);
		setLocation(300, 100);
	}

	@Override
	protected void addHandler() {
		select("msg", ScrollTextArea.class).addKeyListener(new KeyAdapter() {
			private int key = 0;

			@Override
			public void keyReleased(KeyEvent e) {
				int code = e.getKeyCode();
				if (code != 10 && code != 17) {
					key = 0;
					return;
				}
				if (key == 0) {
					key = code;
					return;
				}
				if (key + code != 27) {
					key = 0;
					return;
				}
				send();
				key = 0;
			}
		});
		
		select("send", JButton.class).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});
	}
	
	private void send(){
		ScrollTextArea msg = select("msg", ScrollTextArea.class);
		if (CheckHelper.isNotNullOrEmpty(msg.getText())) {
			ClientService service = parent.get("service",
					ClientService.class);
			MessageInfo info=new MessageInfo(parent
					.get("id", Integer.class), id, parent.get("name",
					String.class), name, df.format(new Date()), msg
					.getText());
			service.send(info);
			select("content", ScrollTextArea.class).append(String.format(FriendListFrame.myTpl,info.from,info.time,info.msg));
			msg.setText("");
		}
	}
}

package x.client.frame;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import x.client.ClientService;
import x.client.IReceiver;
import x.common.exception.ErrorException;
import x.common.frame.BaseFrame;
import x.common.info.MessageInfo;
import x.common.info.Success;
import x.common.info.UserInfo;

public class FriendListFrame extends BaseFrame {
	private static final long serialVersionUID = 7932583190281006007L;
	private ClientService service;
	private List<UserInfo> list;
	private Map<Integer, BaseFrame> chatFrames = null;
	public final static String msgTpl="%1$-20s %2$-80s\n------------------------------------------------------------------------------------------------\n%3$s\n";
	public final static String myTpl="%2$80s %1$20s\n------------------------------------------------------------------------------------------------\n%3$s\n";
	public FriendListFrame(BaseFrame parent) {
		super("好友列表", parent);
		try {
			service = new ClientService(parent.get("host", String.class),
					parent.get("port", Integer.class));
			this.chatFrames = new HashMap<Integer, BaseFrame>();
			this.list = new LinkedList<>();
			load();
			put("service", service);
			put("id", parent.get("id", Integer.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void create() {
		setLayout(new FlowLayout());
		add(new JLabel(String.format("%1$-28s%2$-28s", "账号", "姓名")));
		JList<?> list = new JList<>();
		list.setSize(240, 550);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(list, "list");
		setCenter(240, 600);
	}

	protected void addHandler() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				service.close();
				System.exit(0);
			}
		});
		select("list", JList.class).addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting())
							return;
						UserInfo user = list.get(select("list", JList.class)
								.getSelectedIndex());
						BaseFrame frame = chatFrames.get(user.id);
						if (frame == null) {
							frame = new ChatFrame(user.name, user.id,
									FriendListFrame.this);
							frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
							chatFrames.put(user.id, frame);
						}
						frame.setVisible(true);
						frame.toFront();
						frame.shave();
					}
				});
	}

	private void load() {
		service.send(new UserInfo(parent.get("id", Integer.class), null, parent
				.get("password", String.class), null, true));
		service.start(new IReceiver() {
			private Vector<String> dataList = new Vector<>();

			@SuppressWarnings("unchecked")
			@Override
			public boolean receive(Object obj) {
				if (obj == null)
					return false;
				if (obj instanceof UserInfo) {
					UserInfo user = (UserInfo) obj;
					list.add(user);
					dataList.add(String.format("%1$-28d%2$-28s", user.id,
							user.name));
				} else if (obj instanceof Success) {
					put("name", obj.toString());
					select("list", JList.class).setListData(dataList);
				} else if (obj instanceof ErrorException) {
					JOptionPane.showMessageDialog(null,
							((ErrorException) obj).getMessage(), "错误",
							JOptionPane.ERROR_MESSAGE);
					return true;
				}else if(obj instanceof MessageInfo){
					MessageInfo info=(MessageInfo)obj;
					BaseFrame frame = chatFrames.get(info.fromid);
					if (frame == null) {
						frame = new ChatFrame(info.from, info.toid,
								FriendListFrame.this);
						frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						chatFrames.put(info.fromid, frame);
						frame.setVisible(true);
						frame.shave();
					}
					frame.select("content",ScrollTextArea.class).append(String.format(msgTpl, info.from,info.time,info.msg));
				}
				return false;
			}
		});
	}
}

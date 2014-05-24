package x.server;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import x.common.frame.BaseFrame;

public class ServerFrame extends BaseFrame {

	private static final long serialVersionUID = -5129879068073670289L;
	private ServerService service = null;

	public ServerFrame() {
		super("服务");
	}

	public void create() {
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.RIGHT);
		setLayout(layout);
		add(new JLabel("端口号："));
		add(new JTextField(20), "port");
		select("port", JTextField.class).setText("8888");
		add(new JButton("停    止"), "stop");
		select("stop", JButton.class).setEnabled(false);
		add(new JButton("启    动"), "start");
		setCenter(320, 100);
	}

	public void addHandler() {
		select("start", JButton.class).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ServerFrame.this.select("start", JButton.class).setEnabled(
						false);
				new Thread(new Runnable() {

					@Override
					public void run() {
						new ServerService(Integer.valueOf(ServerFrame.this
								.select("port", JTextField.class).getText()))
								.start();
					}
				}).start();
				ServerFrame.this.select("stop", JButton.class).setEnabled(true);
			}
		});

		select("stop", JButton.class).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ServerFrame.this.select("stop", JButton.class)
						.setEnabled(false);
				if (service != null) {
					service.stop();
					service = null;
				}
				ServerFrame.this.select("start", JButton.class)
						.setEnabled(true);
			}
		});
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}

package x.client.frame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import x.client.ClientService;
import x.client.IReceiver;
import x.common.exception.ErrorException;
import x.common.frame.BaseFrame;
import x.common.info.RegisterInfo;
import x.common.info.Success;
import x.common.util.CheckHelper;

public class RegisterFrame extends BaseFrame {

	private static final long serialVersionUID = -6841637176464819963L;

	public RegisterFrame(BaseFrame parent) {
		super("注册", parent);
	}

	protected void create() {
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.RIGHT);
		setLayout(layout);
		add(new JLabel("用户名："));
		add(new JTextField(20), "username");
		add(new JLabel("密    码："));
		add(new JTextField(20), "password");
		add(new JButton("取    消"), "cancel");
		add(new JButton("注    册"), "register");
		setCenter(320, 140);
	}

	@Override
	protected void addHandler() {
		select("cancel", JButton.class).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				RegisterFrame.this.dispose();
			}
		});
		select("register", JButton.class).addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						ClientService service = null;
						try {
							service = new ClientService(parent.get("host",
									String.class), parent.get("port",
									Integer.class));
							service.send(new RegisterInfo(select("username",
									JTextField.class).getText(), select(
									"password", JTextField.class).getText()));
							service.start(new IReceiver() {

								@Override
								public boolean receive(Object obj) {
									if (obj instanceof Success) {
										if (CheckHelper.isNotNullOrEmpty(obj)) {
											JOptionPane
													.showMessageDialog(
															null,
															"注册成功，账号为："+obj.toString(),
															"成功",
															JOptionPane.INFORMATION_MESSAGE);
											parent.select("id", JTextField.class).setText(obj.toString());
											return true;
										}
									} else
										JOptionPane.showMessageDialog(null,
												((ErrorException) obj)
														.getMessage(), "错误",
												JOptionPane.ERROR_MESSAGE);
									return false;
								}
							});
						} catch (Exception e2) {
						} finally {
							RegisterFrame.this.dispose();
						}
					}
				});
	}
}

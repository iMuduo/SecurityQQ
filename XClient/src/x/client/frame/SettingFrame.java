package x.client.frame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import x.common.frame.BaseFrame;

public class SettingFrame extends BaseFrame{
	private static final long serialVersionUID = -3251121153221239763L;
	public SettingFrame(BaseFrame parent){
		super("����",parent);
	}
	
	protected void create(){
		FlowLayout layout=new FlowLayout();
		layout.setAlignment(FlowLayout.RIGHT);
		setLayout(layout);
		add(new JLabel("��    ַ��"));
		add(new JTextField(20),"host");
		add(new JLabel("��    �ڣ�"));
		add(new JTextField(20),"port");
		add(new JButton("ȡ    ��"),"cancel");
		add(new JButton("ȷ    ��"),"confirm");
		String host=parent.get("host",String.class);
		select("host", JTextField.class).setText(host);
		select("port", JTextField.class).setText(parent.get("port",Integer.class).toString());
		setCenter(320, 130);
	}
	
	@Override
	protected void addHandler(){
		select("cancel", JButton.class).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SettingFrame.this.dispose();
			}
		});
		select("confirm", JButton.class).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.put("host", select("host", JTextField.class).getText());
				parent.put("port", Integer.valueOf(select("port", JTextField.class).getText()));
				SettingFrame.this.dispose();
			}
		});
	}
}

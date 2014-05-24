package x.client.frame;

import java.awt.Rectangle;
import java.awt.event.KeyListener;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ScrollTextArea extends JScrollPane {

	private static final long serialVersionUID = 1282419958964036120L;
	private JTextArea area;

	public ScrollTextArea() {
		super(new JTextArea());
		init();
	}

	private void init(){
		area=(JTextArea) getViewport().getComponent(0);
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		area.setWrapStyleWord(true);
	}

	public ScrollTextArea(int r, int c) {
		super(new JTextArea(r, c));
		init();
	}

	public void setEditable(boolean b) {
		area.setEditable(b);
	}

	public String getText() {
		return area.getText();
	}

	public void append(String str) {
		area.append(str);
		scrollToEnd();
	}

	public void setText(String str) {
		area.setText(str);
	}
	
	@Override
	public void addKeyListener(KeyListener l){
		area.addKeyListener(l);
	}
	
	private void scrollToEnd(){
		JScrollBar bar=getVerticalScrollBar();
		bar.setAutoscrolls(true);
		bar.scrollRectToVisible(new Rectangle(Integer.MAX_VALUE>>1, Integer.MAX_VALUE>>1));
		area.requestFocus();
	}
}

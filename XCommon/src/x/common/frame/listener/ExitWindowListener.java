package x.common.frame.listener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ExitWindowListener extends WindowAdapter{
	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(1);
	}
}

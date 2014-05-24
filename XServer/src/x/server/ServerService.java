package x.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import x.common.exception.ErrorException;
import x.common.info.MessageInfo;
import x.common.info.RegisterInfo;
import x.common.info.Success;
import x.common.info.UserInfo;
import x.common.io.LenInputStream;
import x.common.io.LenOutputStream;
import x.common.io.security.SecurityInputFilter;
import x.common.io.security.SecurityObjectInput;
import x.common.io.security.SecurityObjectOutput;
import x.common.io.security.SecurityOutputFilter;
import x.common.security.DESFilter;
import x.common.util.OnOff;
import x.common.util.RandomHelper;

public class ServerService {
    private int port;
    private final Map<Integer, Queue<Object>> out;
    private OnOff on;

    public ServerService(int port) {
	this.port = port;
	out = new HashMap<Integer, Queue<Object>>();
	this.on = new OnOff();
    }

    public void start() {
	ServerSocket server = null;
	try {
	    server = new ServerSocket(port);
	    while (on.isOn()) {

		final Socket client = server.accept();
		final LenOutputStream los = new LenOutputStream(client.getOutputStream());
		final LenInputStream lis = new LenInputStream(client.getInputStream());
		final byte[] key = RandomHelper.randomKey();
		los.write(key);
		System.out.print("°ä·¢ÃÜÔ¿:");
		System.out.write(key);
		System.out.print("\n");
		Thread service = new Thread(new Runnable() {
		    private UserInfo user;

		    @Override
		    public void run() {
			final SecurityObjectInput soi = new SecurityObjectInput(new SecurityInputFilter(lis, new DESFilter(), key));
			final SecurityObjectOutput soo = new SecurityObjectOutput(new SecurityOutputFilter(los, new DESFilter(), key));
			try {
			    while (true) {
				// register
				Object obj = soi.read();
				int id;
				if (obj instanceof RegisterInfo) {
				    if ((id = DBService.register((RegisterInfo) obj)) > 0)
					soo.write(new Success(id));
				    else
					soo.write(new ErrorException("×¢²áÊ§°Ü£¡"));
				} else if (obj instanceof UserInfo) {
				    user = (UserInfo) obj;
				    user.key = key;
				    if (DBService.login(user)) {
					if (out.get(user.id) == null)
					    out.put(user.id, new ConcurrentLinkedQueue<>());
					for (UserInfo u : DBService.getOnlineLists())
					    // send online list
					    soo.write(u);
					soo.write(new Success(user.name));
					System.out.print(user.name + "µÇÂ¼³É¹¦\n");
					new Thread(new Runnable() {// send
						    @Override
						    public void run() {
							try {
							    while (user != null) {
								Queue<Object> queue = out.get(user.id);
								while (!queue.isEmpty())
								    soo.write(queue.poll());
								Thread.sleep(100);
							    }
							} catch (Exception e) {
							    e.printStackTrace();
							}
						    }
						}).start();
				    } else
					soo.write(new ErrorException("µÇÂ¼Ê§°Ü£¡"));
				} else if (obj instanceof MessageInfo) {
				    MessageInfo info = (MessageInfo) obj;
				    if (out.get(info.toid) == null)
					out.put(info.toid, new ConcurrentLinkedQueue<>());
				    out.get(info.toid).add(info);
				} else
				    break;
			    }

			} catch (Exception e) {
			    if (soo != null && user != null)
				soo.write(new ErrorException("·þÎñ¶Ë´íÎó£¡"));
			} finally {
			    soo.close();
			}
		    }
		});

		service.start();
	    }
	} catch (IOException e) {

	} finally {
	    try {
		if (server != null)
		    server.close();
	    } catch (IOException e1) {
		e1.printStackTrace();
	    }
	}
    }

    public void stop() {
	this.on.setOff();
    }
}

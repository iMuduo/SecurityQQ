package x.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import x.common.io.LenInputStream;
import x.common.io.LenOutputStream;
import x.common.io.security.SecurityInputFilter;
import x.common.io.security.SecurityObjectInput;
import x.common.io.security.SecurityObjectOutput;
import x.common.io.security.SecurityOutputFilter;
import x.common.security.DESFilter;
import x.common.security.ISecurityFilter;
import x.common.util.OnOff;

public class ClientService {
	private final Socket socket;
	private byte[] key;
	private final ISecurityFilter filter;
	private final OnOff on;
	private final Queue<Object> out;

	public ClientService(String host, int port) throws IOException {
		this.filter = new DESFilter();
		this.on = new OnOff();
		socket = new Socket(host, port);
		out = new ConcurrentLinkedQueue<>();
	}

	public void start(final IReceiver receiver) {
		try {
			final LenInputStream lis = new LenInputStream(
					socket.getInputStream());
			final LenOutputStream los = new LenOutputStream(
					socket.getOutputStream());
			key = lis.read();// read key
			Thread read = new Thread(new Runnable() {
				@Override
				public void run() {
					SecurityObjectInput soi = null;
					try {
						while (on.isOn()) {
							soi = new SecurityObjectInput(
									new SecurityInputFilter(lis, filter, key));
							if(receiver.receive(soi.read()))
								ClientService.this.close();
							Thread.sleep(100);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (soi != null)
							soi.close();
					}
				}
			});

			Thread write = new Thread(new Runnable() {
				@Override
				public void run() {
					SecurityObjectOutput soo = null;
					try {
						soo = new SecurityObjectOutput(
								new SecurityOutputFilter(los, filter, key));
						while (on.isOn()) {
							while (!out.isEmpty())
								soo.write(out.poll());
							Thread.sleep(100);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (soo != null)
							soo.close();
					}
				}
			});
			read.start();
			write.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(Object obj) {
		out.add(obj);
	}

	public void close() {
		on.setOff();
	}
}

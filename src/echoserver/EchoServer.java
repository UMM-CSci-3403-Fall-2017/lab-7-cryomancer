package echoserver;

import java.io.*;
import java.net.*;

public class EchoServer implements Runnable {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException, InterruptedException {
		try{
			//set up socket server
			ServerSocket iSocketXetServe = new ServerSocket(6013);
			
			ExecutorService threaded = Executors.newFixedThreadPool(2);
			
			//executing new thread for sockets
		while(true){
			Socket newSocket2_0 = iSocketXetServe.accept();
			badName ThreadServer = new badName(newSocket2_0);
			threaded.execute(ThreadServer);
		}
		//expeception handling with conveniet failure tag
	}catch (IOException FAILURE){
		System.err.println(FAILURE);
	}
	}


	//terrible naming scheme 
	public static class badName extends Thread {
		Socket iSocketX;
		public badName(Socket iSocketX) {
			this.iSocketX = iSocketX;
		}

		//Setting up input and output stream.
		public void run() {
			try{
				OutputStream out = iSocketX.getOutputStream();
				InputStream in = iSocketX.getInputStream();
				
				int Done;
				while((Done = in.read()) != -1) {
					out.write(Done);
				}
			
				out.flush();
				iSocketX.shutdownOutput();
				iSocketX.close();
			} catch (IOException CONTINUOUS_FAILURE) {
				System.err.println(CONTINUOUS_FAILURE);
			}
		}
	}
}
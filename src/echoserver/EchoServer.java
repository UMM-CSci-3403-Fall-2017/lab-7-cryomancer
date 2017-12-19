package echoserver;

import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {

    public static void main(String[] args){
        try {
        	//Starting socket server
            ServerSocket socketServe = new ServerSocket(6013);
			//starting thread pool
            ExecutorService pooledThread = Executors.newFixedThreadPool(32);

            while(true){
            	//running the server while true
                System.out.println("new request please");
                Socket client = socketServe.accept();

                EchoThread echoThread = new EchoThread(client);
                pooledThread.execute(echoThread);
            }
            // throw the exception Failure if the above statements are incorrect
        } catch (IOException Failure){
            System.err.println(Failure);
        }
    }

    //function to set up the echo response
    public static class EchoThread extends Thread {
    	//setting up  new socket for the client.
    	Socket client;
    	public EchoThread(Socket client){
    		this.client = client;
		}
    	//setting up the two streams read and output respectively
		public void run(){
			try {
				OutputStream out = client.getOutputStream();
				InputStream read = client.getInputStream();
				
				//setting up the nextByte (nb)
				//if the nextByte isn't a negative value continuously write out the nb value.
				int nb;
				while((nb = read.read()) != -1){
					out.write(nb);
				}
				
				//close down the sockets and flush the system
				out.flush();
				client.shutdownOutput();
				client.close();
				
				//throw exception Failure if none of this works.
    		} catch (IOException Failure){
    			System.err.println(Failure);
			}
		}
	}
}
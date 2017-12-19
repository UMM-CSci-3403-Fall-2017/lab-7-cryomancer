package echoserver;

import java.net.*;
import java.io.*;

public class EchoClient {

    public static void main(String[] args) throws IOException{
            
    	//set up the new socket to the ip address and port listed
    	Socket socketServe = new Socket("127.0.0.1", 6013);

    	//initialize the stream going to and coming from the server
            InputStream from = socketServe.getInputStream();
            OutputStream to = socketServe.getOutputStream();

			/*
			* Start one thread to send information to the EchoServer
			* and another to receive information from the EchoServer
			* This reduces the time the streams spend waiting for each other to finish reading and writing
			*/
            
            //thread initialization for the send thread and receive thread
            //both interacting with the socket server.
			Threader send = new Threader(System.in, to, socketServe);
			Threader receive = new Threader(from, System.out, socketServe);
			send.start();
			receive.start();
	}

    public static class Threader extends Thread{
    	//initialize variable in, out, socketServer;
    	InputStream in;
    	OutputStream out;
    	Socket socketServer;
    	
    	//creating constructor for threader used in main
    	public Threader(InputStream in, OutputStream out, Socket socket){
    		this.in = in;
    		this.out = out;
    		this.socketServer = socket;
		}
    	
    	//similar to the other file, reads input and writes to output
    	

		public void run(){
    		try {
    			int nb;
    			while ((nb = in.read()) != -1){
    				out.write(nb);
				}
				out.flush();
				//close down inpSut if there is a Syso
    			if (out.equals(System.out)){
    				socketServer.shutdownInput();
    				socketServer.close();
				} else {
    				socketServer.shutdownOutput();
				}
			} catch (IOException e){
    			System.err.println(e);
			}
		}
	}
}
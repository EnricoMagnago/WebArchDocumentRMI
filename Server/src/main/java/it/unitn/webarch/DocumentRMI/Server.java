package it.unitn.webarch.DocumentRMI;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project: DocumentRMI
 * Created by en on 15/10/17.
 */
public class Server  extends UnicastRemoteObject implements DocumentTimestamper{
	private static final long serialVersionUID = 1L;

	protected Server() throws RemoteException{
	}

	@Override
	public <T extends Document> T addTimestamp(final T doc) throws RemoteException{
		final SimpleDateFormat df = new SimpleDateFormat("dd,MM,yyyy HH:mm:ss");
		final Date date = new Date();
		doc.addString("Viewed on: " + df.format(date));
		return doc;
	}

	public static void main(String[] args){
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		System.out.println(s);
		System.getProperties().put("java.security.policy", "rmi.policy");
		System.setProperty("java.security.policy", "file:///" + s +"/src/main/resources/rmi.policy");

		if(System.getSecurityManager() == null){
			System.setSecurityManager(new SecurityManager());
		}
		try{
			LocateRegistry.createRegistry(1099);
			final DocumentTimestamper stamper = new Server();
			Naming.rebind(DocumentTimestamper.name, stamper);
			System.out.println("Server ready");
		}catch(Exception e){
			System.err.println("Server exception:");
			e.printStackTrace();
		}

	}
}

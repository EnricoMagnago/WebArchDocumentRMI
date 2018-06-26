package it.unitn.webarch.DocumentRMI;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Project: DocumentRMI
 * Created by en on 15/10/17.
 */
public class Client{
	private static final long serialVersionUID = 1L;

	public static void main(String[] args){
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		System.out.println(s);
		System.getProperties().put("java.security.policy", "rmi.policy");
		System.setProperty("java.security.policy", "file:///" + s + "/src/main/resources/rmi.policy");

		if(System.getSecurityManager() == null){
			System.setSecurityManager(new SecurityManager());
		}
		try{
			Registry registry = LocateRegistry.getRegistry(null);
			final DocumentTimestamper timestamper = (DocumentTimestamper) Naming.lookup(DocumentTimestamper.name);

			final Document doc = new Document();
			doc.addString("test");

			final Document newDoc = timestamper.addTimestamp(doc);
			System.out.println("old doc: " + doc);
			System.out.println("new doc: " + newDoc);
		}catch(Exception e){
			System.err.println("Client exception:");
			e.printStackTrace();
		}

	}
}

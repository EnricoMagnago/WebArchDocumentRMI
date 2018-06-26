package it.unitn.webarch.DocumentRMI;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Project: DocumentRMI
 * Created by en on 15/10/17.
 */
public interface DocumentTimestamper extends Remote, Serializable {
	public static final String name = "//localhost/DocumentTimestamper";

	<T extends Document> T addTimestamp(T doc) throws RemoteException;
}

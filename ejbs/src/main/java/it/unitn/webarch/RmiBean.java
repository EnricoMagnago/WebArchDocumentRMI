package it.unitn.webarch;

import it.unitn.webarch.rmi.RmiCourse;
import it.unitn.webarch.rmi.RmiStudent;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Project: Assignment_5
 * Created by en on 11/11/17.
 */

@Startup
@Singleton
public class RmiBean{
	@Resource
	private SessionContext context;

	@PostConstruct
	public void postConstruct(){
		try{
			System.out.print("RmiBean binding registry...");
			LocateRegistry.createRegistry(1099);
			System.out.println("binded");
		}catch(RemoteException e){
			System.out.println("WARNING: already binded\n\n");
			e.printStackTrace();
		}

		RmiStudent studentBean = null;
		RmiCourse courseBean = null;

		try{
			studentBean = new RmiStudent();
		}catch(RemoteException e){
			e.printStackTrace();
		}
		try{
			courseBean = new RmiCourse();
		}catch(RemoteException e){
			e.printStackTrace();
		}

		try{
			Naming.bind("rmi_student", studentBean);
		}catch(AlreadyBoundException | MalformedURLException | RemoteException e){
			System.err.println("RmiBean : failed to bind student");
			e.printStackTrace();
		}

		try{
			Naming.bind("rmi_course", courseBean);
		}catch(AlreadyBoundException | MalformedURLException | RemoteException e){
			System.err.println("RmiBean : failed to bind course");
			e.printStackTrace();
		}
	}

	@PreDestroy
	public void preDestroy(){
		try{
			Naming.unbind("rmi_student");
		}catch(RemoteException | NotBoundException | MalformedURLException e){
			System.err.println("failed to unbind student");
			e.printStackTrace();
		}


		try{
			Naming.unbind("rmi_course");
		}catch(RemoteException | NotBoundException | MalformedURLException e){
			System.err.println("failed to unbind course");
			e.printStackTrace();
		}
	}
}

package it.unitn.webarch.rmiclient;

import it.unitn.webarch.rmi.Course;
import it.unitn.webarch.rmi.Student;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Project: Assignment_5
 * Created by en on 09/11/17.
 */
public class RmiClient{
	//private static final long serialVersionUID = 1L;

	public static void main(String args[]){

		final Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		System.getProperties().put("java.security.policy", "rmi.policy");
		System.setProperty("java.security.policy", "file:///"+ s + "/client/rmiClient/src/main/resources/rmi.policy");

		if(System.getSecurityManager() == null){
			System.setSecurityManager(new SecurityManager());
		}

		Course courseManager = null;
		Student studentManager = null;

		try{

			courseManager = (Course) Naming.lookup("//localhost/rmi_course");
			studentManager = (Student) Naming.lookup("//localhost/rmi_student");
		}catch(RemoteException e){
			System.err.println("Client : remote exception");
			e.printStackTrace();
			System.exit(1);
		}catch(NotBoundException e){
			System.err.println("Client : not bound exception");
			e.printStackTrace();
			System.exit(2);
		}catch(MalformedURLException e){
			System.err.println("Client : malformed url exception");
			e.printStackTrace();
			System.exit(3);
		}

		try{
			Long idWebArchi = courseManager.addCourse("web architectures");
			Long idFormal = courseManager.addCourse("formal methods");
			Long idPluto = studentManager.addStudent("pluto");
			Long idPippo = studentManager.addStudent("pippo");

			courseManager.addStudentToCourse(idPluto, idWebArchi);
			courseManager.addStudentToCourse(idPippo, idWebArchi);
			courseManager.addStudentToCourse(idPippo, idFormal);

			List<Long> studentsOfWeb = courseManager.getStudentIds(idWebArchi);

			System.out.println("students of web architectures: ");
			for(long id : studentsOfWeb){
				System.out.print("(" + id);
				System.out.print(", " + studentManager.getName(id) + "); ");
			}

			List<Long> studentsOfFormal = courseManager.getStudentIds(idFormal);

			System.out.println("\nstudents of formal methods: ");
			for(long id : studentsOfFormal){
				System.out.print("(" + id);
				System.out.print(", " + studentManager.getName(id) + "); ");
			}
			System.out.println();


		}catch(RemoteException e){
			e.printStackTrace();
		}
	}
}

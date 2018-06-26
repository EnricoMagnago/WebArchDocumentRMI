package it.unitn.webarch.ejbclient;

import it.unitn.webarch.Course;
import it.unitn.webarch.CurrentTime;
import it.unitn.webarch.Student;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;
import java.util.Properties;

/**
 * Project: Assignment_5
 * Created by en on 06/11/17.
 */
public class EjbClient{

	public static void main(String[] args){
		final Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		props.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
		props.put(Context.SECURITY_PRINCIPAL, "webarch");
		props.put(Context.SECURITY_CREDENTIALS, "webarch2017");
		props.put("jboss.naming.client.ejb.context", true);

		Context ctx = null;
		try{
			ctx = new InitialContext(props);
		}catch(NamingException e){
			System.err.println("EjbClient : can not create initial context");
			e.printStackTrace();
			System.exit(1);
		}

		CurrentTime currentTime = null;
		try{
			currentTime = (CurrentTime) ctx.lookup("/assignment5/ejbs/CurrentTimeBean!it.unitn.webarch.CurrentTime");
		} catch(NamingException e){
			System.err.println("EjbClient : object lookup failed");
			e.printStackTrace();
			System.exit(2);
		}

		System.out.println(currentTime.getCurrentTime());

		Course courseManager = null;
		Student studentManager = null;
		try{
			courseManager = (Course) ctx.lookup("/assignment5/ejbs/CourseBean!it.unitn.webarch.CourseBusiness");
			studentManager = (Student) ctx.lookup("/assignment5/ejbs/StudentBean!it.unitn.webarch.StudentBusiness");
		} catch(NamingException e){
			System.err.println("EjbClient : object lookup failed");
			e.printStackTrace();
			System.exit(2);
		}

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
	}
}

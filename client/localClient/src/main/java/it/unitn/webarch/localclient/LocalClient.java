package it.unitn.webarch.localclient;

import it.unitn.webarch.storage.SessionManager;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Project: Assignment_5
 * Created by en on 09/11/17.
 */
public class LocalClient{

	protected Long addCourse(String name){
		Session session = SessionManager.getSession();
		session.beginTransaction();
		it.unitn.webarch.storage.Course course = new it.unitn.webarch.storage.Course();
		course.setCourseName(name);
		session.save(course);
		session.getTransaction().commit();
		session.close();
		return course.getCourseId();
	}

	protected Long addStudent(String name){
		Session session = SessionManager.getSession();
		session.beginTransaction();
		it.unitn.webarch.storage.Student student = new it.unitn.webarch.storage.Student();
		student.setStudentName(name);
		session.save(student);
		session.getTransaction().commit();
		session.close();
		return student.getStudentId();
	}

	protected String getStudentName(long studentId){
		Session session = SessionManager.getSession();
		session.beginTransaction();
		it.unitn.webarch.storage.Student student = session.load(it.unitn.webarch.storage.Student.class, studentId);
		session.getTransaction().commit();
		String studentName = student.getStudentName();
		session.close();
		return studentName;
	}

	protected void addStudentToCourse(long studentId, long courseId){
		Session session = SessionManager.getSession();
		session.beginTransaction();
		it.unitn.webarch.storage.Course course = session.load(it.unitn.webarch.storage.Course.class, courseId);
		it.unitn.webarch.storage.Student student = session.load(it.unitn.webarch.storage.Student.class, studentId);
		course.addStudent(student);
		student.addCourse(course);
		session.saveOrUpdate(student);
		session.saveOrUpdate(course);
		session.getTransaction().commit();
		session.close();
	}

	public List<Long> getStudentIdsOfCourse(long courseId){
		Session session = SessionManager.getSession();
		final Query query = session.createQuery("SELECT student.studentId FROM Student student JOIN student.courses course where course.courseId = :input");
		query.setParameter("input", courseId);
		return query.list();
	}


	public static void main(String args[]){
		LocalClient client = new LocalClient();

		Long idWebArchi = client.addCourse("web architectures");
		System.out.println("idWebArchi: " + idWebArchi);
		Long idFormal = client.addCourse("formal methods");
		System.out.println("idFormal: " + idFormal);
		Long idPluto = client.addStudent("pluto");
		System.out.println("idPluto: " + idPluto);
		Long idPippo = client.addStudent("pippo");
		System.out.println("idPippo: " + idPippo);

		client.addStudentToCourse(idPluto, idWebArchi);
		client.addStudentToCourse(idPippo, idWebArchi);
		client.addStudentToCourse(idPippo, idFormal);

		List<Long> studentsOfWeb = client.getStudentIdsOfCourse(idWebArchi);

		System.out.println("students of web architectures: ");
		for(long id : studentsOfWeb){
			System.out.print("(" + id);
			System.out.print(", " + client.getStudentName(id) + "); ");
		}

		List<Long> studentsOfFormal = client.getStudentIdsOfCourse(idFormal);

		System.out.println("\nstudents of formal methods: ");
		for(long id : studentsOfFormal){
			System.out.print("(" + id);
			System.out.print(", " + client.getStudentName(id) + "); ");
		}
		System.out.println();

		SessionManager.closeFactory();
	}
}

package it.unitn.webarch.rmi;

import it.unitn.webarch.storage.SessionManager;
import org.hibernate.Query;
import org.hibernate.Session;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Project: Assignment_5
 * Created by en on 11/11/17.
 */
public class RmiCourse extends UnicastRemoteObject implements Course{
	public RmiCourse() throws RemoteException{
	}

	@Override
	public List<Long> getCourseIds() throws RemoteException{
		Session session = SessionManager.getSession();
		final Query query = session.createQuery("SELECT course.courseId FROM Course course");
		return query.list();
	}

	@Override
	public List<Long> getStudentIds(long courseId) throws RemoteException{
		Session session = SessionManager.getSession();
		final Query query = session.createQuery("SELECT student.studentId FROM Student student JOIN student.courses course where course.courseId = :input");
		query.setParameter("input", courseId);
		return query.list();
	}

	@Override
	public long addCourse(String name) throws RemoteException{
		Session session = SessionManager.getSession();
		session.beginTransaction();
		it.unitn.webarch.storage.Course course = new it.unitn.webarch.storage.Course();
		course.setCourseName(name);
		session.save(course);
		session.getTransaction().commit();
		session.close();
		return course.getCourseId();
	}

	@Override
	public String getName(long courseId) throws RemoteException{
		Session session = SessionManager.getSession();
		session.beginTransaction();
		it.unitn.webarch.storage.Course course = session.load(it.unitn.webarch.storage.Course.class, courseId);
		session.getTransaction().commit();
		String courseName = course.getCourseName();
		session.close();
		return courseName;
	}

	@Override
	public void addStudentToCourse(long studentId, long courseId) throws RemoteException{
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
}

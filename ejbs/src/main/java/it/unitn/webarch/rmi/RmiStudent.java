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
public class RmiStudent extends UnicastRemoteObject implements Student{
	public RmiStudent() throws RemoteException{
	}

	@Override
	public List<Long> getStudentIds() throws RemoteException{
		Session session = SessionManager.getSession();
		final Query query = session.createQuery("SELECT student.studentId FROM Student student");
		return query.list();
	}

	@Override
	public List<Long> getCourseIds(long studentId) throws RemoteException{
		Session session = SessionManager.getSession();
		final Query query = session.createQuery("SELECT course.courseId FROM Student student JOIN student.courses course where student.studentId = :input");
		query.setParameter("input", studentId);
		return query.list();
	}

	@Override
	public long addStudent(String name) throws RemoteException{
		Session session = SessionManager.getSession();
		session.beginTransaction();
		it.unitn.webarch.storage.Student student = new it.unitn.webarch.storage.Student();
		student.setStudentName(name);
		session.save(student);
		session.getTransaction().commit();
		session.close();
		return student.getStudentId();
	}

	@Override
	public String getName(long studentId) throws RemoteException{
		Session session = SessionManager.getSession();
		session.beginTransaction();
		it.unitn.webarch.storage.Student student = session.load(it.unitn.webarch.storage.Student.class, studentId);
		session.getTransaction().commit();
		String studentName = student.getStudentName();
		session.close();
		return studentName;
	}

	@Override
	public void addCourseToStudent(long courseId, long studentId) throws RemoteException{
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

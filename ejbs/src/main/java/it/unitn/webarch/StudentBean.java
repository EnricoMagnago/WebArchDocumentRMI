package it.unitn.webarch;

import it.unitn.webarch.storage.SessionManager;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Project: Assignment_5
 * Created by en on 04/11/17.
 */

@Stateless
@Remote(StudentBusiness.class)
@Local(StudentLocal.class)
public class StudentBean extends UnicastRemoteObject implements it.unitn.webarch.Student{
	@PersistenceContext
	private Session manager;

	public StudentBean() throws RemoteException{
		super();
		manager = SessionManager.getSession();
	}

	@Override
	public List<Long> getStudentIds(){
		final Query query = manager.createQuery("select student.studentId from Student student");
		return (List<Long>)query.list();
	}

	@Override
	public String getName(long studentId){
		it.unitn.webarch.storage.Student student = manager.load(it.unitn.webarch.storage.Student.class, studentId);
		return student.getStudentName();
	}

	@Override
	public List<Long> getCourseIds(long studentId){
		final Query query = manager.createQuery("SELECT course.courseId FROM Course course JOIN course.students student where student.studentId = :input");
		query.setParameter("input", studentId);
		return query.list();
	}

	public long addStudent(String name){
		it.unitn.webarch.storage.Student student = new it.unitn.webarch.storage.Student();
		student.setStudentName(name);
		manager.persist(student);
		return student.getStudentId();
	}

	@Override
	public void addCourseToStudent(long courseId, long studentId){
		manager.beginTransaction();

		final it.unitn.webarch.storage.Course course = manager.load(it.unitn.webarch.storage.Course.class, courseId);
		final it.unitn.webarch.storage.Student student = manager.load(it.unitn.webarch.storage.Student.class, studentId);
		student.addCourse(course);
		course.addStudent(student);

		manager.saveOrUpdate(course);
		manager.saveOrUpdate(student);
		manager.getTransaction().commit();
	}
}

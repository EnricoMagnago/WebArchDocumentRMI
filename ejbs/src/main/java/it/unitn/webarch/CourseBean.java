package it.unitn.webarch;

import it.unitn.webarch.storage.SessionManager;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Project: Assignment_5
 * Created by en on 04/11/17.
 */

@Stateless
@Remote(it.unitn.webarch.CourseBusiness.class)
@Local(it.unitn.webarch.CourseLocal.class)
public class CourseBean implements it.unitn.webarch.Course{
	@PersistenceContext(name="Assignment5")
	private final Session manager;
	public CourseBean() throws RemoteException{
		super();
		manager = SessionManager.getSession();
	}

	@Override
	public List<Long> getStudentIds(long courseId){
		final Query query = manager.createQuery("SELECT student.studentId FROM Student student JOIN student.courses course where course.courseId = :input");
		query.setParameter("input", courseId);
		return query.list();
	}

	@Override
	public String getName(long courseId){
		it.unitn.webarch.storage.Course course = manager.load(it.unitn.webarch.storage.Course.class, courseId);
		return course.getCourseName();
	}

	@Override
	public List<Long> getCourseIds(){
		final Query query = manager.createQuery("select course.courseId from Course course");
		return (List<Long>)query.list();
	}

	public long addCourse(String name){
		it.unitn.webarch.storage.Course course = new it.unitn.webarch.storage.Course();
		course.setCourseName(name);
		manager.persist(course);
		return course.getCourseId();
	}

	public void addStudentToCourse(long studentId, long courseId){
		manager.beginTransaction();
		it.unitn.webarch.storage.Course course = manager.load(it.unitn.webarch.storage.Course.class, courseId);
		it.unitn.webarch.storage.Student student = manager.load(it.unitn.webarch.storage.Student.class, studentId);
		course.addStudent(student);
		student.addCourse(course);
		manager.saveOrUpdate(student);
		manager.saveOrUpdate(course);
		manager.getTransaction().commit();
	}
}

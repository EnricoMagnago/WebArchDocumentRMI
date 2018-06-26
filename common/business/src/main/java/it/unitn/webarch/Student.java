package it.unitn.webarch;

import java.io.Serializable;
import java.util.List;

/**
 * Project: Assignment_5
 * Created by en on 05/11/17.
 */
public interface Student extends Serializable{
	public List<Long> getStudentIds();
	public long addStudent(String name);
	public String getName(long studentId);
	public List<Long> getCourseIds(long studentId);
	public void addCourseToStudent(long courseId, long studentId);
}

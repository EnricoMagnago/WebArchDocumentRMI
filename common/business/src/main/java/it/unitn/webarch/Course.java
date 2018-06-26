package it.unitn.webarch;

import java.io.Serializable;
import java.util.List;

/**
 * Project: Assignment_5
 * Created by en on 04/11/17.
 */
public interface Course extends Serializable{
	public List<Long> getCourseIds();
	public long addCourse(String name);
	public String getName(long courseId);
	public List<Long> getStudentIds(long courseId);
	public void addStudentToCourse(long studentId, long courseId);
}

package it.unitn.webarch.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Project: Assignment_5
 * Created by en on 04/11/17.
 */
public interface Course extends Remote{
	public List<Long> getCourseIds() throws RemoteException;
	public List<Long> getStudentIds(long courseId) throws RemoteException;
	public long addCourse(String name) throws RemoteException;
	public String getName(long courseId) throws RemoteException;
	public void addStudentToCourse(long studentId, long courseId) throws RemoteException;
}

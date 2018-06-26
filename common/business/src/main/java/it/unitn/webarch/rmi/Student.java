package it.unitn.webarch.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Project: Assignment_5
 * Created by en on 05/11/17.
 */
public interface Student extends Remote{
	public List<Long> getStudentIds() throws RemoteException;
	public List<Long> getCourseIds(long studentId) throws RemoteException;
	public long addStudent(String name) throws RemoteException;
	public String getName(long studentId) throws RemoteException;
	public void addCourseToStudent(long courseId, long studentId) throws RemoteException;
}

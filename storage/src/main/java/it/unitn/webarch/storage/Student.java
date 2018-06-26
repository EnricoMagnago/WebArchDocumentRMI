package it.unitn.webarch.storage;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: Assignment_5
 * Created by en on 04/11/17.
 */
@Entity(name="Student")
@Table(name="Student")
public class Student implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="STUDENT_ID", unique=true, nullable = false)
	private long studentId;
	@Column(name="STUDENT_NAME", length = 100)
	private String studentName;
	@ManyToMany(cascade = {CascadeType.ALL}, fetch= FetchType.EAGER, mappedBy ="students")
	private List<Course> courses;


	public long getStudentId(){
		return studentId;
	}

	public void setStudentId(long studentId){
		this.studentId = studentId;
	}

	public String getStudentName(){
		return studentName;
	}

	public void setStudentName(String studentName){
		this.studentName = studentName;
	}

	public List<Course> getCourses(){
		return courses;
	}

	public void setCourses(List<Course> courses){
		this.courses = courses;
	}

	public void addCourse(Course course){
		if(this.courses == null) this.courses = new ArrayList<>();
		this.courses.add(course);
	}

	@Override
	public int hashCode(){
		return Long.hashCode(this.getStudentId()) + this.getStudentName().hashCode();
	}

	@Override
	public boolean equals(Object obj){
		if(obj == null) return false;
		if(!this.getClass().equals(obj.getClass())) return false;

		final Student other = (Student) obj;
		return Long.compare(other.getStudentId(), this.getStudentId()) == 0L;
	}
}

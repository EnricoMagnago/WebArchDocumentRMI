package it.unitn.webarch.storage;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: Assignment_5
 * Created by en on 04/11/17.
 */
@Entity(name="Course")
@Table (name="Course", uniqueConstraints = {
		@UniqueConstraint(columnNames ="COURSE_NAME")
        })
public class Course implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name="COURSE_ID", unique=true, nullable = false)
	private Long courseId;

	@Column(name="COURSE_NAME", length=50)
	private String courseName;

	@ManyToMany(cascade = {CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinTable(name="Course_Student",
			joinColumns={@JoinColumn(name="COURSE_ID", nullable = false, updatable = false)},
			inverseJoinColumns = {@JoinColumn(name="STUDENT_ID", nullable = false, updatable = false)}
	)
	private List<Student> students;

	public Course(){ this.courseId = null;}

	public long getCourseId(){
		return courseId;
	}

	public void setCourseId(long courseId){ this.courseId = courseId; }

	public String getCourseName(){
		return courseName;
	}

	public void setCourseName(String courseName){
		this.courseName = courseName;
	}

	public List<Student> getStudents(){
		return students;
	}

	public void setStudents(List<Student> students) { this.students = students; }

	public void addStudent(Student student){
		if(this.students == null) this.students = new ArrayList<>();
		this.students.add(student);
	}

	@Override
	public int hashCode(){
		return Long.hashCode(this.getCourseId()) + this.getCourseName().hashCode();
	}

	@Override
	public boolean equals(Object obj){
		if(obj == null) return false;
		if(!this.getClass().equals(obj.getClass())) return false;

		final Course other = (Course) obj;
		return Long.compare(other.getCourseId(), this.getCourseId()) == 0L;
	}
}

package com.WebStudentBookJSF.web.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ComponentSystemEvent;
import javax.persistence.Entity;


@ManagedBean
@SessionScoped
public class StudentManager {

	
	List<Student> students;
	
	
	public StudentManager() {
		this.students = new ArrayList<Student>();
	}
	
	
	public List<Student> getStudents() { return students; }

	public void setStudents(List<Student> students) { this.students = students; }
	
	
	
	// : loads students from the database.
	public void loadStudents() {
		System.out.print("Load Students Started");
		students = StudentDbUtil.getStudents();	
		System.out.print(students);
	}
	
	
	
	
	// : saves a student to the database and opens the updated list of students.
	public String addStudent() {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	
		Map<String, Object> requestMap = externalContext.getRequestMap();
		
		String ln = (String) requestMap.get("TheLastName");
		String fn = (String) requestMap.get("TheFirstName");
		String email = (String) requestMap.get("TheEmail");
			
		Student stu = new Student(fn, ln, email);
		StudentDbUtil.addStudent(stu);
		
		return "List-students";
	}
	
	// : opens the editstudent.xhtml form with the selected student.
	public String loadStudent(int id) {	
		id=id-1;
		Student theStudent = students.get(id);
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();
		requestMap.put("student", theStudent);
		
		return "Edit-student";
	}

	
	// : updates the edited student and opens the updated list of students.
	public String updateStudent() {
			
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();
		Student st = (Student) requestMap.get("student");

		StudentDbUtil.updateStudent(st);
		
		return "List-students";
	}
	

	
	
	// : deletes a student with a given id from the database and gets back to the list of students.
	public String deleteStudent(int id) {
			StudentDbUtil.deleteStudent(id);
			this.loadStudents();
			return "List-students";
	}
	
	
	
	
}

package com.WebStudentBookJSF.web.jdbc;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.omg.CORBA.SystemException;


@ManagedBean()
@ApplicationScoped

public class StudentDbUtil {

	private static final String PERSIS_NAME = "JSFJPA"; 
	
	
	public static List<Student> getStudents(){
		
		EntityManagerFactory factory = null;
		EntityManager em = null;
		List<Student> students;
		
		try {
			factory = Persistence.createEntityManagerFactory(PERSIS_NAME);
			em = factory.createEntityManager();
			
			students = em.createQuery("SELECT st FROM Student st", Student.class).getResultList();
			
						
		} finally {
			if(em != null) em.close();	
			if(factory != null) factory.close();			
		}
		
		return students;
				
	}
			
	
	
	public static void addStudent(Student student){
		EntityManagerFactory factory = null;
		EntityManager em = null;
		
		try {
			factory = Persistence.createEntityManagerFactory(PERSIS_NAME);
			em = factory.createEntityManager();
			
			em.getTransaction().begin();
			em.persist(student);
			em.getTransaction().commit();

		} catch (SystemException e){
			e.printStackTrace(); 
						
		} finally {
			if(em != null) em.close();	
			if(factory != null) factory.close();			
		}
			
	}
	

	
	
	public static void updateStudent(Student student){
		
		EntityManagerFactory factory = null;
		EntityManager em = null;
		System.out.print("olééééé");
		
		try {
			factory = Persistence.createEntityManagerFactory(PERSIS_NAME);
			em = factory.createEntityManager();
			
			Student studentToUpdate = em.find(Student.class, student.ID);
			
			
			System.out.print(studentToUpdate.email);
			em.getTransaction().begin();
			
			studentToUpdate.setFirstName(student.firstName);
			studentToUpdate.setLastName(student.lastName);
			studentToUpdate.setEmail(student.email);
	
			em.getTransaction().commit();
			

		} catch (SystemException e){
			e.printStackTrace(); 

		} finally {
			if(em != null) em.close();	
			if(factory != null) factory.close();			
		}

	}
	
	
	
	public static void deleteStudent(int id){
		
		EntityManagerFactory factory = null;
		EntityManager em = null;

		
		try {
			factory = Persistence.createEntityManagerFactory(PERSIS_NAME);
			em = factory.createEntityManager();
			Student oldStudent = em.find(Student.class, id);
			em.getTransaction().begin();
			em.remove(oldStudent);
			
			
			em.createNativeQuery("SET SQL_SAFE_UPDATES = 0").executeUpdate();
			em.createNativeQuery("set @autoid := 0").executeUpdate();
			em.createNativeQuery("update student set id= @autoid := (@autoid + 1)").executeUpdate();
			em.createNativeQuery("ALTER TABLE student AUTO_INCREMENT =1").executeUpdate();
			em.createNativeQuery("SET SQL_SAFE_UPDATES = 1").executeUpdate();
			
			em.getTransaction().commit();

		} catch (SystemException e){
			e.printStackTrace(); 

		} finally {
			if(em != null) em.close();	
			if(factory != null) factory.close();			
		}

	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

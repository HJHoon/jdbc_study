package jdbc_study;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import jdbc_study.dao.EmployeeDao;
import jdbc_study.daoimpl.EmployeeDaoImpl;
import jdbc_study.dto.Department;
import jdbc_study.dto.Employee;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeDaoTest {
	static final Logger log = LogManager.getLogger();
	static EmployeeDao dao;
	static File picsDir;
	static File readFileDir;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log.trace("setUpBeforeClass()");
		dao = new EmployeeDaoImpl();
		
		picsDir = new File(System.getProperty("user.dir")+ 
				           System.getProperty("file.separator") + 
				           "pics" + 
				           System.getProperty("file.separator"));
		
		if (!picsDir.exists()) {
			picsDir.mkdir();
		}

		readFileDir = new File(System.getProperty("user.dir")+ 
				           System.getProperty("file.separator") + 
				           "images" + 
				           System.getProperty("file.separator"));
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		log.trace("tearDownAfterClass()");
		dao = null;
/*
		if (picsDir.exists()) {
			for(File f : picsDir.listFiles()) {
				f.delete();
			}
			picsDir.delete();
		}
*/
	}

	@Test
	public void test02SelectEmployeeByAll() throws SQLException, FileNotFoundException, IOException {
		log.trace("testSelectEmployeeByAll()");
		List<Employee> list = dao.selectEmployeeByAll();
		Assert.assertNotEquals(0, list.size());
		
		File imgFile = null;
		for(Employee e : list) {
			if (e.getPic() != null) {
				imgFile = getPicFile(e);
				log.trace(imgFile.getAbsolutePath());
			}
		}
	}
	
	@Test
	public void test03InsertEmployee() throws SQLException {
		log.trace("testInsertEmployee()");
		Employee newEmp = new Employee(1004, "채지안", "사원", new Employee(3000), 
				1500000, new Department(1), getImage("채지안2.jpg"));
		
		int res = dao.insertEmployee(newEmp);
		log.trace("res = " + res);
		Assert.assertEquals(1, res);
	}

	@Test
	public void test04UpdateEmployee() throws SQLException {
		Employee updateEmp = new Employee(1004, "채지안2", "인턴", new Employee(3000), 
				3500000, new Department(2));
		updateEmp.setPic(getImage("jian.jpg"));
		
		int res = dao.updateEmployee(updateEmp);
		Assert.assertEquals(1, res);
	}
	
	@Test
	public void test05SelectEmployeeByNo() throws FileNotFoundException, IOException, SQLException {
		log.trace("test04SelectEmployeeByNo()");
		Employee selEmp = dao.selectEmployeeByNo(new Employee(1004));
		Assert.assertNotNull(selEmp);
		
		if (selEmp.getPic() != null) {
			File imgFile = getPicFile(selEmp);
			log.trace(imgFile.getAbsolutePath());
		}
		
	}
	
	@Test
	public void test01DeleteEmployee() throws SQLException {
		Employee delEmp = new Employee(1004);
		int res = dao.deleteEmployee(delEmp);
		Assert.assertEquals(1, res);
	}
	
	private byte[] getImage(String fileName) {
		byte[] pic = null;

		File imgFile = new File(readFileDir, fileName);
		
		try(InputStream is = new FileInputStream(imgFile);){
			pic = new byte[is.available()];
			is.read(pic);
		} catch (FileNotFoundException e) {
			System.out.println("해당 파일을 찾을 수 없음");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pic;
	}

	private File getPicFile(Employee e) throws FileNotFoundException, IOException {
		File file = null;
		file = new File(picsDir, e.getEmpName()+".jpg");
		try(FileOutputStream fos = new FileOutputStream(file)){
			fos.write(e.getPic());
		}
		return file;
	}

}

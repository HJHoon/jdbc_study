package jdbc_study.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jdbc_study.dao.DepartmentDao;
import jdbc_study.dao.EmployeeDao;
import jdbc_study.daoimpl.DepartmentDaoImpl;
import jdbc_study.daoimpl.EmployeeDaoImpl;
import jdbc_study.dto.Department;
import jdbc_study.dto.Employee;
import jdbc_study.ui.content.PanelDepartment;
import jdbc_study.ui.content.PanelEmployee;

@SuppressWarnings("serial")
public class ErpManagementUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnDeptDelete;
	private JButton btnDeptUpdate;
	private JButton btnDeptSearch;
	private JButton btnDeptAdd;
	private JButton btnDeptList;

	private DepartmentDao deptDao;
	private EmployeeDao empDao;

	private DepartmentUI frameDepartment;
	private DepartmentListUI frameDepartmentList;

	private EmployeeUI frameEmployee;
	private EmployeeListUI frameEmployeeList;

	private JPanel pDept;
	private JPanel pEmp;
	private JButton btnEmpAdd;
	private JButton btnEmpUpdate;
	private JButton btnEmpDelete;
	private JButton btnEmpSearch;
	private JButton btnEmpList;

	public ErpManagementUI() {
		deptDao = new DepartmentDaoImpl();
		empDao = new EmployeeDaoImpl();
		initComponents();
	}

	private void initComponents() {
		setTitle("부서 관리 메뉴");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 622, 202);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 0, 10, 10));

		pDept = new JPanel();
		contentPane.add(pDept);
		pDept.setLayout(new GridLayout(1, 0, 10, 0));

		btnDeptAdd = new JButton("부서 추가");
		pDept.add(btnDeptAdd);

		btnDeptUpdate = new JButton("부서 수정");
		pDept.add(btnDeptUpdate);

		btnDeptDelete = new JButton("부서 삭제");
		pDept.add(btnDeptDelete);

		btnDeptSearch = new JButton("부서 검색");
		pDept.add(btnDeptSearch);

		btnDeptList = new JButton("부서 목록");
		pDept.add(btnDeptList);
		btnDeptList.addActionListener(this);
		btnDeptSearch.addActionListener(this);
		btnDeptDelete.addActionListener(this);
		btnDeptUpdate.addActionListener(this);
		btnDeptAdd.addActionListener(this);

		pEmp = new JPanel();
		contentPane.add(pEmp);
		pEmp.setLayout(new GridLayout(1, 5, 10, 0));

		btnEmpAdd = new JButton("사원 추가");
		btnEmpAdd.addActionListener(this);
		pEmp.add(btnEmpAdd);

		btnEmpUpdate = new JButton("사원 수정");
		btnEmpUpdate.addActionListener(this);
		pEmp.add(btnEmpUpdate);

		btnEmpDelete = new JButton("사원 삭제");
		btnEmpDelete.addActionListener(this);
		pEmp.add(btnEmpDelete);

		btnEmpSearch = new JButton("사원 검색");
		btnEmpSearch.addActionListener(this);
		pEmp.add(btnEmpSearch);

		btnEmpList = new JButton("사원 목록");
		btnEmpList.addActionListener(this);
		pEmp.add(btnEmpList);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnEmpUpdate) {
			try {
				actionPerformedBtnEmpUpdate(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource() == btnEmpDelete) {
			String delEmpNo = JOptionPane.showInputDialog("삭제할 사원번호를 입력하세요");
			if (delEmpNo == null)
				return;
			actionPerformedBtnEmpDelete(Integer.parseInt(delEmpNo));
		}
		if (e.getSource() == btnEmpList) {
			actionPerformedBtnEmpList(e);
		}
		if (e.getSource() == btnEmpSearch) {
			actionPerformedBtnEmpSearch(e);
		}
		if (e.getSource() == btnEmpAdd) {
			actionPerformedBtnEmpAdd(e);
		}
		if (e.getSource() == btnDeptList) {
			actionPerformedBtnList(e);
		}
		if (e.getSource() == btnDeptAdd) {
			actionPerformedBtnAdd(e);
		}
		if (e.getSource() == btnDeptSearch) {
			actionPerformedBtnSearch(e);
		}
		if (e.getSource() == btnDeptUpdate) {
			actionPerformedBtnUpdate(e);
		}
		if (e.getSource() == btnDeptDelete) {
			String deptNo = JOptionPane.showInputDialog("삭제할 부서번호를 입력하세요");
			if (deptNo == null)
				return;
			actionPerformedBtnDelete(Integer.parseInt(deptNo));
		}
	}

	public void actionPerformedBtnEmpDelete(int empNo) {
		try {
			int res = empDao.deleteEmployee(new Employee(empNo));
			if (res != -1)
				JOptionPane.showMessageDialog(null, "삭제되었습니다.");
			refreshUI();
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void actionPerformedBtnDelete(int deptNo) {
		try {
			int res = deptDao.deleteDepartment(new Department(deptNo));
			if (res != -1)
				JOptionPane.showMessageDialog(null, "삭제되었습니다.");
			refreshUI();
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	protected void actionPerformedBtnUpdate(ActionEvent e) {
		List<Department> deptList = deptDao.selectDepartmentByAll();
		Department[] dList = new Department[deptList.size()];
		deptList.toArray(dList);
		Department selDept = (Department) JOptionPane.showInputDialog(null, "수정할 부서번호를 입력하세요", "부서 수정",
				JOptionPane.QUESTION_MESSAGE, null, dList, dList[0]);
		if (selDept != null) {
			showDepartmentUI(selDept);
		}
	}

	protected void actionPerformedBtnEmpUpdate(ActionEvent e) throws SQLException {
		List<Employee> empList = empDao.selectEmployeeByAll();
		Employee[] eList = new Employee[empList.size()];
		empList.toArray(eList);

		Employee selEmp = (Employee) JOptionPane.showInputDialog(null, "수정할 사원번호를 입력하세요", "사원 수정",
				JOptionPane.QUESTION_MESSAGE, null, eList, eList[0]);
		if (selEmp == null)
			return;
		showEmployeeUI(selEmp.getEmpNo());
	}

	public void showDepartmentUI() {
		if (frameDepartment == null) {
			frameDepartment = new DepartmentUI();
			frameDepartment.setParent(ErpManagementUI.this);
			frameDepartment.setDao(deptDao);
		}
		frameDepartment.clearDepartment();
		frameDepartment.setVisible(true);
	}

	public void showEmployeeUI() {
		if (frameEmployee == null) {
			frameEmployee = new EmployeeUI();
			frameEmployee.setParent(ErpManagementUI.this);
			frameEmployee.setEmpDao(empDao);
			frameEmployee.setDepDao(deptDao);
		}
		frameEmployee.clearEmployee();

		frameEmployee.setVisible(true);
	}

	public void showDepartmentUI(Department selectedDept) {
		if (frameDepartment == null) {
			frameDepartment = new DepartmentUI();
			frameDepartment.setParent(this);
			frameDepartment.setDao(deptDao);
		}
		frameDepartment.setDepartment(selectedDept);
		frameDepartment.setVisible(true);
	}

	public void showEmployeeUI(int empNo) {
		Employee selEmp;
		try {
			selEmp = empDao.selectEmployeeByNo(new Employee(empNo));
			if (selEmp == null) {
				JOptionPane.showMessageDialog(null, "수정할 사원이 존재하지 않습니다.");
				return;
			}
			if (frameEmployee == null) {
				frameEmployee = new EmployeeUI();
				frameEmployee.setParent(this);
				frameEmployee.setEmpDao(empDao);
				frameEmployee.setDepDao(deptDao);
			}
			frameEmployee.setEmployee(selEmp);
			frameEmployee.setVisible(true);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	protected void actionPerformedBtnSearch(ActionEvent e) {
		String deptNo = JOptionPane.showInputDialog("검색할 부서번호를 입력하세요");
		if (deptNo == null) {
			return;
		}
		Department selDept;
		try {
			selDept = deptDao.selectDepartmentByNo(new Department(Integer.parseInt(deptNo)));
			if (selDept == null) {
				JOptionPane.showMessageDialog(null, "해당 부서가 존재하지 않습니다.");
				return;
			}
			PanelDepartment pdept = new PanelDepartment();
			pdept.setDepartment(selDept);
			pdept.setTfAllEditable(false);
			JOptionPane.showMessageDialog(null, pdept, "부서 정보", JOptionPane.INFORMATION_MESSAGE);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	protected void actionPerformedBtnAdd(ActionEvent e) {
		showDepartmentUI();
	}

	protected void actionPerformedBtnList(ActionEvent e) {
		if (frameDepartmentList == null) {
			frameDepartmentList = new DepartmentListUI();
			frameDepartmentList.setErpManagementUI(this);
		}
		frameDepartmentList.setDepartmentList(deptDao.selectDepartmentByAll());
		frameDepartmentList.reloadData();
		frameDepartmentList.setVisible(true);
	}

	public void refreshUI() throws SQLException {
		if (frameDepartmentList != null && frameDepartmentList.isVisible()) {
			frameDepartmentList.setDepartmentList(deptDao.selectDepartmentByAll());
			frameDepartmentList.reloadData();
		}
		if (frameEmployeeList != null && frameEmployeeList.isVisible()) {
			frameEmployeeList.setEmployeeList(empDao.selectEmployeeByAll());
			frameEmployeeList.reloadData();
		}

	}

	protected void actionPerformedBtnEmpAdd(ActionEvent e) {
		showEmployeeUI();
	}

	protected void actionPerformedBtnEmpSearch(ActionEvent e) {
		String empNo = JOptionPane.showInputDialog("검색할 사원번호를 입력하세요");
		if (empNo == null) {
			return;
		}
		Employee selEmp;
		try {
			selEmp = empDao.selectEmployeeByNo(new Employee(Integer.parseInt(empNo)));
			if (selEmp == null) {
				JOptionPane.showMessageDialog(null, "해당 사원이 존재하지 않습니다.");
				return;
			}
			PanelEmployee pEmp = new PanelEmployee();
			pEmp.setEmployee(selEmp);
			pEmp.setCmbModel(deptDao.selectDepartmentByAll());
			pEmp.setTfAllEditable(false);
			JOptionPane.showMessageDialog(null, pEmp, "사원 정보", JOptionPane.INFORMATION_MESSAGE);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	protected void actionPerformedBtnEmpList(ActionEvent e) {
		if (frameEmployeeList == null) {
			frameEmployeeList = new EmployeeListUI();
			frameEmployeeList.setErpManagementUI(this);
		}
		try {
			List<Employee> selList = empDao.selectEmployeeByAll();
			if (selList == null) {
				selList = new ArrayList<>();
			}
			frameEmployeeList.setEmployeeList(selList);
			frameEmployeeList.reloadData();
			frameEmployeeList.setVisible(true);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

}

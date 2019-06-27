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
		setTitle("�μ� ���� �޴�");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 243);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 0, 10, 10));

		pDept = new JPanel();
		contentPane.add(pDept);
		pDept.setLayout(new GridLayout(1, 0, 10, 0));

		btnDeptAdd = new JButton("�μ� �߰�");
		pDept.add(btnDeptAdd);

		btnDeptUpdate = new JButton("�μ� ����");
		pDept.add(btnDeptUpdate);

		btnDeptDelete = new JButton("�μ� ����");
		pDept.add(btnDeptDelete);

		btnDeptSearch = new JButton("�μ� �˻�");
		pDept.add(btnDeptSearch);

		btnDeptList = new JButton("�μ� ���");
		pDept.add(btnDeptList);
		btnDeptList.addActionListener(this);
		btnDeptSearch.addActionListener(this);
		btnDeptDelete.addActionListener(this);
		btnDeptUpdate.addActionListener(this);
		btnDeptAdd.addActionListener(this);

		pEmp = new JPanel();
		contentPane.add(pEmp);
		pEmp.setLayout(new GridLayout(1, 5, 10, 0));

		btnEmpAdd = new JButton("��� �߰�");
		btnEmpAdd.addActionListener(this);
		pEmp.add(btnEmpAdd);

		btnEmpUpdate = new JButton("��� ����");
		btnEmpUpdate.addActionListener(this);
		pEmp.add(btnEmpUpdate);

		btnEmpDelete = new JButton("��� ����");
		btnEmpDelete.addActionListener(this);
		pEmp.add(btnEmpDelete);

		btnEmpSearch = new JButton("��� �˻�");
		btnEmpSearch.addActionListener(this);
		pEmp.add(btnEmpSearch);

		btnEmpList = new JButton("��� ���");
		btnEmpList.addActionListener(this);
		pEmp.add(btnEmpList);
	}

	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == btnEmpUpdate) {
				actionPerformedBtnEmpUpdate(e);
			}
			if (e.getSource() == btnEmpDelete) {
				String delEmpNo = JOptionPane.showInputDialog("������ �����ȣ�� �Է��ϼ���");
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
				actionPerformedBtnDeptList(e);
			}
			if (e.getSource() == btnDeptAdd) {
				actionPerformedBtnDeptAdd(e);
			}
			if (e.getSource() == btnDeptSearch) {
				actionPerformedBtnDeptSearch(e);
			}
			if (e.getSource() == btnDeptUpdate) {
				actionPerformedBtnDeptUpdate(e);
			}
			if (e.getSource() == btnDeptDelete) {
				String deptNo = JOptionPane.showInputDialog("������ �μ���ȣ�� �Է��ϼ���");
				if (deptNo == null)
					return;
				actionPerformedBtnDeptDelete(Integer.parseInt(deptNo));
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
	}

	public void actionPerformedBtnEmpDelete(int empNo) throws SQLException {
		int res = empDao.deleteEmployee(new Employee(empNo));
		if (res != -1)
			JOptionPane.showMessageDialog(null, "�����Ǿ����ϴ�.");
		refreshListUI();
	}

	public void actionPerformedBtnDeptDelete(int deptNo) throws SQLException {
		int res = deptDao.deleteDepartment(new Department(deptNo));
		if (res != -1)
			JOptionPane.showMessageDialog(null, "�����Ǿ����ϴ�.");
		refreshListUI();
	}

	protected void actionPerformedBtnDeptUpdate(ActionEvent e) {
		List<Department> deptList = deptDao.selectDepartmentByAll();
		Department[] dList = new Department[deptList.size()];
		deptList.toArray(dList);
		Department selDept = (Department) JOptionPane.showInputDialog(null, "������ �μ���ȣ�� �Է��ϼ���", "�μ� ����",
				JOptionPane.QUESTION_MESSAGE, null, dList, dList[0]);
		if (selDept != null) {
			showDepartmentUI(selDept);
		}
	}

	protected void actionPerformedBtnEmpUpdate(ActionEvent e) throws SQLException {
		List<Employee> empList = empDao.selectEmployeeByAll();
		Employee[] eList = new Employee[empList.size()];
		empList.toArray(eList);

		Employee selEmp = (Employee) JOptionPane.showInputDialog(null, "������ �����ȣ�� �Է��ϼ���", "��� ����",
				JOptionPane.QUESTION_MESSAGE, null, eList, eList[0]);
		if (selEmp == null)
			return;
		showEmployeeUI(selEmp);
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

	public void showDepartmentUI(Department selectedDept) {
		showDepartmentUI();
		frameDepartment.setDepartment(selectedDept);
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

	public void showEmployeeUI(Employee selEmp) throws SQLException {
		showEmployeeUI();
		frameEmployee.setEmployee(selEmp);
	}

	protected void actionPerformedBtnDeptAdd(ActionEvent e) {
		showDepartmentUI();
	}

	protected void actionPerformedBtnDeptList(ActionEvent e) {
		showDeptListUI();
	}

	protected void actionPerformedBtnEmpList(ActionEvent e) throws SQLException {
		showEmpListUI();
	}

	private void showEmpListUI() throws SQLException {
		if (frameEmployeeList == null) {
			frameEmployeeList = new EmployeeListUI();
			frameEmployeeList.setErpManagementUI(this);
		}

		List<Employee> selList = empDao.selectEmployeeByAll();
		if (selList == null) {
			selList = new ArrayList<>();
		}
		frameEmployeeList.setEmployeeList(selList);
		frameEmployeeList.reloadData();
		frameEmployeeList.setVisible(true);
	}

	private void showDeptListUI() {
		if (frameDepartmentList == null) {
			frameDepartmentList = new DepartmentListUI();
			frameDepartmentList.setErpManagementUI(this);
		}
		frameDepartmentList.setDepartmentList(deptDao.selectDepartmentByAll());
		frameDepartmentList.reloadData();
		frameDepartmentList.setVisible(true);
	}

	public void refreshListUI() throws SQLException {
		if (frameDepartmentList != null && frameDepartmentList.isVisible()) {
			showDeptListUI();
		}
		if (frameEmployeeList != null && frameEmployeeList.isVisible()) {
			showEmpListUI();
		}
	}

	protected void actionPerformedBtnEmpAdd(ActionEvent e) {
		showEmployeeUI();
	}

	protected void actionPerformedBtnEmpSearch(ActionEvent e) throws NumberFormatException, SQLException {
		String empNo = JOptionPane.showInputDialog("�˻��� �����ȣ�� �Է��ϼ���");
		if (empNo == null) {
			return;
		}
		Employee selEmp = empDao.selectEmployeeByNo(new Employee(Integer.parseInt(empNo)));
		if (selEmp == null) {
			JOptionPane.showMessageDialog(null, "�ش� ����� �������� �ʽ��ϴ�.");
			return;
		}
		PanelEmployee pEmp = new PanelEmployee();
		pEmp.setCmbModel(deptDao.selectDepartmentByAll());
		pEmp.setEmployee(selEmp);
		pEmp.setTfAllEditable(false);
		JOptionPane.showMessageDialog(null, pEmp, "��� ����", JOptionPane.INFORMATION_MESSAGE);
	}

	protected void actionPerformedBtnDeptSearch(ActionEvent e) throws NumberFormatException, SQLException {
		String deptNo = JOptionPane.showInputDialog("�˻��� �μ���ȣ�� �Է��ϼ���");
		if (deptNo == null) {
			return;
		}
		Department selDept = deptDao.selectDepartmentByNo(new Department(Integer.parseInt(deptNo)));
		if (selDept == null) {
			JOptionPane.showMessageDialog(null, "�ش� �μ��� �������� �ʽ��ϴ�.");
			return;
		}
		PanelDepartment pdept = new PanelDepartment();
		pdept.setDepartment(selDept);
		pdept.setTfAllEditable(false);
		JOptionPane.showMessageDialog(null, pdept, "�μ� ����", JOptionPane.INFORMATION_MESSAGE);
	}

}

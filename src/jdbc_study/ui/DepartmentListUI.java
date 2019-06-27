package jdbc_study.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import jdbc_study.dto.Department;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class DepartmentListUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;
	private List<Department> deptList;
	private JPopupMenu popupMenu;
	private JMenuItem mntmPopUpdate;
	private JMenuItem mntmPopDelete;

	private ErpManagementUI parent;

	public DepartmentListUI() {
		initComponents();
	}

	public void setDepartmentList(List<Department> deptList) {
		this.deptList = deptList;
	}

	private void initComponents() {
		setTitle("�μ� ���");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "�μ� ���", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);

		popupMenu = new JPopupMenu();

		mntmPopUpdate = new JMenuItem("����");
		mntmPopUpdate.addActionListener(this);
		popupMenu.add(mntmPopUpdate);

		mntmPopDelete = new JMenuItem("����");
		mntmPopDelete.addActionListener(this);
		popupMenu.add(mntmPopDelete);

		table.setComponentPopupMenu(popupMenu);
		scrollPane.setComponentPopupMenu(popupMenu);
	}

	public void reloadData() {
		table.setModel(new DefaultTableModel(getRows(), getColumnNames()));

		// �μ���ȣ, �μ����� ��� ����
		tableCellAlignment(SwingConstants.CENTER, 0, 1);
		// ��ġ(��)�� ���� ����
		tableCellAlignment(SwingConstants.RIGHT, 2);
		// �μ���ȣ, �μ���, ��ġ �� ���� (100, 200, 70)���� �����ϸ� ����
		tableSetWidth(100, 200, 70);
	}

	private Object[][] getRows() {
		Object[][] rows = new Object[deptList.size()][];
		for (int i = 0; i < deptList.size(); i++) {
			rows[i] = deptList.get(i).toArray();
		}
		return rows;
	}

	private String[] getColumnNames() {
		return new String[] { "�μ���ȣ", "�μ���", "��ġ(��)" };
	}

	// ���̺� �� ������ ����
	protected void tableCellAlignment(int align, int... idx) {
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(align);

		TableColumnModel model = table.getColumnModel();
		for (int i = 0; i < idx.length; i++) {
			model.getColumn(idx[i]).setCellRenderer(dtcr);
		}
	}

	// ���̺� ���� �� ����
	protected void tableSetWidth(int... width) {
		TableColumnModel cModel = table.getColumnModel();

		for (int i = 0; i < width.length; i++) {
			cModel.getColumn(i).setPreferredWidth(width[i]);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mntmPopUpdate) {
			updateUI();
		}
		if (e.getSource() == mntmPopDelete) {
			deleteUI();
		}
	}

	private void deleteUI() {
		int i = table.getSelectedRow();

		if (table.getModel().getRowCount() == 0) { // �μ������� �������� ���� ���
			parent.showDepartmentUI();
			return;
		}
		if (i < 0 || i > table.getModel().getRowCount() - 1) { // �������� ���� ���
			JOptionPane.showMessageDialog(null, "���õ� �μ��� �����ϴ�.");
			return;
		}

		int deptNo = (int) table.getModel().getValueAt(i, 0);

		parent.actionPerformedBtnDelete(deptNo);
	}

	private void updateUI() {
		int i = table.getSelectedRow();

		if (table.getModel().getRowCount() == 0) { // �μ������� �������� ���� ���
			parent.showDepartmentUI();
			return;
		}
		if (i < 0 || i > table.getModel().getRowCount() - 1) { // �������� ���� ���
			JOptionPane.showMessageDialog(null, "���õ� �μ��� �����ϴ�.");
			return;
		}

		int deptNo = (int) table.getModel().getValueAt(i, 0);
		JOptionPane.showMessageDialog(null, "deptNo = " + deptNo);

		JOptionPane.showMessageDialog(null, deptList.indexOf(new Department(deptNo)));
		JOptionPane.showMessageDialog(null, deptList.get(deptList.indexOf(new Department(deptNo))));
		Department searchDept = deptList.get(deptList.indexOf(new Department(deptNo)));
		parent.showDepartmentUI(searchDept);
	}

	public void setErpManagementUI(ErpManagementUI erpManagementUI) {
		this.parent = erpManagementUI;
	}

}

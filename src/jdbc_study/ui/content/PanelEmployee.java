package jdbc_study.ui.content;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import jdbc_study.dto.Department;
import jdbc_study.dto.Employee;

@SuppressWarnings("serial")
public class PanelEmployee extends JPanel implements ActionListener {
	private JTextField tfEmpNo;
	private JTextField tfEmpName;
	private JTextField tfTitle;
	private JTextField tfManager;
	private JTextField tfSalary;
	private JTextField tfDept;
	private JLabel lblImg;

	private String imgPath;
	private int imgWidth;
	private int imgHeight;
	private JButton btnImgAdd;
	
	private JFileChooser chooser;
	
	public PanelEmployee() {
		imgPath = System.getProperty("user.dir")+"\\images\\";
		imgWidth = 90;
		imgHeight= 150;

		chooser = new JFileChooser(imgPath);
		
		initComponents();
		
		switchImage(imgPath+"noImg.png");
	}
	
	private void initComponents() {
		setBorder(new TitledBorder(null, "사원 정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		lblImg = new JLabel();
		panel.add(lblImg);
		lblImg.setSize(imgWidth, imgHeight);
		lblImg.setPreferredSize(new Dimension(60, 80));
		
		
		btnImgAdd = new JButton("사진 추가");
		btnImgAdd.addActionListener(this);
		panel.add(btnImgAdd);
		
		JPanel pEmp = new JPanel();
		add(pEmp);
		pEmp.setLayout(new GridLayout(0, 2, 20, 10));
		
		JLabel lblEmpNo = new JLabel("사원 번호");
		pEmp.add(lblEmpNo);
		lblEmpNo.setHorizontalAlignment(SwingConstants.RIGHT);
		
		tfEmpNo = new JTextField();
		pEmp.add(tfEmpNo);
		tfEmpNo.setColumns(10);
		
		JLabel lblEmpName = new JLabel("사원명");
		pEmp.add(lblEmpName);
		lblEmpName.setHorizontalAlignment(SwingConstants.RIGHT);
		
		tfEmpName = new JTextField();
		pEmp.add(tfEmpName);
		tfEmpName.setColumns(10);
		
		JLabel lblTitle = new JLabel("직책");
		pEmp.add(lblTitle);
		lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		
		tfTitle = new JTextField();
		pEmp.add(tfTitle);
		tfTitle.setColumns(10);
		
		JLabel lblManager = new JLabel("직속상사");
		pEmp.add(lblManager);
		lblManager.setHorizontalAlignment(SwingConstants.RIGHT);
		
		tfManager = new JTextField();
		pEmp.add(tfManager);
		tfManager.setColumns(10);
		
		JLabel lblSalary = new JLabel("급여");
		pEmp.add(lblSalary);
		lblSalary.setHorizontalAlignment(SwingConstants.RIGHT);
		
		tfSalary = new JTextField();
		pEmp.add(tfSalary);
		tfSalary.setColumns(10);
		
		JLabel lblDept = new JLabel("부서");
		pEmp.add(lblDept);
		lblDept.setHorizontalAlignment(SwingConstants.RIGHT);
		
		tfDept = new JTextField();
		pEmp.add(tfDept);
		tfDept.setColumns(10);
	}
	
	private void switchImage(String filePath) {
		Image tmpIcon = new ImageIcon(filePath).getImage().getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(tmpIcon);
		lblImg.setIcon(imageIcon);
	}
	
	public void setEmployee(Employee emp) {
		tfEmpNo.setText(emp.getEmpNo()+"");
		tfEmpName.setText(emp.getEmpName());
		tfTitle.setText(emp.getTitle());
		tfManager.setText(emp.getManager().getDno()+"");
		tfSalary.setText(emp.getSalary()+"");
		tfDept.setText(emp.getDno().getDeptNo()+"");
		//이미지 나중에
//		lblImg;
	}
	
	public Employee getEmployee() {
		int empNo = Integer.parseInt(tfEmpNo.getText().trim());
		String empName = tfEmpName.getText().trim();
		String title = tfTitle.getText().trim();
		int salary = Integer.parseInt(tfSalary.getText().trim());
		Employee manager = new Employee(Integer.parseInt(tfManager.getText().trim()));
		Department dno = new Department(Integer.parseInt(tfDept.getText().trim()));
		return new Employee(empNo, empName, title, manager, salary, dno);
//		return new Employee(empNo, empName, title, manager, salary, dno, pic)
	}
	
	public void clearTextField() {
		tfEmpNo.setText("");
		tfEmpName.setText("");
		tfTitle.setText("");
		tfManager.setText("");
		tfSalary.setText("");
		tfDept.setText("");
		//이미지
//		lblImg;
	}
	
	public JTextField getTfEmpNo() {
		return tfEmpNo;
	}
	
	public void setTfAllEditable(boolean isEditable) {
		tfEmpNo.setEditable(isEditable);
		tfEmpName.setEditable(isEditable);
		tfTitle.setEditable(isEditable);
		tfManager.setEditable(isEditable);
		tfSalary.setEditable(isEditable);
		tfDept.setEditable(isEditable);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnImgAdd) {
			actionPerformedBtnImgAdd(e);
		}
	}
	
	protected void actionPerformedBtnImgAdd(ActionEvent e) {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF & PNG Images", "jpg", "gif", "png");
		chooser.setFileFilter(filter);
		
		int ret = chooser.showOpenDialog(null);
		if (ret != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
			return;
		}
		String selectedFilePath = chooser.getSelectedFile().getPath();
//		System.out.println(selectedFilePath);
		
		switchImage(selectedFilePath);
		repaint();
		revalidate();
	}
	
	private byte[] getImage(String fileName) {
		byte[] pic = null;

		File imgFile = new File(imgPath, fileName);
		
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
	

	
//	  private File getPicFile(Employee e) throws FileNotFoundException, IOException
//	  { File file = null; file = new File(picsDir, e.getEmpName()+".jpg");
//	  try(FileOutputStream fos = new FileOutputStream(file)){
//	  fos.write(e.getPic()); } return file; }
//	 

}

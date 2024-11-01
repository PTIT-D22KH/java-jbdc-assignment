/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import jbdc.StudentDao;
import models.Student;
import views.StudentManagerView;

/**
 *
 * @author P51
 */
public class StudentManagerController {
    private StudentManagerView view;
    private DefaultTableModel tableModel;
    private ArrayList<Student> students = new ArrayList<>();
    private final StudentDao studentDao;
    private String previousId;
    public StudentManagerController() {
        view = new StudentManagerView();
        studentDao = new StudentDao();
        tableModel = new DefaultTableModel();
        view.getStudentTable().setAutoCreateRowSorter(true);
        addTableColumns();
        view.getStudentTable().setModel(tableModel);
    }
    public void show(){
        view.setVisible(true);
        addEvent();
    }
    private void addTableColumns(){ 
        tableModel.addColumn("Mã sinh viên");
        tableModel.addColumn("Tên sinh viên");
        tableModel.addColumn("Lớp");
        tableModel.addColumn("GPA");
    }
    private void renderTable() throws  SQLException{
        students = studentDao.getAll();
        tableModel.setRowCount(0);
        for (Student x : students) {
            tableModel.addRow(x.toRowTable());
        }
    }
    private void addEvent(){
        addClickTableEvent();
        showButtonEvent();
        addButtonEvent();
        addUpdateEvent();
        addResetEvent();
        addDeleteEvent();
        
    }
    private void addClickTableEvent() {
        view.getStudentTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = view.getStudentTable().getSelectedRow();
                if (selectedRow != -1) {
                    String id = (String)view.getStudentTable().getValueAt(selectedRow, 0);
                    String name = (String)view.getStudentTable().getValueAt(selectedRow, 1);
                    String className = (String)view.getStudentTable().getValueAt(selectedRow, 2);
                    double gpa = (double)view.getStudentTable().getValueAt(selectedRow, 3);
                    String gpaString = String.valueOf(gpa);
                    setTxtField(id, name, className, gpaString);
                    previousId = id;
                }
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
            
        });
    }
    private void addDeleteEvent() {
        view.getDeleteButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = view.getStudentTable().getSelectedRow();
                String id = (String)view.getStudentTable().getValueAt(selectedRow, 0);
                try {
                    studentDao.delete(id);
                    renderTable();
                    setTxtField("", "", "", "");
                } catch (SQLException exception) {
                }
            }
            
        });
    }
    private void addResetEvent(){ 
        view.getResetButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    studentDao.deleteAll();
                    renderTable();
                } catch (SQLException exception) {
                }
                
            }
            
        });
    }
    private void addUpdateEvent() {
        view.getUpdateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = view.getStudentIdTxtField().getText();
                String name = view.getNameTxtField().getText();
                String className = view.getClassNameTxtField().getText();
                String gpaString = view.getGpaTxtField().getText();
                if (checkInput(id, name, className, gpaString)) {
                    
                    double gpa = Double.parseDouble(gpaString);
                    if (!previousId.equals(id) && isExistStudent(id)) {
                        JOptionPane.showMessageDialog(view, "Mã sinh viên đã tồn tại!");
                    } else {
                        Student student = new Student(id, name, className, gpa);
                        try {
                            studentDao.update(previousId, student);
                            renderTable();

                        } catch (SQLException exception) {
                        }
                    }
                    
                } else {
                    JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
                }
                setTxtField("", "", "", "");
                
            }
        });
        
    }
    private void showButtonEvent(){
        view.getShowButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    renderTable();
                } catch (SQLException exception) {
                }
                
            }
            
        });
    }
    private void addButtonEvent() {
        view.getAddButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = view.getStudentIdTxtField().getText();
                String name = view.getNameTxtField().getText();
                String className = view.getClassNameTxtField().getText();
                String gpaString = view.getGpaTxtField().getText();
                if (checkInput(id, name, className, gpaString)) {
                    try {
                        double gpa = Double.parseDouble(gpaString);
                        if (isExistStudent(id)) {
                            JOptionPane.showMessageDialog(view, "Mã sinh viên đã tồn tại!");
                        } else {
                            Student student = new Student(id, name, className, gpa);
                            try {
                                studentDao.add(student);
                                renderTable();
                            } catch (SQLException exception) {
                            }
                        }
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(view, "GPA phải là 1 số thực!");
                    }
                    
                    

                } else {
                    JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
                }
                setTxtField("", "", "", "");
            }
            
        });
    }
    private boolean checkInput(String id, String name, String className, String gpaString) {
        return !(id.isEmpty() || name.isEmpty() || className.isEmpty() || gpaString.isEmpty());
    }
    private boolean isExistStudent(String id) {
        try {
            if(studentDao.checkExists(id)) {
                return true;
            }
        } catch (SQLException ex) {

        }
        return false;
    }
    private void setTxtField(String id, String name, String className, String gpaString) {
        view.getStudentIdTxtField().setText(id);
        view.getNameTxtField().setText(name);
        view.getClassNameTxtField().setText(className);
        view.getGpaTxtField().setText(gpaString);
    }
}

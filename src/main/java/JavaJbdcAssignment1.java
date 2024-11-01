
import controllers.StudentManagerController;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

/**
 *
 * @author P51
 */
public class JavaJbdcAssignment1 {

    public static void main(String[] args) throws  SQLException{
//        Student a = new Student("B22DCKH024", "Vu Cong Tuan Duong", "D22CQKH02-B", 3.5);
//        StudentDao studentDao = new StudentDao();
//        studentDao.add(a);
        try {
            javax.swing.UIManager.setLookAndFeel("com.formdev.flatlaf.FlatIntelliJLaf");
        } catch (Exception ex) {
        }
        StudentManagerController controller = new StudentManagerController();
        controller.show();
        
    }
}

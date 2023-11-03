import java.sql.*;
import java.util.List;

public class DataTool {
    public static void getData(List<Word> words) {
        Connection conn = null;
        Statement stmt;
        stmt = null;
        ResultSet rs = null;
        try {
            // 加载MySQL驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 连接到MySQL数据库
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LexicalUniverse?allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai", "root", "123456");

            // 执行SELECT语句，读取MySQL数据库中的数据
            stmt = conn.createStatement();
            String sql = "SELECT * FROM LexicalUniverse.allword";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int uuid = rs.getInt("uuid");
                String date = rs.getString("word");
                String explanation = rs.getString("explanation");
                words.add(new Word(uuid, date, explanation));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

package atguigu.jdbc.crud;

import atguigu.jdbc.utils.JDBCUtils;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @Author Curry
 * @Create 2020-11-0:15
 */
public class PreparedStatementUpdateTest {

    @Test
    public void test() {
//        String sql = "delete from customers where id=?";
//        commonUpdate(sql, 17);
        String sql = "INSERT INTO customers (name,email,birth)VALUES (?,?,?)";
        commonUpdate(sql, "test","test@qq.com","1995-10-15");
    }

    @Test
    public void commonUpdate(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }
    }

    @Test
    public void testInsert() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "INSERT INTO customers (name,email,birth)VALUES (?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, "test");
            ps.setObject(2, "test@qq.com");
//            SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
//            Date date = sdf.parse("1994-10-15");
            ps.setObject(3, "1994-12-20");
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }
    }

    @Test
    public void testUpdate() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "UPDATE customers SET name=? WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, "curry");
            ps.setObject(2, 17);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }
    }
}





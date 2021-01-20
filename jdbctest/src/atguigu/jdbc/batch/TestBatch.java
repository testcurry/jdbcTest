package atguigu.jdbc.batch;

import atguigu.jdbc.utils.JDBCUtils;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @Author Curry
 * @Create 2020-11-1:36
 */
public class TestBatch {
    @Test
    public void testInsert() {
        long start = System.currentTimeMillis();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);
            String sql = "insert into `user`(name,password,address,phone)values(?,?,?,?)";
            ps = conn.prepareStatement(sql);
            for (int i = 1; i <= 10; i++) {
                ps.setObject(1, "t" + i);
                ps.setObject(2, "test123@324");
                ps.setObject(3, "上海浦东新区");
                ps.setObject(4, "12345454534");
                ps.addBatch();
                if (i % 10 == 0) {
                    ps.executeBatch();
                    ps.clearBatch();
                }
            }
            conn.commit();
            long end = System.currentTimeMillis();
            System.out.println("花费的时间为:" + (end - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }
    }
}

package atguigu.jdbc.transaction;

import atguigu.jdbc.bean.UserTable;
import atguigu.jdbc.utils.JDBCUtils;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * @Author Curry
 * @Create 2020-11-17:34
 */
public class TestTransaction {

    //不考虑事务的转账操作

    @Test
    public void testUpdate() {
        String sql1 = "update user_table set balance=balance-100 where user=?";
        update(sql1, "AA");
        //模拟网络异常
        System.out.println(10 / 0);
        String sql2 = "update user_table set balance=balance+100 where user=?";
        update(sql2, "BB");
        System.out.println("转账成功");
    }

    /**
     * 考虑上事务的转账操作**********
     */

    @Test
    public void testUpdateWithTx() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "update user_table set balance=balance-100 where user=?";
            updateTx(conn, sql1, "AA");
            //模拟网络异常
//            System.out.println(10 / 0);
            String sql2 = "update user_table set balance=balance+100 where user=?";
            updateTx(conn, sql2, "BB");
            System.out.println("转账成功");

            //提交数据
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //回滚数据
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            JDBCUtils.closeResource(conn, null);
        }

    }

    @Test
    public void test() {
        String sql = "update user_table set balance=1000 where user=?";
        int count = update(sql, "DD");
        if (count > 0) {
            System.out.println("修改成功！");
        } else {
            System.out.println("修改失败！");
        }
    }

    //    通用的增删改查操作version1.0
    public int update(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            count = ps.executeUpdate();
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JDBCUtils.closeResource(conn, ps);
        }
        return 0;
    }

    //    *****考虑上事务的增删改查操作*****通用的增删改查操作version2.0
    public int updateTx(Connection conn, String sql, Object... args) {
        PreparedStatement ps = null;
        int count = 0;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            count = ps.executeUpdate();
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, ps);
        }
        return 0;
    }

    @Test
    public void testTransactionSelect() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        //获取当前数据库的隔离级别
        int transactionIsolation = conn.getTransactionIsolation();
        System.out.println(transactionIsolation);
        //设置数据库的隔离级别
        conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
        conn.setAutoCommit(false);
        String sql = "select user,password,balance from user_table where user=?";
        UserTable user = getInstanceTx(conn, UserTable.class, sql, "AA");
        System.out.println(user);
    }


    /**
     * 数据库隔离级别为read_uncommited时的转账效果
     */
    @Test
    public void testTxUpdate() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        conn.setAutoCommit(false);
        String sql = "update user_table set balance=? where user=?";
        updateTx(conn, sql, 5000, "AA");
        Thread.sleep(15000);
        System.out.println("修改结束");

    }

    /**
     * 考虑上事务的通用的查询操作，返回表中的一套记录 version2.0
     */
    public <T> T getInstanceTx(Connection conn, Class<T> clazz, String sql, Object... args) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            T t = clazz.newInstance();
            if (rs.next()) {
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = rs.getObject(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, ps, rs);
        }
        return null;
    }
}

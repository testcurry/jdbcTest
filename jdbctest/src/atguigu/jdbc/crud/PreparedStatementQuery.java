package atguigu.jdbc.crud;

import atguigu.jdbc.bean.Customer;
import atguigu.jdbc.bean.Order;
import atguigu.jdbc.utils.JDBCUtils;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

/**
 * @Author Curry
 * @Create 2020-11-23:44
 */
public class PreparedStatementQuery {

    @Test
    public void test() throws Exception {
//        String sql0 = "Select `order_id` orderId,`order_name` orderName,`order_date` orderDate from `order` where order_id=?";
//        String sql = "SELECT `id`,`name`,`email`,`birth` FROM `customers` where id =?";
//        Customer customer = getInstance(Customer.class, sql, 1);
//        Order order = getInstance(Order.class, sql0, 1);
//        System.out.println(customer);
//        System.out.println(order);
        String sql2 = "SELECT `id`,`name`,`email`,`birth` FROM `customers` where id <?";
        String sql3 = "Select `order_id` orderId,`order_name` orderName,`order_date` orderDate from `order` where order_id<?";
        ArrayList list = getForList(Customer.class, sql2, 10);
        ArrayList list1 = getForList(Order.class, sql3, 10);
        list.forEach(System.out::println);
        list1.forEach(System.out::println);
    }

    /**
     * 针对于不同表的查询操作，返回多条记录
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */

    public <T> ArrayList<T>  getForList(Class<T> clazz,String sql,Object...args)   {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1, args[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            ArrayList<T> list = new ArrayList<T>();
            while (rs.next()){
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = rs.getObject(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,rs );
        }
        return null;
    }

    /**
     * 针对不同的表的通用的查询操作，返回表中的一条记录
     *
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T getInstance(Class<T> clazz, String sql, Object... args) throws Exception {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
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
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
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

    /**
     * 针对于不同表的查询操作，返回多条记录，并考虑上事务  version2.0
     *
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> ArrayList<T> getForListTX(Connection conn, Class<T> clazz, String sql, Object... args) {
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
            ArrayList<T> list = new ArrayList<T>();
            while (rs.next()) {
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = rs.getObject(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }
}

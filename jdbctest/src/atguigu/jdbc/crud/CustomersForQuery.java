package atguigu.jdbc.crud;

import atguigu.jdbc.bean.Customer;
import atguigu.jdbc.bean.Order;
import atguigu.jdbc.utils.JDBCUtils;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * @Author Curry
 * @Create 2020-11-0:58
 */
public class CustomersForQuery {

    @Test
    public void testquery() {
//        String sql = "SELECT `id`,`name`,`email`,`birth` FROM `customers` where id =?";
//        Customer customer = commonQueryForCustomser(sql, 1);
//        System.out.println(customer);
        String sql1= "Select `order_id` orderId,`order_name` orderName,`order_date` orderDate from `order` where order_id=?";
        Order order = commonQueryForCustomser(sql1, 1);
        System.out.println(order);
    }

    /**
     * 针对customser表通用的增删改查操作
     *
     * @throws Exception
     */
    public Order commonQueryForCustomser(String sql, Object... args) {
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
//            Customer customer = new Customer();
            Order order = new Order();
            if (rs.next()) {
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = rs.getObject(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Field field = Order.class.getDeclaredField(columnLabel);
                    field.setAccessible(true);
//                    field.set(customer, columnValue);
                    field.set(order, columnValue);
                }
                return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }


    /**
     * 查询customer表的一条记录
     */
    @Test
    public void testQuery1() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT `id`,`name`,`email`,`birth` FROM `customers` where id =?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, 1);
            rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                Date birth = rs.getDate(4);
                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
    }
}

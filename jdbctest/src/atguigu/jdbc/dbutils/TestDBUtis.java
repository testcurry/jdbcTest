package atguigu.jdbc.dbutils;/**
 * @Author Curry
 * @Create 2020-11-1:35
 */

import atguigu.jdbc.bean.Customer;
import atguigu.jdbc.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Description todo
 * @ClassName TestDBUtis
 * @Author admin
 * @Date 2020/11/18 1:35
 * @Version 1.0
 */
public class TestDBUtis {
    //测试插入操作
    @Test
    public void testInsert() {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getconnectionByDruid();
            String sql = "insert into customers (name,email,birth)values(?,?,?)";
            int count = runner.update(conn, sql, "caixukun", "test@qq.com", "1997-09-08");
            System.out.println("插入" + count + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    //测试查询
    @Test
    public void testQuery() {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getconnectionByDruid();
            String sql = "select id,name,email,birth from customers where id=?";
            BeanHandler<Customer> hander = new BeanHandler<Customer>(Customer.class);
            Customer customer = runner.query(conn, sql, hander, 12);
            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    @Test
    public void testQuery2() {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getconnectionByDruid();
            BeanListHandler<Customer> beanListHandler = new BeanListHandler<>(Customer.class);
            String sql = "select id,name,email,birth from customers where id<?";
            List<Customer> list = runner.query(conn, sql, beanListHandler, 12);
            Iterator<Customer> iterator = list.iterator();
            while (iterator.hasNext()) {
                Customer customer = iterator.next();
                System.out.println(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn);
        }
    }

    @Test
    public void testQuery3() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getconnectionByDruid();
            QueryRunner runner = new QueryRunner();
            String sql = "select id,name,email,birth from customers where id=?";
            MapHandler mapHandler = new MapHandler();
            Map<String, Object> customer = runner.query(conn, sql, mapHandler, 12);
            System.out.println(customer);
//            Set<Map.Entry<String, Object>> entries = customer.entrySet();
//            Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
//            while (iterator.hasNext()) {
//                Map.Entry<String, Object> customerEntry = iterator.next();
//                System.out.print(customerEntry);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn);
        }
    }

    @Test
    public void testQuer3() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getconnectionByDruid();
            QueryRunner runner = new QueryRunner();
            String sql = "select id,name,email,birth from customers where id<?";
            MapListHandler mapListHandler = new MapListHandler();
            List<Map<String, Object>> list = runner.query(conn, sql, mapListHandler, 12);
            list.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    @Test
    //查询特殊值
    public void testQuery4() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getconnectionByDruid();
            QueryRunner runner = new QueryRunner();
            ScalarHandler<Long> scalarHandler = new ScalarHandler<>();
            String sql = "select count(*) from customers";
            long count = runner.query(conn, sql, scalarHandler);
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }

    }

    @Test
    public void testQuery6() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getconnectionByDruid();
            QueryRunner runner = new QueryRunner();
            ScalarHandler<Date> scalarHandler = new ScalarHandler<>();
            String sql = "select max(birth) from customers";
            Date maxBirth = runner.query(conn, sql, scalarHandler);
            System.out.println(maxBirth);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    //自定义ResultSethandler的实现类

    @Test
    public void test() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getconnectionByDruid();
            QueryRunner runner = new QueryRunner();
            String sql = "select id,name,email,birth from customers where id=?";
            ResultSetHandler<Customer> handler = new ResultSetHandler<Customer>() {
                @Override
                public Customer handle(ResultSet rs) throws SQLException {
                    if (rs.next()) {
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        String email = rs.getString("email");
                        Date birth = rs.getDate("birth");
                        Customer customer = new Customer(id, name, email, birth);
                        return customer;
                    }
                    return null;
                }
            };
            Customer customer = runner.query(conn, sql, handler, 1);
            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }

    }

}

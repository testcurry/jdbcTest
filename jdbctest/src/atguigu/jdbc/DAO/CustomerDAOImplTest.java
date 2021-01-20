package atguigu.jdbc.DAO;

import atguigu.jdbc.bean.Customer;
import atguigu.jdbc.utils.JDBCUtils;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * @Author Curry
 * @Create 2020-11-0:05
 */
public class CustomerDAOImplTest {
    CustomerDAOImpl dao = new CustomerDAOImpl();

    @Test
    public void testInsert() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Customer customer = new Customer(22, "test", "test@qq.com", new Date(1234123412343L));
            dao.insert(conn, customer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn);
        }
    }

    @Test
    public void testDeleteByid()  {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            dao.deleteByid(conn,22 );

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn);
        }
    }

    @Test
    public void testUpdateCust()  {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Customer customer = new Customer(20, "testcy", "test@126.com", new Date(324234234L));
            dao.updateCust(conn, customer);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn);
        }
    }

    @Test
    public void testGetCustomerByid()  {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Customer customer = dao.getCustomerByid(conn, 21);
            System.out.println(customer);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn);
        }
    }

    @Test
    public void testGetAll()  {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            List<Customer> list = dao.getAll(conn);
            list.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn);
        }
    }

    @Test
    public void testGetCount() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Long count = dao.getCount(conn);
            System.out.println(count);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn);
        }
    }

    @Test
    public void testGetMaxBirth() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Date maxBirth = dao.getMaxBirth(conn);
            System.out.println(maxBirth);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn);
        }
    }
}

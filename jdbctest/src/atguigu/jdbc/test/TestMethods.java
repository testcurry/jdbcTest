package atguigu.jdbc.test;

import atguigu.jdbc.DAO.CustomerDAOImpl;
import atguigu.jdbc.bean.Customer;
import atguigu.jdbc.utils.JDBCUtils;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.util.List;

/**
 * @Author Curry
 * @Create 2020-11-21:31
 */
public class TestMethods {
    @Test
    public void testGetConnection() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        System.out.println(conn);
    }

    @Test
    public void testGetConnectionPool() throws Exception {
//        Connection conn = JDBCUtils.getConnectionByC3p0();
//        Connection conn = JDBCUtils.getConnectionByDBCP();
        Connection conn = JDBCUtils.getconnectionByDruid();
        System.out.println(conn);
        CustomerDAOImpl dao = new CustomerDAOImpl();
        List<Customer> list = dao.getAll(conn);
        list.forEach(System.out::println);
    }
}

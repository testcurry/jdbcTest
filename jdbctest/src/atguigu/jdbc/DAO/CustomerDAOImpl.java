package atguigu.jdbc.DAO;/**
 * @Author Curry
 * @Create 2020-11-23:35
 */

import atguigu.jdbc.bean.Customer;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description todo
 * @ClassName CustomerDAOImpl
 * @Author admin
 * @Date 2020/11/15
 * @Version 1.0
 */
public class CustomerDAOImpl extends BaseDAO implements CustomerDAO {
    @Override
    public void insert(Connection conn, Customer customer) {
        String sql = "insert into customers(name,email,birth)values(?,?,?)";
        update(conn, sql, customer.getName(), customer.getEmail(), customer.getBirth());
    }

    @Override
    public void deleteByid(Connection conn,  int id) {
        String sql="delete from customers where id=?";
        update(conn, sql, id);
    }

    @Override
    public void updateCust(Connection conn, Customer customer) {
        String sql = "update customers set name=?, email=?, birth=? where id=?";
        update(conn, sql,customer.getName(), customer.getEmail(), customer.getBirth(), customer.getId());
    }

    @Override
    public Customer getCustomerByid(Connection conn, int id) {
        String sql = "select id,name,email,birth from customers where id =?";
        Customer customer = getInstanceTx(conn, Customer.class, sql, id);
        return customer;
    }

    @Override
    public List<Customer> getAll(Connection conn) {
        String sql="select id,name,birth,email from customers";
        ArrayList<Customer> list = getForList(conn, Customer.class, sql);
        return list;
    }

    @Override
    public Long getCount(Connection conn) {
        String sql="select count(*) from customers";
        Long value = getValue(conn, sql);
        return value;
    }

    @Override
    public Date getMaxBirth(Connection conn) {
        String sql="select max(birth) from customers";
        Date maxBirth = getValue(conn, sql);
        return maxBirth;
    }
}

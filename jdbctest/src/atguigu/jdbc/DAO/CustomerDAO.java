package atguigu.jdbc.DAO;/**
 * @Author Curry
 * @Create 2020-11-22:00
 */

import atguigu.jdbc.bean.Customer;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * @Description customer表所有的功能规范
 * @ClassName CustomerDAO
 * @Author admin
 * @Date 2020/11/15
 * @Version 1.0
 */
public interface CustomerDAO {

    /**
     * @param conn
     * @param customer
     * @return void
     * @Description 将对象添加到数据库中
     * @Author Testcy
     * @Date 23:12 2020/11/15
     */

    void insert(Connection conn, Customer customer);

    /**
     * @param conn
     * @param id
     * @return void
     * @Description 根据id删除表中的一条记录
     * @Author Testcy
     * @Date 23:05 2020/11/15
     */
    void deleteByid(Connection conn,  int id);

    /**
     * @param conn
     * @param customer
     * @return void
     * @Description 针对内存中的customer对象，去修改数据表中指定的记录
     * @Author Testcy
     * @Date 23:16 2020/11/15
     */
    void updateCust(Connection conn, Customer customer);

    /**
     * @param conn
     * @param id
     * @return atguigu.jdbc.bean.Customer
     * @Description 根据id查询得到对应的customer对象
     * @Author Testcy
     * @Date 23:18 2020/11/15
     */
    Customer getCustomerByid(Connection conn, int id);

    /**
     * @param conn
     * @return java.util.List<atguigu.jdbc.bean.Customer>
     * @Description 查询表彰所有的记录构成的集合
     * @Author Testcy
     * @Date 23:20 2020/11/15
     */
    List<Customer> getAll(Connection conn);

    /**
     * @param conn
     * @return java.lang.Long
     * @Description
     * @Author Testcy
     * @Date 23:22 2020/11/15
     */
    Long getCount(Connection conn);

    /**
     * @param conn
     * @return java.util.Date
     * @Description 返回数据表中最大的生日
     * @Author Testcy
     * @Date 23:23 2020/11/15
     */
    Date getMaxBirth(Connection conn);
}

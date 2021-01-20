package atguigu.jdbc.poolTest;/**
 * @Author Curry
 * @Create 2020-11-21:54
 */


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.testng.annotations.Test;

import java.sql.Connection;

/**
 * @Description c3p0数据库连接池技术获取数据库连接
 * @ClassName c3p0Test
 * @Author admin
 * @Date 2020/11/17 21:54
 * @Version 1.0
 */
public class c3p0Test {

    //方式一
    @Test
    public void getConnection2() throws Exception {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.jdbc.Driver"); //loads the jdbc driver
        cpds.setJdbcUrl("jdbc:mysql:///test?useUnicode=true&characterEncoding=UTF-8&rewriteBatchedStatements=true");
        cpds.setUser("root");
        cpds.setPassword("123456");
        Connection connection = cpds.getConnection();
        System.out.println(connection);
    }

    @Test
    public void getConnectionByC3p0() throws Exception {
        ComboPooledDataSource cpds = new ComboPooledDataSource("c3p0-config");
        Connection connection = cpds.getConnection();
        System.out.println(connection);
    }
}

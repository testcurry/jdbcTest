package atguigu.jdbc.poolTest;/**
 * @Author Curry
 * @Create 2020-11-23:30
 */

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 *@Description todo
 *@ClassName DBCPTest
 *@Author admin
 *@Date 2020/11/17 23:30
 *@Version 1.0
 */
public class DBCPTest {

    @Test
    public void getConnection() throws SQLException {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&rewriteBatchedStatements=true");
        source.setUsername("root");
        source.setPassword("123456");
        Connection connection = source.getConnection();
        System.out.println(connection);
    }

    @Test

    public void getConnectionByDBCP() throws Exception {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
        Properties pros = new Properties();
        pros.load(is);
        BasicDataSource source = BasicDataSourceFactory.createDataSource(pros);
        Connection conn = source.getConnection();
        System.out.println(conn);
    }
}

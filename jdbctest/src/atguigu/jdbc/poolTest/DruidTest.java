package atguigu.jdbc.poolTest;/**
 * @Author Curry
 * @Create 2020-11-0:42
 */

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 *@Description todo
 *@ClassName DruidTest
 *@Author admin
 *@Date 2020/11/18 0:42
 *@Version 1.0
 */
public class DruidTest {
    @Test
    public void getconnectionByDruid() throws Exception {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
        Properties pros = new Properties();
        pros.load(is);
        DataSource source = DruidDataSourceFactory.createDataSource(pros);
        Connection conn = source.getConnection();
        System.out.println(conn);
    }
}

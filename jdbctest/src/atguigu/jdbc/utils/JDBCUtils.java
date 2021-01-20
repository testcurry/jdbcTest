package atguigu.jdbc.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.*;
import java.sql.*;
import java.util.Properties;

/**
 * @Author Curry
 * @Create 2020-11-23:53
 */
public class JDBCUtils {

    /**
     * 获取数据库连接
     *
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros = new Properties();
        pros.load(is);
        String url = pros.getProperty("url");
        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String driverClass = pros.getProperty("driverClass");
        Class.forName(driverClass);
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    /**
     * @param
     * @return void
     * @Description 通过dpcp技术获取数据库连接
     * @Author Testcy
     * @Date 0:31 2020/11/18
     */
    private static BasicDataSource source;

    static {

        try {
            Properties pros = new Properties();
            FileInputStream is = new FileInputStream(new File("src/dbcp.properties"));
            pros.load(is);
            source = BasicDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Connection getConnectionByDBCP() throws Exception {
        Connection conn = source.getConnection();
        return conn;
    }

    /**
     * @param
     * @return java.sql.Connection
     * @Description 通过druid技术获取数据库连接
     * @Author Testcy
     * @Date 0:57 2020/11/18
     */
    private static DataSource source1;

    static {
        try {
            Properties pros = new Properties();
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
            pros.load(is);
            source1 = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getconnectionByDruid() throws Exception {
        Connection conn = source1.getConnection();
        return conn;
    }

    /**
     * @param
     * @return void
     * @Description 用c3p0数据库连接池技术获取数据库连接
     * @Author Testcy
     * @Date 23:28 2020/11/17
     */
    private static ComboPooledDataSource cpds = new ComboPooledDataSource("c3p0-config");

    public static Connection getConnectionByC3p0() throws Exception {
        Connection conn = cpds.getConnection();
        return conn;
    }

    /**
     * 关闭数据库连接资源
     *
     * @param conn
     */
    public static void closeResource(Connection conn) {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭数据库连接资源
     *
     * @param conn
     * @param ps
     */
    public static void closeResource(Connection conn, Statement ps) {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 关闭数据库连接资源
     *
     * @param conn
     * @param ps
     * @param rs
     */
    public static void closeResource(Connection conn, Statement ps, ResultSet rs) {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package atguigu.jdbc.blob;

import atguigu.jdbc.bean.Customer;
import atguigu.jdbc.utils.JDBCUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;

/**
 * @Author Curry
 * @Create 2020-11-0:46
 */
public class BlobTest {

    /**
     * 插入blob类型的数据
     */
    @Test
    public void testBlob() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql="insert into `customers`(name,email,birth,photo)values(?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, "girl");
            ps.setObject(2, "test@qq.com");
            ps.setObject(3, "1993-11-11");
            FileInputStream is = new FileInputStream(new File("testjava.jpg"));
            ps.setBlob(4, is);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }
    }

    /**
     * 查询blob类型的数据
     */
    @Test
    public void testDownload() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql="select id,name,email,birth,photo from customers where id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, 21);
            rs = ps.executeQuery();
            if (rs.next()){

                int id = rs.getInt("id");
                String name = rs.getString("name");
                Date birth = rs.getDate("birth");
                String email = rs.getString("email");
                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);

                //将blob类型的数据下载下来
                Blob photo = rs.getBlob("photo");
                InputStream is = photo.getBinaryStream();
                FileOutputStream fos = new FileOutputStream("girl.jpg");
                byte[] buffer = new byte[1024];
                int len;
                while ((len=is.read(buffer))!=-1){
                    fos.write(buffer, 0, len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
    }

}

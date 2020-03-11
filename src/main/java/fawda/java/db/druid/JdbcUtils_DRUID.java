package fawda.java.db.druid;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * <b>修订原因:</b> 初始化创建.详细说明:<br>
 *       <br>
 *
 * <b>时间:</b> <i>2019年08月07日 下午3:19</i>
 *
 * @author Fawda: liuyl
 */
public class JdbcUtils_DRUID {
    /**
     * 在java中，编写数据库连接池需实现java.sql.DataSource接口，每一种数据库连接池都是DataSource接口的实现
     * DBCP连接池就是java.sql.DataSource接口的一个具体实现
     */
    private static List<DataSource> ds = new ArrayList<DataSource>();

    //在静态代码块中创建数据库连接池
    static {
        try {
            //加载dbcpconfig.properties配置文件
            InputStream in = JdbcUtils_DRUID.class.getClassLoader().getResourceAsStream("fawda/java/db/druid/connection.properties");
            Properties prop = new Properties();
            prop.load(in);
            //创建数据源
            ds.add(DruidDataSourceFactory.createDataSource(prop));
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }

    }

    /**
     * @return Connection
     * @throws SQLException
     * @Method: getConnection
     * @Description: 从数据源中获取数据库连接
     * @Anthor:孤傲苍狼
     */
    public static List<Connection> getConnection() throws SQLException {
        //从数据源中获取数据库连接
        ArrayList<Connection> connections = new ArrayList<>();
        for (DataSource d : ds) {
            connections.add(d.getConnection());
        }
        return connections;
    }

    /**
     * <b>修订原因:</b> 初始化创建.详细说明:<br>
     *   释放资源，
     *   释放的资源包括Connection数据库连接对象，负责执行SQL命令的Statement对象，存储查询结果的ResultSet对象<br>
     *
     * <b>时间:</b> <i>2019年08月07日 下午3:21</i>
     *
     * @author Fawda: liuyl
     */
    public static void release(Connection conn, Statement st, ResultSet rs) {
        if (rs != null) {
            try {
                //关闭存储查询结果的ResultSet对象
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if (st != null) {
            try {
                //关闭负责执行SQL命令的Statement对象
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                //将Connection连接对象还给数据库连接池
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
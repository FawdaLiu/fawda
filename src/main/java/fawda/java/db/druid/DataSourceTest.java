package fawda.java.db.druid;

import fawda.java.db.dbcp.JdbcUtils_DBCP;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <b>时间:</b> <i>2019-08-07 15:18</i> 修订原因:初始化创建.详细说明:<br>
 * <br>
 * Action类{@linkplain fawda.java.db.druid}使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author Fawda liuyl @since 1.0
 **/
public class DataSourceTest {
    private static List<Connection> conn;
    private static PreparedStatement st;
    private static ResultSet rs;

    static {
        try {
            conn = new ArrayList<Connection>();
            for (Connection connection : JdbcUtils_DRUID.getConnection()) {
                connection.setAutoCommit(false);
                conn.add(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpOA_READRECORD() throws SQLException {
        Connection connection = conn.get(0);
        String sql = "SELECT A.OARR_UNID FROM OA_READRECORD A WHERE A.ROWID > (SELECT MIN(ROWID) FROM OA_READRECORD B WHERE A.OARR_UNID = B.OARR_UNID) ORDER BY A.OARR_UNID";
        st = connection.prepareStatement(sql);
        rs = st.executeQuery();

        String upSql = "UPDATE OA_READRECORD SET OARR_UNID=NEXT.NEXTVAL WHERE OARR_UNID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(upSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        while (rs.next()) {
            preparedStatement.setInt(1, rs.getInt(1));
            preparedStatement.addBatch();
        }
        int[] tt = preparedStatement.executeBatch();
        System.out.println(tt.length);
        // connection.commit();
        // connection.setAutoCommit(true);
    }
    @Test
    public void testUpWF_HISTORY() throws SQLException {
        Connection connection = conn.get(0);
        String sql = "SELECT A.WFHISTORY_UNID FROM WF_HISTORY A WHERE A.ROWID > (SELECT MIN(ROWID) FROM WF_HISTORY B WHERE A.WFHISTORY_UNID = B.WFHISTORY_UNID) ORDER BY A.WFHISTORY_UNID";
        st = connection.prepareStatement(sql);
        rs = st.executeQuery();

        String upSql = "UPDATE WF_HISTORY SET WFHISTORY_UNID=NEXT.NEXTVAL WHERE WFHISTORY_UNID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(upSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        while (rs.next()) {
            preparedStatement.setInt(1, rs.getInt(1));
            preparedStatement.addBatch();
        }
        int[] tt = preparedStatement.executeBatch();
        System.out.println(tt.length);
        // connection.commit();
        // connection.setAutoCommit(true);
    }

    @Test
    public void testUpOA_FILE_READ() throws SQLException {
        Connection connection = conn.get(0);
        String sql = "SELECT a.OAFIPR_UNID,a.OAFIPR_UUID,a.OAFIPR_FILEUUID FROM OA_FILE_READ A WHERE A.ROWID > (SELECT MIN(ROWID) FROM OA_FILE_READ B WHERE A.OAFIPR_UNID = B.OAFIPR_UNID) ORDER BY A.OAFIPR_UNID";
        st = connection.prepareStatement(sql);
        rs = st.executeQuery();

        String upSql = "UPDATE OA_FILE_READ SET OAFIPR_UNID=NEXT.NEXTVAL, OAFIPR_UUID=? WHERE OAFIPR_UNID=? AND OAFIPR_FILEUUID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(upSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        while (rs.next()) {
            preparedStatement.setString(1, String.valueOf(UUID.randomUUID()).replaceAll("-","").toUpperCase());
            preparedStatement.setInt(2, rs.getInt(1));
            preparedStatement.setString(3, rs.getString(3));
            preparedStatement.addBatch();
        }
        int[] tt = preparedStatement.executeBatch();
        System.out.println(tt.length);
        // connection.commit();
        // connection.setAutoCommit(true);
    }

    @Test
    public void testUuid() {
        System.out.println(String.valueOf(UUID.randomUUID()).replaceAll("-","").toUpperCase());
    }
}

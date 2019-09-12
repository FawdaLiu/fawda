package fawda.java.db.dbcp;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.risen.base.encry.DesHexPwdCoder;
import fawda.java.security.DESUtil;

public class DataSourceTest {
    private static List<Connection> conn;
    private static PreparedStatement st;
    private static ResultSet rs;


    static {
        try {
            conn = new ArrayList<Connection>();
            for (Connection connection : JdbcUtils_DBCP.getConnection()) {
                connection.setAutoCommit(false);
                conn.add(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void testDmInsert() throws SQLException {
        String sql = "insert into \"RISENPT_HZRISEN_TRAIN\".\"RSPRJ_PROJECT\"(\"PRJ_UNID\", \"PRJ_UUID\", \"PRJ_CREATE_TIME\", \"PRJ_UPDATE_TIME\", \"PRJ_STATUS\", \"PRJ_ORDER\", \"PRJ_CREATOR\", \"PRJ_UPDATER\", \"PRJ_PROJECT_NO\", \"PRJ_PROJECT_NAME\", \"PRJ_PROJECT_TYPE1\", \"PRJ_PROJECT_TYPE2\", \"PRJ_UNIT_NAME\", \"PRJ_UNIT_INFO\", \"PRJ_UNIT_ADRRESS\", \"PRJ_UNIT_ZIPCODE\", \"PRJ_UNIT_PHONE\", \"PRJ_UNIT_FAX\", \"PRJ_CONTACT_PERSON\", \"PRJ_PERSON_INFO\", \"PRJ_CONTACT_PHONE\", \"PRJ_CONTACT_MOBILE\", \"PRJ_CONTACT_EMAIL\", \"PRJ_SIGN_DATE\", \"PRJ_CHECK_DATE\", \"PRJ_TOTAL_AMOUNT\", \"PRJ_HARDWARE_AMOUNT\", \"PRJ_SOFTWARE_AMOUNT\", \"PRJ_BUDGET_EXPEND\", \"PRJ_MAINTAIN_MODE\", \"PRJ_MAINTAIN_END\", \"PRJ_MARGIN_AMOUNT\", \"PRJ_SALESMAN_UUID\", \"PRJ_SALESMAN_NAME\", \"PRJ_MANAGER_UUID\", \"PRJ_MANAGER_NAME\", \"PRJ_MEMBERS_UUIDS\", \"PRJ_MEMBERS_NAMES\", \"PRJ_CHARGE_DEPT_UUID\", \"PRJ_CHARGE_DEPT_NAME\", \"PRJ_ASSIST_DEPT_UUIDS\", \"PRJ_ASSIST_DEPT_NAMES\", \"PRJ_ASSIST_MEMBERS_UUIDS\", \"PRJ_ASSIST_MEMBERS_NAMES\", \"PRJ_PLANED_COMPLETE\", \"PRJ_PLANED_COST\", \"PRJ_START_TIME\", \"PRJ_COMPLETE\", \"PRJ_INITIAL_CHECK\", \"PRJ_REALITY_INITIAL_CHECK\", \"PRJ_FINAL_CHECK\", \"PRJ_REALITY_FINAL_CHECK\", \"PRJ_TAX0_AMOUT\", \"PRJ_TAX6_AMOUT\", \"PRJ_TAX17_AMOUT\", \"PRJ_CONSTRUCT_INFOME\", \"PRJ_CONTRACT_ATTACHUUID\", \"PRJ_CONTRACT_ATTACHNAME\", \"PRJ_INVOICE_COLLECTION\", \"PRJ_ACCOUNT_COLLECTION\", \"PRJ_INVOICE_PAYMENT\", \"PRJ_ACCOUNT_PAYMENT\", \"PRJ_RUNNING_STATUS\", \"PRJ_RUNNING_PROGRESS\", \"PRJ_CONTRACT_STATUS\", \"PRJ_UNIT_UUID\", \"PRJ_SETUP_YEARS\", \"PRJ_CONTRACT_UUID\") VALUES(GLOBAL_NEXT.NEXTVAL, '11', to_date('16-10-2018', 'dd-mm-yyyy'), to_date('16-10-2018', 'dd-mm-yyyy'), '0D', 81, 'admin', 'admin', 'YJ20160825N0081', '海曙区短信平台', 'YJ', null, '海曙区政府', '联系电话:13375402658', null, null, null, null, '翟天菱', '联系电话:13634653840', null, null, null, to_date('25-08-2016', 'dd-mm-yyyy'), to_date('03-03-2017', 'dd-mm-yyyy'), 4546000, 4546000, 0, 3864100, '2', to_date('02-03-2020', 'dd-mm-yyyy'), 136380, 'CRACT_254', '王吉', 'CRACT_228', '梅忠杰', 'CRACT_232', '谢志东', 'CRORG_860', '电子政务事业二部', null, null, null, null, to_date('03-03-2017', 'dd-mm-yyyy'), 2773060, to_date('27-08-2016', 'dd-mm-yyyy'), to_date('12-03-2017', 'dd-mm-yyyy'), to_date('29-11-2016', 'dd-mm-yyyy'), null, to_date('08-03-2017', 'dd-mm-yyyy'), null, 2273000, 1295610, 977390, null, null, null, null, null, null, null, null, null, null, null, null, null);";
        st = conn.get(0).prepareStatement(sql);
        int i = st.executeUpdate(sql);
        System.out.println(i);
    }


    public static void testJCSelect() throws SQLException {
        String sql = "SELECT  distinct(CRACT_NAME), CRACT_PWD, CRACT_PWD_EXCHG, CRACT_UUID FROM RISENPT_HZRISEN_TRAIN2.CORE_ACCOUNT;";
        st = conn.get(0).prepareStatement(sql);
        rs = st.executeQuery();

        String updateSql = "update RISENPT_HZRISEN_TRAIN2.CORE_ACCOUNT set CRACT_PWD=?, CRACT_PWD_EXCHG=? where CRACT_UUID=?";
        PreparedStatement preparedStatement = conn.get(0).prepareStatement(updateSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        DesHexPwdCoder desPwdEncoder = new DesHexPwdCoder();

        try {
            String content = "测试使用字符串";
            File file = new File("./test2.txt");
            //文件不存在时候，主动穿件文件。
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file, false);
            BufferedWriter bw = new BufferedWriter(fw);
            while (rs.next()) {
                int pwd = (int) ((Math.random() * 9 + 1) * 100000);
                preparedStatement.setString(1, DESUtil.encodeMD5Hex(Integer.toString(pwd)));
                preparedStatement.setString(2, desPwdEncoder.encode(Integer.toString(pwd)));
                preparedStatement.setString(3, rs.getString(4));
                preparedStatement.addBatch();
                System.out.println(rs.getString(1) + "    " + pwd);
                bw.write(rs.getString(1) + "," + pwd + "\r\n");
            }

            bw.close();
            fw.close();
            System.out.println("test2 done!");
            long start = System.currentTimeMillis();
            int[] tt = preparedStatement.executeBatch();
            long end = System.currentTimeMillis();
            System.out.println("update : " + tt.length);
            System.out.println(end - start);

            //提交，设置事务初始值
            // conn.commit();
            // conn.setAutoCommit(true);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }


    public static void testImportCoreAccount() {
        try {
            String sql = "SELECT CRACT_UNID, CRACT_UUID, CRACT_CRORG_UUID, CRACT_ID_NUMBER, CRACT_MOBILE, CRACT_EMAIL, CRACT_CODE, CRACT_PASSWORD, CRACT_NAME, CRACT_STATUS, CRACT_NATURE, CRACT_CDATE, CRACT_UDATE, CRACT_CUSR, CRACT_ORD FROM CORE_ACCOUNT";
            st = conn.get(0).prepareStatement(sql);
            rs = st.executeQuery();

            String insertSql = "INSERT INTO CORE_ACCOUNT (CRACT_UNID, CRACT_UUID, CRACT_ORG_OWNERS, CRACT_ID_NO, CRACT_MOBILE, CRACT_EMAIL, CRACT_CODE, CRACT_PWD, CRACT_NAME, CRACT_STATUS, CRACT_NATURE, CRACT_CREATE_TIME, CRACT_UPDATE_TIME, CRACT_CREATOR, CRACT_ORDER, CRACT_PWD_EXCHG) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.get(1).prepareStatement(insertSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            DesHexPwdCoder desPwdEncoder = new DesHexPwdCoder();

            while (rs.next()) {
                preparedStatement.setInt(1, rs.getInt(1));
                preparedStatement.setString(2, rs.getString(2));
                preparedStatement.setString(3, rs.getString(3));
                preparedStatement.setString(4, rs.getString(4));
                preparedStatement.setString(5, rs.getString(5));
                preparedStatement.setString(6, rs.getString(6));
                preparedStatement.setString(7, rs.getString(7));
                preparedStatement.setString(8, DESUtil.encodeMD5Hex(rs.getString(8)));
                preparedStatement.setString(9, rs.getString(9));
                preparedStatement.setString(10, rs.getString(10));
                preparedStatement.setString(11, rs.getString(11));
                preparedStatement.setDate(12, rs.getDate(12));
                preparedStatement.setDate(13, rs.getDate(13));
                preparedStatement.setString(14, rs.getString(14));
                preparedStatement.setInt(15, rs.getInt(15));
                preparedStatement.setString(16, desPwdEncoder.encode(rs.getString(8)));
                preparedStatement.addBatch();
            }
            int[] ints = preparedStatement.executeBatch();
            conn.get(1).commit();
            conn.get(1).setAutoCommit(true);
            System.out.println(ints.length);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void testImportCoreOrganization() {
        try {
            String sql = "SELECT CRORG_UNID, CRORG_UUID, CRORG_LEVEL_CODE, CRORG_NUM, CRORG_PARENT_UUID, CRORG_SHORT_NAME, CRORG_FULL_NAME, CRORG_ADDRESS, CRORG_PHONE, CRORG_TYPE, CRORG_STATUS, CRORG_ORD, CRORG_CDATE, CRORG_UDATE, CRORG_CRACT_CREATER_UUID, CRORG_PINYIN, CRORG_OUTER_UUID, CRORG_NKC FROM CORE_ORGANIZATION";
            st = conn.get(0).prepareStatement(sql);
            rs = st.executeQuery();

            String insertSql = "INSERT INTO CORE_ORGANIZATION (CRORG_UNID, CRORG_UUID, CRORG_LEVEL_CODE, CRORG_NUMBER, CRORG_PARENT_UUID, CRORG_NAME, CRORG_FULL_NAME, CRORG_ADDRESS, CRORG_PHONE, CRORG_TYPE, CRORG_STATUS, CRORG_ORDER, CRORG_CREATE_TIME, CRORG_UPDATE_TIME, CRORG_CREATOR, CRORG_PINYIN, CRORG_OUTER_UUID, CRORG_NKC) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.get(1).prepareStatement(insertSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            while (rs.next()) {
                preparedStatement.setInt(1, rs.getInt(1));
                preparedStatement.setString(2, rs.getString(2));
                preparedStatement.setString(3, rs.getString(3));
                preparedStatement.setString(4, rs.getString(4));
                preparedStatement.setString(5, rs.getString(5));
                preparedStatement.setString(6, rs.getString(6));
                preparedStatement.setString(7, rs.getString(7));
                preparedStatement.setString(8, rs.getString(8));
                preparedStatement.setString(9, rs.getString(9));
                preparedStatement.setString(10, rs.getString(10));
                preparedStatement.setString(11, rs.getString(11));
                preparedStatement.setInt(12, rs.getInt(12));
                preparedStatement.setDate(13, rs.getDate(13));
                preparedStatement.setDate(14, rs.getDate(14));
                preparedStatement.setString(15, rs.getString(15));
                preparedStatement.setString(16, rs.getString(16));
                preparedStatement.setString(17, rs.getString(17));
                preparedStatement.setString(18, rs.getString(18));
                preparedStatement.addBatch();
            }
            int[] ints = preparedStatement.executeBatch();
            conn.get(1).commit();
            conn.get(1).setAutoCommit(true);
            System.out.println(ints.length);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void release() {
        JdbcUtils_DBCP.release(conn.get(0), st, rs);
    }

    public static void main(String[] args) throws SQLException {
        // DataSourceTest.testJCSelect();
        DataSourceTest.testImportCoreAccount();
        // DataSourceTest.testImportCoreOrganization();
    }

}
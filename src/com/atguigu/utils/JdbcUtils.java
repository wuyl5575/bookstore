package com.atguigu.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author HP
 */
public class JdbcUtils {

    private static DruidDataSource dataSource;
    private static ThreadLocal<Connection> conns = new ThreadLocal<Connection>();

    /**
     * ctrl + alt + T 出现 选择 添加抛出异常
     * ctrl + shift +上下键移动一行代码
     */
    static {
        Properties properties = new Properties();
        /**
         * InputStream得到这个流 读取jdbc.properties配置文件
         */
        InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
        try {
            properties.load(inputStream);
            /**
             * 创建数据库连接池
             */
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取数据库连接池中的连接
     * @return 如果返回null,说明获取连接失败
     */
    public static Connection getConnection() {
        Connection conn = conns.get();
        if (conn == null) {
            try {
                //数据库连接池获取连接
                conn = dataSource.getConnection();
                //保存到ThreadLocal对象中,供后面的jdbc操作使用
                conns.set(conn);
                //设置手动管理事务
                conn.setAutoCommit(false);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return conn;
    }


    /**
     * 提交事务，并关闭释放连接
     */
    public static void commitAndClose() {
        Connection connection = conns.get();
        // 如果不等于 null，说明 之前使用过连接，操作过数据库
        if (connection != null) {
            try {
                // 提交 事务
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    // 关闭连接，资源资源
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // 一定要执行 remove 操作,否则就会出错(因为 Tomcat 服务器底层使用了线程池技术)
        conns.remove();
    }


    /**
     * 回滚事务，并关闭释放连接
     */
    public static void rollbackAndClose() {
        Connection connection = conns.get();
        // 如果不等于 null，说明 之前使用过连接，操作过数据库
        if (connection != null) {
            try {
                //回滚事务
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    // 关闭连接，资源资源
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // 一定要执行 remove 操作,否则就会出错(因为 Tomcat 服务器底层使用了线程池技术)
        conns.remove();
    }






    /**
     * 关闭连接放回数据库连接池
     * @param conn

    public static void close(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    */
}

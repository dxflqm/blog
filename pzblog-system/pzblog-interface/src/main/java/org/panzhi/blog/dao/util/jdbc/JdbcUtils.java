package org.panzhi.blog.dao.util.jdbc;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.panzhi.blog.common.constant.SysConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSourceFactory;

/**
 * 数据连接池操作工具
 * @author panzhi
 *
 */
public class JdbcUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(JdbcUtils.class);
	
	/**jdbc配置文件*/
	private static Properties prop = new Properties(); 
    
	private static DataSource dataSource = null;
	// 它是事务专用连接！
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	
	static{
		//读取指定位置的配置文档(读取class目录文件)
    	classPathSourceRead();
    	//读取磁盘位置的文件	
    	readSourceFile();
    }

    private static void classPathSourceRead(){
    	//读取指定位置的配置文档(读取class目录文件)
    	try {
			prop.load(JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties"));
			logger.info("初始化默认jdbc配置文件成功!");
		} catch (Exception e) {
			logger.error("初始化默认jdbc文件失败!",e);
		}
    }
    
    private static void readSourceFile(){
    	BufferedInputStream inputStream = null; 
    	String proFilePath = SysConstant.SERVER_CONFIG_ADS + "/jdbc.properties";
        try {
            inputStream = new BufferedInputStream(new FileInputStream(proFilePath));  
            prop.load(new InputStreamReader(inputStream, "utf-8"));
            logger.info("初始化服务器配置文件:jdbc.properties,成功!");
        } catch (Exception e) {  
        	logger.error("初始化服务器配置文件失败,文件地址: "+ proFilePath);  
        } finally{
        	try {
        		if (inputStream != null) {
        			inputStream.close();
				}
			} catch (IOException e) {
				logger.error("初始化服务器配置文件失败,文件地址: "+ proFilePath);  
			}  
        }
    }
	
	/**
	 * 从连接池获取数据源
	 * @param sourceName 数据源的名称
	 * @return
	 * @throws Exception
	 */
	public static DataSource getDataSource() throws Exception {
		try {
			if (dataSource == null) {
				synchronized (JdbcUtils.class) {
					if (dataSource == null) {
						dataSource = DruidDataSourceFactory.createDataSource(prop); 
					}
				}
			}
			return dataSource;
		} catch (Exception e) {
			logger.error("根据数据库名称获取数据库资源失败，" , e);
			throw new Exception("根据数据库名称获取数据库资源失败");
		}
	}
	
	/**
	 * 使用连接池返回一个连接对象
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws Exception {
		try {
			Connection con = tl.get();
			// 当con不等于null，说明已经调用过beginTransaction()，表示开启了事务！
			if (con != null)
				return con;
			return getDataSource().getConnection();
		} catch (Exception e) {
			logger.error("获取数据库连接失败！", e);
			throw new SQLException("获取数据库连接失败！");
		}
	}
	
	/**
	 * 开启事务 1. 获取一个Connection，设置它的setAutoComnmit(false) 
	 * 2. 还要保证dao中使用的连接是我们刚刚创建的！ -------------- 
	 * 3. 创建一个Connection，设置为手动提交 
	 * 4. 把这个Connection给dao用！ 
	 * 5. 还要让commitTransaction或rollbackTransaction可以获取到！
	 * 
	 * @throws SQLException
	 */
	public static void beginTransaction() throws Exception {
		try {
			Connection con = tl.get();
			if (con != null) {
				con.close();
				tl.remove();
				//throw new SQLException("已经开启了事务，就不要重复开启了！");
			}
			con = getConnection();
			con.setAutoCommit(false);
			tl.set(con);
		} catch (Exception e) {
			logger.error("数据库事物开启失败！", e);
			throw new SQLException("数据库事物开启失败！");
		}
	}

	/**
	 * 提交事务 1. 获取beginTransaction提供的Connection，然后调用commit方法
	 * 
	 * @throws SQLException
	 */
	public static void commitTransaction() throws SQLException {
		Connection con = tl.get();
		try {
			if (con == null)
				throw new SQLException("还没有开启事务，不能提交！");
			con.commit();
		} catch (Exception e) {
			logger.error("数据库事物提交失败！", e);
			throw new SQLException("数据库事物提交失败！");
		} finally {
			if (con != null) {
				con.close();
			}
			tl.remove();
		}
	}
	
	/**
	 * 回滚事务 1. 获取beginTransaction提供的Connection，然后调用rollback方法
	 * 
	 * @throws SQLException
	 */
	public static void rollbackTransaction() throws SQLException {
		Connection con = tl.get();
		try {
			if (con == null)
				throw new SQLException("还没有开启事务，不能回滚！");
			con.rollback();
		} catch (Exception e) {
			logger.error("数据库事物回滚失败！", e);
			throw new SQLException("数据库事物回滚失败！");
		} finally {
			if (con != null) {
				con.close();
			}
			tl.remove();
		}
	}
	
	/**
	 * 释放连接　
	 * @param connection
	 * @throws SQLException
	 */
	public static void releaseConnection(Connection connection) throws SQLException {
		try {
			Connection con = tl.get();
			// 判断它是不是事务专用，如果是，就不关闭！ 如果不是事务专用，那么就要关闭！
			// 如果con == null，说明现在没有事务，那么connection一定不是事务专用的！
			//如果con != null，说明有事务，那么需要判断参数连接是否与con相等，若不等，说明参数连接不是事务专用连接
			if (con == null || con != connection)
				connection.close();
		} catch (Exception e) {
			logger.error("数据库连接释放失败！", e);
			throw new SQLException("数据库连接释放失败！");
		}
	}
}

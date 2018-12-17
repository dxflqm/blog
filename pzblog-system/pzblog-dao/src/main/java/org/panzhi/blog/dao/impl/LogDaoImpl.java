package org.panzhi.blog.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.panzhi.blog.common.annotation.Service;
import org.panzhi.blog.common.error.CommonErrorMsg;
import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.dao.LogDao;
import org.panzhi.blog.dao.entity.Log;
import org.panzhi.blog.dao.entity.Page;
import org.panzhi.blog.dao.util.jdbc.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

@Service
public class LogDaoImpl implements LogDao {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleDaoImpl.class);

	@Override
	public List<Log> findList(Page page) throws CommonException {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String param = page.getInput() == null ? "":page.getInput();
			String sql = "select log_id AS logId,visit_ip AS visitIp,ip_address AS ipAddress,path AS path, user_agent AS userAgent, device AS device,browser AS browser,create_time AS createTime from tb_log where ip_address like '" + param + "%' order by create_time DESC LIMIT ? , ?";
			List<Log> logList = runner.query(sql, new BeanListHandler<Log>(Log.class), page.getCurrentRow(),page.getPageSize());
			return logList;
		} catch (Exception e) {
			logger.error("查询日志信息数据失败!", e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}
	
	@Override
	public int count(Page page) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String param = page.getInput() == null ? "":page.getInput();
			String sql = "SELECT count(*) FROM tb_log where ip_address like '" + param + "%'";
			@SuppressWarnings({ "unchecked", "rawtypes" })
			Object obj = queryRunner.query(sql, new ScalarHandler(1));
			return ((Long) obj).intValue();
		}  catch (Exception e) {
			logger.error("查询日志信息总行数失败!", e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}


	@Override
	public int insert(Log lg) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "INSERT INTO `tb_log`(`log_id`, `visit_ip`, `ip_address`, `path`, `user_agent`,`device`,`browser`, `create_time`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			int result = queryRunner.update(sql, lg.getLogId(),lg.getVisitIp(),lg.getIpAddress(),lg.getPath(),lg.getUserAgent(),lg.getDevice(),lg.getBrowser(),new Timestamp(new Date().getTime()));
			if(result <= 0){
				logger.warn("新增日志数据到数据库失败!数据：" + JSON.toJSONString(lg));
			}
			return result;
		} catch (Exception e) {
			logger.error("新增日志数据到数据库失败!",e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

	
}

package org.panzhi.blog.dao.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.dbutils.QueryRunner;
import org.panzhi.blog.common.annotation.Service;
import org.panzhi.blog.common.error.CommonErrorMsg;
import org.panzhi.blog.common.exception.CommonException;
import org.panzhi.blog.dao.UserDao;
import org.panzhi.blog.dao.entity.User;
import org.panzhi.blog.dao.util.jdbc.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

@Service
public class UserDaoImpl implements UserDao {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleDaoImpl.class);

	@Override
	public int insert(User ur) throws CommonException {
		try {
			QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "INSERT INTO `tb_user`(`user_id`, `user_uuid`, `user_name`, `user_logo`, `create_time`, `update_time`, `delete_flag`) VALUES (?, ?, ?, ?, ?, ?, 0)";
			int result = queryRunner.update(sql,ur.getUserId(),ur.getUserUuid(),ur.getUserName(),ur.getUserLogo(),new Timestamp(new Date().getTime()),new Timestamp(new Date().getTime()));
			if(result <= 0){
				logger.warn("新增用户数据到数据库失败!数据：" + JSON.toJSONString(ur));
			}
			return result;
		} catch (Exception e) {
			logger.error("新增用户数据到数据库失败!",e);
			throw new CommonException(CommonErrorMsg.OPERATE_DB_ERROR);
		}
	}

}

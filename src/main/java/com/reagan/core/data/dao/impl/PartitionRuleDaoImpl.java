package com.reagan.core.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.reagan.core.data.dao.IPartitionRuleDao;
import com.reagan.core.entity.po.PartitionRule;
import com.reagan.core.exception.MapperException;
import com.reagan.core.util.ObjectMapperParams;


/**
 * <p>Description: </p>
 * @date 2013年11月29日
 * @author rr
 * @version 1.0
 * <p>Company:reagan</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Repository
public class PartitionRuleDaoImpl extends MapperDaoImpl<PartitionRule> implements IPartitionRuleDao {
	
	@Override
	public void createTableRule(String createSQL) {
		this.getBaseDao().execute(createSQL);
	}
	
	@Override
	public void updateTableRule(String updateSQL) {
		this.getBaseDao().execute(updateSQL);
	}
	
	
	class PartitionRuleMapper implements RowMapper<PartitionRule> {
		
		private ObjectMapperParams<PartitionRule> objectMapperParams = null;
		
		public PartitionRuleMapper(ObjectMapperParams<PartitionRule> objectMapperParams) {
			this.objectMapperParams = objectMapperParams;
		}

		@Override
		public PartitionRule mapRow(ResultSet rs, int row) throws SQLException {
			PartitionRule object = new PartitionRule();
			try {
				return objectMapperParams.resultObjectFactory(object, rs);
			} catch (MapperException e) {
				e.printStackTrace();
				return null;
			}
		}
		
	}
	
	@Override
	public RowMapper<PartitionRule> getRowMapper(
			ObjectMapperParams<PartitionRule> objectMapperParams) {
		return new PartitionRuleMapper(objectMapperParams);
	}
	
}

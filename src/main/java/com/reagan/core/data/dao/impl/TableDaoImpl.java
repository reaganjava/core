package com.reagan.core.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.reagan.core.data.dao.ITableDao;
import com.reagan.core.entity.po.Table;
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
public class TableDaoImpl extends MapperDaoImpl<Table> implements ITableDao {

	class TableMapper implements RowMapper<Table> {
		
		private ObjectMapperParams<Table> objectMapperParams = null;
		
		public TableMapper(ObjectMapperParams<Table> objectMapperParams) {
			this.objectMapperParams = objectMapperParams;
		}

		@Override
		public Table mapRow(ResultSet rs, int row) throws SQLException {
			Table object = new Table();
			try {
				return objectMapperParams.resultObjectFactory(object, rs);
			} catch (MapperException e) {
				e.printStackTrace();
				return null;
			}
		}
		
	}

	@Override
	public RowMapper<Table> getRowMapper(
			ObjectMapperParams<Table> objectMapperParams) {
		return new TableMapper(objectMapperParams);
	}
	
}

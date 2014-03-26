package com.reagan.core.data.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reagan.core.data.dao.IPartitionRuleDao;
import com.reagan.core.data.dao.ITableDao;
import com.reagan.core.data.service.IParititonTableService;
import com.reagan.core.entity.bo.ParititonTable;
import com.reagan.core.entity.po.PartitionRule;
import com.reagan.core.entity.po.Table;
import com.reagan.util.LoggerUtil;

/**
 * <p>Description: </p>
 * @date 2013年12月4日
 * @author RR
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Service("parititonTableService")
public class ParititonTableServiceImpl implements IParititonTableService {
	
	private LoggerUtil loggerUtil = new LoggerUtil(ParititonTableServiceImpl.class);
	
	@Autowired
	private IPartitionRuleDao partitionRuleDao;
	
	@Autowired
	private ITableDao tableDao;


}

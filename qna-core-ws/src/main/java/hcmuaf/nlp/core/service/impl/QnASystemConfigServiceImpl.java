package hcmuaf.nlp.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmuaf.nlp.core.dao.QnASystemConfigDao;
import hcmuaf.nlp.core.model.QnASystemConfig;
import hcmuaf.nlp.core.service.QnASystemConfigService;

@Service("qnASystemConfigService")
public class QnASystemConfigServiceImpl implements QnASystemConfigService {
	@Autowired
	private QnASystemConfigDao configDao;
	
	@Override
	public List<QnASystemConfig> getAllConfig() {
		return configDao.getAllConfig();
	}
	
	@Override
	public QnASystemConfig getConfigByName(String configName) {
		return configDao.getConfigByName(configName);
	}
	
	@Override
	public void updateConfig(QnASystemConfig config) {
		configDao.updateConfig(config);
	}
}

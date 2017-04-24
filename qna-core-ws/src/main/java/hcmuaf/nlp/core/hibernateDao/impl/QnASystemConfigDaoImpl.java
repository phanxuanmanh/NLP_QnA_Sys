package hcmuaf.nlp.core.hibernateDao.impl;

import hcmuaf.nlp.core.dao.QnASystemConfigDao;
import hcmuaf.nlp.core.model.QnASystemConfig;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class QnASystemConfigDaoImpl implements QnASystemConfigDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public QnASystemConfig getConfigByName(String configName) {
		Session session = sessionFactory.getCurrentSession();
		QnASystemConfig config = (QnASystemConfig) session.createCriteria(QnASystemConfig.class)
				.add(Restrictions.eq("configName", configName)).uniqueResult();
		return config;
	}
	
	@Override
	public <T> void updateConfig(String configName, T value) {
		Session session = sessionFactory.getCurrentSession();
		QnASystemConfig config = (QnASystemConfig) session.createCriteria(QnASystemConfig.class)
				.add(Restrictions.eq("configName", configName)).uniqueResult();
		if (config != null) {
			config.setConfigValue(value.toString());
		} else {
			config = new QnASystemConfig();
			config.setConfigName(configName);
			config.setConfigValue(value.toString());
		}
		session.saveOrUpdate(config);
	}
	
	@Override
	public Double getDoubleConfigValue(String configName) {
		try {
			return Double.valueOf(getConfigByName(configName).getConfigValue());
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<QnASystemConfig> getAllConfig() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<QnASystemConfig> configs = session.createCriteria(QnASystemConfig.class).list();
		return configs;
	}

	@Override
	public void updateConfig(QnASystemConfig config) {
		updateConfig(config.getConfigName(), config.getConfigValue());
		
	}
}

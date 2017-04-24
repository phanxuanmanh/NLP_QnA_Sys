/**
 * @author Manh Phan
 *
 * Edited date Mar 4, 2017
 */
package hcmuaf.nlp.core.dao;

import hcmuaf.nlp.core.model.QnASystemConfig;

import java.util.List;

public interface QnASystemConfigDao {
	
	/**
	 * Gets the configuration by name.
	 *
	 * @param configName the configuration name
	 * @return the configuration by name
	 */
	public QnASystemConfig getConfigByName(String configName);
	
	/**
	 * Update configuration.
	 *
	 * @param <T> the generic type
	 * @param configName the configuration name
	 * @param value the value
	 */
	public <T> void updateConfig(String configName, T value);
	
	/**
	 * Update configuration.
	 *
	 * @param config the configuration
	 */
	public void updateConfig(QnASystemConfig config);
	
	/**
	 * Gets the double configuration value.
	 *
	 * @param configName the configuration name
	 * @return the double configuration value
	 */
	public Double getDoubleConfigValue(String configName);
	
	/**
	 * Gets the all configuration.
	 *
	 * @return the all configuration
	 */
	public List<QnASystemConfig> getAllConfig();
}

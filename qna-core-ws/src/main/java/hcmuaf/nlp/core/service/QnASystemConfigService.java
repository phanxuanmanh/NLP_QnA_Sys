/**
 * @author Manh Phan
 *
 * Edited date Mar 4, 2017
 */
package hcmuaf.nlp.core.service;

import hcmuaf.nlp.core.model.QnASystemConfig;

import java.util.List;

/**
 * The Interface QnASystemConfigService.
 */
public interface QnASystemConfigService {
	
	/**
	 * Gets the all configuration.
	 *
	 * @return the all configuration
	 */
	public List<QnASystemConfig> getAllConfig();
	
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
	 * @param config the configuration
	 */
	public void updateConfig(QnASystemConfig config);
	
}

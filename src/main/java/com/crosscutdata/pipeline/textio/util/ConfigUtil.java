package com.crosscutdata.pipeline.textio.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.crosscutdata.pipeline.textio.model.config.Config;

public class ConfigUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(ConfigUtil.class);

	public static Config loadConfig(String configPath) {
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			//load config
			String[] configPathArray = configPath.split(":");
			LOG.info("loading config file from: " + configPathArray[0] + "|" + configPathArray[1] + "|" + configPathArray[2]);
			String configStr = StorageUtil.readFileAsStringFromGCS(configPathArray[0], //projectId
					configPathArray[1], //bucket
					configPathArray[2], //filepath
					"/tmp/config.json");
			Config config = mapper.readValue(configStr, Config.class);
			LOG.info("base config: " + mapper.writeValueAsString(config));
			return config;
		} catch (IOException e) {
			LOG.error(e.getMessage());
			return null;
		}
	}

}

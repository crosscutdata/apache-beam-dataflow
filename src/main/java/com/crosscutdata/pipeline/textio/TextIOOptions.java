package com.crosscutdata.pipeline.textio;

import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;

public interface TextIOOptions extends DataflowPipelineOptions {
	
	String getConfigPath();
	void setConfigPath(String configPath);

}

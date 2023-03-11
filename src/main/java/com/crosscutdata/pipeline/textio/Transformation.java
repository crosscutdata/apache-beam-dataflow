package com.crosscutdata.pipeline.textio;

import org.apache.beam.sdk.transforms.DoFn;

public class Transformation extends DoFn<String, String> {
	
	private static final long serialVersionUID = -5259879110421558885L;

	@ProcessElement
	public void processElement(ProcessContext c) {
		
		String line = c.element();
		line = line.toUpperCase();
		
		c.output(line);
		
	}

}

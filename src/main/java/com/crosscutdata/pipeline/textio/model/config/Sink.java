package com.crosscutdata.pipeline.textio.model.config;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sink implements Serializable {
	
	private static final long serialVersionUID = 9067491750901730060L;
	
	private String successfulOutputFilePath;
	private String failedOutputFilePath;

}

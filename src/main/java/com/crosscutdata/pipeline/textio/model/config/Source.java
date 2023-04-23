package com.crosscutdata.pipeline.textio.model.config;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Source implements Serializable {
	
	private static final long serialVersionUID = -4945694214701869978L;
	
	private String inputFilePath;

}

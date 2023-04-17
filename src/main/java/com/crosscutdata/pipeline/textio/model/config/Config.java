package com.crosscutdata.pipeline.textio.model.config;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Config implements Serializable {

	private static final long serialVersionUID = -3664384836560727005L;
	
	private Source source;
	private Sink sink;

}

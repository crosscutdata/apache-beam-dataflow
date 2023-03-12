package com.crosscutdata.pipeline.textio;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.TupleTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Transformation extends DoFn<String, String> {
	
	private static final long serialVersionUID = -5259879110421558885L;
	private static final Logger LOG = LoggerFactory.getLogger(Transformation.class);
	
	public static TupleTag<String> VALID_DATA_TAG = new TupleTag<String>();
	public static TupleTag<String> FAILURE_DATA_TAG = new TupleTag<String>();

	@ProcessElement
	public void processElement(ProcessContext c) {
		
		String row = c.element();
		LOG.info("row: " + row);

		String[] items = row.split(",");
		
		try {
			int sqft = Integer.parseInt(items[1].trim());
			int price = Integer.parseInt(items[6].trim());
			float pricePerSqft = price/sqft;
			
			String message = "Zip code " + items[4].trim() + " house is cost $" + String.valueOf(pricePerSqft) + " per sqft";

			LOG.info(message);
			c.output(VALID_DATA_TAG, message);
		} catch(Exception e) {
			String message = "Zip code " + items[4].trim() + " house\'s size is UNKNOWN";
			
			LOG.info(message);
			c.output(FAILURE_DATA_TAG, message);
		}
		
	}

}

package com.crosscutdata.pipeline.textio;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.apache.beam.sdk.values.PDone;
import org.apache.beam.sdk.values.TupleTag;
import org.apache.beam.sdk.values.TupleTagList;


public class TextIOPipeline {
	


	public static void main(String[] args) {

		TextIOOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(TextIOOptions.class);
		Pipeline p = Pipeline.create(options);

		
		// load zillow data from GCS
		PCollection<String> extractedData = p.apply("Extract", TextIO.read().from("gs://bucket-wissen/dataflow/zillow.csv"));
		
		// execute Transform to calculate price per sqft for houses
		PCollectionTuple transformedTuple = extractedData.apply("Transform", ParDo.of(new Transformation())
				.withOutputTags(Transformation.VALID_DATA_TAG, TupleTagList.of(Transformation.FAILURE_DATA_TAG)));
		
		//save data for known sized house
		transformedTuple.get(Transformation.VALID_DATA_TAG)
				.setCoder(StringUtf8Coder.of())
				.apply("Save Result", TextIO.write().to("gs://bucket-wissen/dataflow/zillow-result.txt"));
		
		//handle exception for unknown sized house
		transformedTuple.get(Transformation.FAILURE_DATA_TAG)
				.setCoder(StringUtf8Coder.of())
				.apply("Unknown Sqft Data", TextIO.write().to("gs://bucket-wissen/dataflow/zillow-unknown.txt"));
		
		
		PipelineResult result = p.run();
		try {
			result.getState();
			result.waitUntilFinish();
		} catch (UnsupportedOperationException e) {
			// do nothing
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

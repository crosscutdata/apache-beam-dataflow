package com.crosscutdata.pipeline.textio;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.apache.beam.sdk.values.TupleTagList;

import com.crosscutdata.pipeline.textio.model.config.Config;
import com.crosscutdata.pipeline.textio.util.ConfigUtil;


public class TextIOPipeline {

	public static void main(String[] args) {

		TextIOOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(TextIOOptions.class);
		Pipeline p = Pipeline.create(options);
		
		Config config = ConfigUtil.loadConfig(options.getConfigPath());

		
		// load zillow data from GCS
		PCollection<String> extractedData = p.apply("Extract", TextIO.read().from(config.getSource().getInputFilePath()));
		
		// execute Transform to calculate price per sqft for houses
		PCollectionTuple transformedTuple = extractedData.apply("Transform", ParDo.of(new Transformation())
				.withOutputTags(Transformation.VALID_DATA_TAG, TupleTagList.of(Transformation.FAILURE_DATA_TAG)));
		
		//save data for known sized house
		transformedTuple.get(Transformation.VALID_DATA_TAG)
				.setCoder(StringUtf8Coder.of())
				.apply("Save Result", TextIO.write().to(config.getSink().getSuccessfulOutputFilePath()));
		
		//handle exception for unknown sized house
		transformedTuple.get(Transformation.FAILURE_DATA_TAG)
				.setCoder(StringUtf8Coder.of())
				.apply("Unknown Sqft Data", TextIO.write().to(config.getSink().getFailedOutputFilePath()));
		
		
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

package com.crosscutdata.pipeline.textio;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PDone;


public class TextIOPipeline {

	public static void main(String[] args) {

		TextIOOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(TextIOOptions.class);
		Pipeline p = Pipeline.create(options);

		
		// This example reads from a public dataset containing the text of King Lear.
		PCollection<String> extractedData = p.apply("Extract", TextIO.read().from("gs://apache-beam-samples/shakespeare/kinglear.txt"));
		
		PCollection<String> transformedData = extractedData.apply("Transform", ParDo.of(new Transformation()));
		
		PDone done = transformedData.apply("Load", TextIO.write().to("gs://bucket-wissen/dataflow/transformed-output.txt"));

		
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

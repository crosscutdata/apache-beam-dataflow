package com.crosscutdata.streaming;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PDone;


public class StreamingPipeline {

	public static void main(String[] args) {

		Options options = PipelineOptionsFactory.fromArgs(args).withValidation().as(Options.class);
		Pipeline p = Pipeline.create(options);

		// This example reads from a public dataset containing the text of King Lear.
		PCollection<String> input = p.apply("Read", TextIO.read().from("gs://apache-beam-samples/shakespeare/kinglear.txt"));
		PDone done = input.apply("Write", TextIO.write().to("gs://crosscutdata-bucket/dataflow/output.txt"));

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

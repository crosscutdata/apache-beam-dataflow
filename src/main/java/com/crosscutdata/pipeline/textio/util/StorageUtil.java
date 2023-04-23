package com.crosscutdata.pipeline.textio.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class StorageUtil {

	private static final Logger LOG = LoggerFactory.getLogger(StorageUtil.class);

	public static String readFileAsStringFromGCS(String projectId, String bucket, String filePath, String tempPath) {
		try {
			LOG.info(
					"projectId:" + projectId +
					" - bucket:" + bucket +
					" - filePath:" + filePath +
					" - tempPath:" + tempPath);

			Storage storage = StorageOptions.newBuilder()
					.setProjectId(projectId)
					.setCredentials(GoogleCredentials.getApplicationDefault())
					.build()
					.getService();
			Blob blob = storage.get(bucket, filePath);
			LOG.info("StorageUtil.getFileFromGCS: Blob to String");
			LOG.info("StorageUtil.getFileFromGCS: Blob size" + blob.getSize());
			return new String(blob.getContent());
		} catch (IOException e) {
			LOG.error("IOException occured in getFileFromGCS");
			LOG.error(e.getMessage());
			return null;
		}
	}

}

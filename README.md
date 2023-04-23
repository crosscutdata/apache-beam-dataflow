# Apache Beam Pipeline in GCP Dataflow
Build ETL pipeline using Apache Beam and run in Dataflow in GCP

## Who is this Tutorial Series for
If you are a Java developer who wants to build an ETL pipeline using Apache Beam and want to deploy it in Dataflow service in the Google Cloud Platform, follow us at https://www.youtube.com/@crosscutdata to build and deploy your first ETL pipeline with a lean codebase

## Prerequisites
- This setup is done in a Windoes 10 machine, please figure out the environment setup by yourself if you are using any other OS. for any other.
- You need to have following softwares installed in your machines
  - Java/JDK (version at least 11)
  - Maven  (version 3.6.3 or later)
  - IDE (Eclipse)
  - GitHub Desktop
  - Gcloud CLI (https://cloud.google.com/sdk/docs/install)
- As a first step you need to create a gmail account
- Use that gmail account to create a Google cloud account (https://console.cloud.google.com/)
- Activate the free trial to get the free $300 USD credit that you can use for next 3 months. You will need a credit card to activate the free trial.
- Activate the following services in GCP:
  - Artifact Registry (Create a Docker Registry)
  - Cloud build
  - Storage (Create a bucket to store template)
  - Pubsub
  - Bigquery
  - Dataflow
  - Dataflow API
- Configure Gcloud CLI by opening a command prompt and use the following command ```gcloud init```

## Build and Deploy
- Clone this repository (for better understanding the code, use the ```feature``` branch rather than ```master``` branch as the master branch will always content the latest code)
- Open a command prompt and cd (change directory) to the root folder of the cloned repository
- Build the jar using command 
```
mvn clean install
```
- Build and push the template in Artifact Registry of GCP using command 
```
gcloud dataflow flex-template build gs://<BUCKET_PATH_WITH_FOLDER>/dataflow-template.json --image-gcr-path="<ARTIFACT_REGISTRY_PATH>/dataflow:latest" --sdk-language=JAVA --flex-template-base-image=JAVA11 --jar="<FULL_PATH_OF_YOUR_CLONED_FOLDER>\target\pipeline-<VERSION>.jar" --env=FLEX_TEMPLATE_JAVA_MAIN_CLASS="com.crosscutdata.pipeline.textio.TextIOPipeline"
```
- Deploy the template in Dataflow of GCP using command 
```
gcloud dataflow flex-template run "data-pipeline" --template-file-gcs-location="gs://<BUCKET_PATH_WITH_FOLDER>/dataflow-template.json" --parameters=configPath=crosscutdata-gcp:crosscutdata-bucket:configs/config_1.3.json
```
- Go to GCP Dataflow console to check

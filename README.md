# Data sets
## Data set links (for real)
Chicago Crime Data set: [https://data.cityofchicago.org/Public-Safety/Crimes-2022/9hwr-2zxp](https://data.cityofchicago.org/Public-Safety/Crimes-2022/9hwr-2zxp "https://data.cityofchicago.org/Public-Safety/Crimes-2022/9hwr-2zxp")

NYC Data set: [https://data.cityofnewyork.us/Transportation/2016-Green-Taxi-Trip-Data/hvrh-b6nb](https://data.cityofnewyork.us/Transportation/2016-Green-Taxi-Trip-Data/hvrh-b6nb "https://data.cityofnewyork.us/Transportation/2016-Green-Taxi-Trip-Data/hvrh-b6nb")
## Data set generation (for synthetic)
Run: `dataset_test_important.GenerateDatasetTest$generateZipf` to generate the zipf data set.

Run: `src/main/resources/matlabcode/generateWriteTwoDimNormPoint.m` to generate the normal data set.

Run: `src/main/resources/matlabcode/generateWriteTwoDimNormPointWithMultiCenter.m` to generate the multi-center normal data set.
 
## Data set preprocess

[//]: # (Run:`dataset_test_important.DatasetMessageTest$preHandleData` to extract the Chicago Crime data set and the NYC data set.)
Run: `ecnu.dll.construction.dataset.Preprocess` to to extract the Chicago Crime data set and the NYC data set.

Run: `dataset_test_important.ExtractDatasetTest$extractNormDataset` to extract the normal data.

Run: `dataset_test_important.ExtractDatasetTest$extractNormalDatasetWithMultipleCenters` to extract the multi-center normal data.

# Run Process (version_1)
Set the value of `depoyment/config/parameter_config.xml/xpath{root/properites/using}` as `version_1`

## For Wasserstein Distance 
Run: `ecnu.dll.construction.run._1_total_run.main_repeat_process.version_1.RepeatMainMainRun$main` to get the basic result of all mechanisms' Wasserstein Distances.

Run: `ecnu.dll.construction.run._1_total_run.main_repeat_process.version_1.RepeatMainExtendedRun$main` to get the further result of Subset-GeoI and RAM 's Wasserstein Distances.

[//]: # (## For KL Divergence )
[//]: # (Run: `src/main/java/ecnu/dll/construction/run/basic_preprocess/SubsetGeoIEpsilonLPTableGeneration.java` to generate LP table for SEM-Geo-I.)
[//]: # ()
[//]: # (Run: `src/main/java/ecnu/dll/construction/run/basic_preprocess/DAMEpsilonLPTableGeneration.java` to generate LP table for DAM.)
[//]: # ()
[//]: # (Run: `src/main/java/ecnu/dll/construction/run/main_repeat_process/RepeatExtendedExtendedForKLDivergenceOnlyRun.java` to get the result under KL divergence.)


## Combine
Run `ecnu.dll.construction.run._0_base_run.basic_postprocess.CombineSubDatasetResult$main` to combine the result in different parts and different times to get the final result.


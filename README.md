# Data sets
## Data set links (for real)
Chicago Crime Data set: [https://data.cityofchicago.org/Public-Safety/Crimes-2022/9hwr-2zxp](https://data.cityofchicago.org/Public-Safety/Crimes-2022/9hwr-2zxp "https://data.cityofchicago.org/Public-Safety/Crimes-2022/9hwr-2zxp")

NYC Data set: [https://data.cityofnewyork.us/Transportation/2016-Green-Taxi-Trip-Data/hvrh-b6nb](https://data.cityofnewyork.us/Transportation/2016-Green-Taxi-Trip-Data/hvrh-b6nb "https://data.cityofnewyork.us/Transportation/2016-Green-Taxi-Trip-Data/hvrh-b6nb")
## Data set generation (for synthetic)
Run: `src/test/java/dataset_test_important/GenerateDatasetTest.java$generateZipf()` to generate the zipf data set.

Run: `src/main/resources/matlabcode/generateWriteTwoDimNormPoint.m` to generate the normal data set.

Run: `src/main/resources/matlabcode/generateWriteTwoDimNormPointWithMultiCenter.m` to generate the multi-center normal data set.
 
## Data set preprocess
Run:`src/test/java/dataset_test_important/DatasetMessageTest.java$preHandleData()` to extract the Chicago Crime data set and the NYC data set.

Run: `src/test/java/dataset_test_important/ExtractDatasetTest.java$extractNormDataset()` to extract the normal data.

Run: `src/test/java/dataset_test_important/ExtractDatasetTest.java$extractNormalDatasetWithMultipleCenters()` to extract the multi-center normal data.

# Run Process
## For Wasserstein Distance
Run: `src/main/java/ecnu/dll/construction/run/main_repeat_process/RepeatMainExtendedRun.java` to get the result under Wasserstein Distance.

## For KL Divergence 
Run: `src/main/java/ecnu/dll/construction/run/basic_preprocess/SubsetGeoIEpsilonLPTableGeneration.java` to generate LP table for SEM-Geo-I.

Run: `src/main/java/ecnu/dll/construction/run/basic_preprocess/DAMEpsilonLPTableGeneration.java` to generate LP table for DAM.

Run: `src/main/java/ecnu/dll/construction/run/main_repeat_process/RepeatExtendedExtendedForKLDivergenceOnlyRun.java` to get the result under KL divergence.


## Combine
Run `src/main/java/ecnu/dll/construction/run/basic_postprocess/CombineSubDatasetResult.java` to combine the result in different parts and different times to get the final result.


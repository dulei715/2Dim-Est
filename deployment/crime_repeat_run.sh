#!/bin/bash
class_name="Crime"
for i in {1..10}; do
  java -cp 2Dim-Est-1.01.01-jar-with-dependencies.jar ecnu.dll.construction.run._1_total_run.main_repeat_process.version_2.total_run.${class_name}Run ${i}
done

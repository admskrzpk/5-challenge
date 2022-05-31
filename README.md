# Scala Academy Exam - Bugfix

Run the Reader main class which reads the parquet file containing some data. It should print to stdout sample of the resulting dataset which shows grouped data about persons with an aggregated number of their emails, but nothing is printed while the application is run. Identify the bug, describe what is wrong and try to find the solution to fix it. The result of this task should be fixed code with the brief description of the bug and explained solution. Describe your thought process step after step. The description can be committed either as a separate plain text file or inserted as a comment in the committed source code.


## What I have discovered
* App is printing stdoutput running from IDE on Windows

```text
+----------+------+--------+-----------+
|  position|  name| surname|email_total|
+----------+------+--------+-----------+
|  Producer|  John|    Knut|          3|
|  Governor| Steve|Einstein|          8|
| Caretaker| Homer|Strassen|          4|
|   Builder|  Adam| Wozniak|          2|
|Politician| Steve| Feynman|          3|
|      NEET|  John| Feynman|          6|
|   Painter|Donald| Simpson|          3|
+----------+------+--------+-----------+

```
* App submitted by ``./bin/spark-submit`` on Ubuntu WSL works as well
````text
  +----------+------+--------+-----------+
  |  position|  name| surname|email_total|
  +----------+------+--------+-----------+
  |  Producer|  John|    Knut|          3|
  |  Governor| Steve|Einstein|          8|
  | Caretaker| Homer|Strassen|          4|
  |   Builder|  Adam| Wozniak|          2|
  |Politician| Steve| Feynman|          3|
  |      NEET|  John| Feynman|          6|
  |   Painter|Donald| Simpson|          3|
  +----------+------+--------+-----------+
````
* Simlarly when ``sbt run`` executed on Ubuntu WSL, app is printing result:
````text
+----------+------+--------+-----------+
|  position|  name| surname|email_total|
+----------+------+--------+-----------+
|  Producer|  John|    Knut|          3|
|  Governor| Steve|Einstein|          8|
| Caretaker| Homer|Strassen|          4|
|   Builder|  Adam| Wozniak|          2|
|Politician| Steve| Feynman|          3|
|      NEET|  John| Feynman|          6|
|   Painter|Donald| Simpson|          3|
````

* If app is launched with ```sbt run```  command on Windows is stuck when reading a file without throwing any error.

#### End of stacktrace

```text
22/05/31 13:37:23 INFO DAGScheduler: Final stage: ShuffleMapStage 1 (show at Reader.scala:24)
22/05/31 13:37:23 INFO DAGScheduler: Parents of final stage: List()
22/05/31 13:37:23 INFO DAGScheduler: Missing parents: List()
22/05/31 13:37:23 INFO DAGScheduler: Submitting ShuffleMapStage 1 (MapPartitionsRDD[5] at show at Reader.scala:24), which has no missing parents
22/05/31 13:37:23 INFO MemoryStore: Block broadcast_2 stored as values in memory (estimated size 43.9 KiB, free 391.1 MiB)
22/05/31 13:37:23 INFO MemoryStore: Block broadcast_2_piece0 stored as bytes in memory (estimated size 19.0 KiB, free 391.1 MiB)
22/05/31 13:37:23 INFO BlockManagerInfo: Added broadcast_2_piece0 in memory on host.docker.internal:60999 (size: 19.0 KiB, free: 391.4 MiB)
22/05/31 13:37:23 INFO SparkContext: Created broadcast 2 from broadcast at DAGScheduler.scala:1478
22/05/31 13:37:23 INFO DAGScheduler: Submitting 1 missing tasks from ShuffleMapStage 1 (MapPartitionsRDD[5] at show at Reader.scala:24) (first 15 tasks are for partitions Vector(0))
22/05/31 13:37:23 INFO TaskSchedulerImpl: Adding task set 1.0 with 1 tasks resource profile 0
22/05/31 13:37:23 INFO TaskSetManager: Starting task 0.0 in stage 1.0 (TID 1) (host.docker.internal, executor driver, partition 0, PROCESS_LOCAL, 4924 bytes) taskResourceAssignments Map()
22/05/31 13:37:23 INFO Executor: Running task 0.0 in stage 1.0 (TID 1)
22/05/31 13:37:23 INFO CodeGenerator: Code generated in 25.419 ms
22/05/31 13:37:23 INFO CodeGenerator: Code generated in 9.732 ms
22/05/31 13:37:23 INFO CodeGenerator: Code generated in 18.0872 ms
22/05/31 13:37:24 INFO CodeGenerator: Code generated in 20.576 ms
22/05/31 13:37:24 INFO FileScanRDD: Reading File path: file:///C:/exam/scala-academy-task/dataset/part-00000-54873ab5-8587-4a86-8570-0bdbe65ad157-c000.snappy.parquet, range: 0-218407, partition values: [empty row]
22/05/31 13:37:24 INFO FilterCompat: Filtering using predicate: noteq(id, null)
22/05/31 13:37:24 INFO CodecPool: Got brand-new decompressor [.snappy]
22/05/31 13:37:27 WARN ProcfsMetricsGetter: Exception when trying to compute pagesize, as a result reporting of ProcessTree metrics is stopped
```

## Final thoughts

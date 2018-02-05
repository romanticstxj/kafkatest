package main;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import scala.Tuple2;

public class SparkStreamKafkaMain {

	public static void main(String[] args) throws InterruptedException {
		// Create a local StreamingContext with two working thread and batch interval of 1 second
		SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount");
		conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");  
		conf.set("spark.kryo.registrator", "main.MyRegistrator");  
		JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(10));
		
		Map<String, Object> kafkaParams = new HashMap<>();
		kafkaParams.put("bootstrap.servers", "172.16.25.27:9092,172.16.25.28:9092,172.16.25.29:9092");
		kafkaParams.put("key.deserializer", StringDeserializer.class);
		kafkaParams.put("value.deserializer", StringDeserializer.class);
		kafkaParams.put("group.id", "mygroup1");
		kafkaParams.put("auto.offset.reset", "earliest");
		kafkaParams.put("enable.auto.commit", false);
		
		Collection<String> topics = Arrays.asList("topic_test");
		JavaInputDStream<ConsumerRecord<String, String>> stream =
		  KafkaUtils.createDirectStream(
				  jssc,
		    LocationStrategies.PreferConsistent(),
		    ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams)
		  );
		stream.mapToPair(record -> new Tuple2<>(record.key(), record.value()));
//		stream.print();
		// 6.spark rdd转化和行动处理  
//        stream.foreachRDD( (v1,v2) -> {
//        	List<ConsumerRecord<String, String>> consumerRecords = v1.collect();  
//            consumerRecords.forEach(System.out::println);
//        });
		
		stream.foreachRDD((rdd,time) -> {
			// Get the singleton instance of SparkSession
			  SparkSession spark = SparkSession.builder().config(rdd.context().getConf()).getOrCreate();

			  // Convert RDD[String] to RDD[case class] to DataFrame
			  JavaRDD<JavaRow> rowRDD = rdd.map(word -> {
			    JavaRow record = new JavaRow();
			    record.setWord(word.value());
			    return record;
			  });
			  Dataset<Row> wordsDataFrame = spark.createDataFrame(rowRDD, JavaRow.class);

			  // Creates a temporary view using the DataFrame
			  wordsDataFrame.createOrReplaceTempView("words");

			  // Do word count on table using SQL and print it
			  Dataset<Row> wordCountsDataFrame =
			    spark.sql("select word, count(*) as total from words group by word");
			  wordCountsDataFrame.show();
		});
		
//		stream.foreachRDD(rdd -> {
//			  OffsetRange[] offsetRanges = ((HasOffsetRanges) rdd.rdd()).offsetRanges();
//			  rdd.foreachPartition(consumerRecords -> {
//			    OffsetRange o = offsetRanges[TaskContext.get().partitionId()];
//			    System.out.println(
//			      o.topic() + " " + o.partition() + " " + o.fromOffset() + " " + o.untilOffset());
//			  });
//			});
        
        
        
     // Import dependencies and create kafka params as in Create Direct Stream above

//        OffsetRange[] offsetRanges = {
//          // topic, partition, inclusive starting offset, exclusive ending offset
//          OffsetRange.create("topic_test", 0, 253, 293)
//        };
//
//        JavaRDD<ConsumerRecord<String, String>> rdd = KafkaUtils.createRDD(
//        	  jssc.sparkContext(),
//          kafkaParams,
//          offsetRanges,
//          LocationStrategies.PreferConsistent()
//        );
//        
//        rdd.foreach(c -> {
//        	System.out.println(c.offset());
//        	System.out.println(c.key());
//        	System.out.println(c.value());
//        });
		
		jssc.start();              // Start the computation
		jssc.awaitTermination();   // Wait for the computation to terminate
	}

}


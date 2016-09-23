package com.cadre.kafka;

import com.cadre.dao.LocationDAO;
import com.cadre.entities.LocationInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.skife.jdbi.v2.DBI;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by sameer.sanagala on 10/09/16.
 */
@Slf4j
public class DataFetcher {

  private final ConsumerConnector consumer;
  private final String topic;
  private LocationDAO locationDAO;

  public DataFetcher(){
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("zookeeper.connect", "localhost:2181");
    props.put("group.id", "test");
    props.put("enable.auto.commit", "false");
    props.put("auto.commit.interval.ms", "1000");
    props.put("session.timeout.ms", "30000");
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
    this.topic = "test";
    locationDAO = createDBI().onDemand(LocationDAO.class);

  }

  public DBI createDBI(){

    System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("Driver not found");
    }
    String url = "jdbc:mysql://127.0.0.1:3306/cadredb";
    String user = "root";
    String password = "";
    DBI jdbi = new DBI(url, user, password);
    return jdbi;

  }

  public void loadData(){
    //TODO fetch from kafka directly using offset instead persiting into db
    Map<String, Integer> topicCount = new HashMap<>();
    topicCount.put(topic, 1);

    Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams = consumer.createMessageStreams(topicCount);
    List<KafkaStream<byte[], byte[]>> streams = consumerStreams.get(topic);
    for (final KafkaStream stream : streams) {
      ConsumerIterator<byte[], byte[]> it = stream.iterator();
      while (it.hasNext()) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
          LocationInfo locationInfo = objectMapper.readValue(it.next().message(), LocationInfo.class);
          locationDAO.insertInfo(locationInfo);
        } catch (IOException e) {
          log.error("Failed to deserialize message" + e);
        }

      }
    }
    if (consumer != null) {
      consumer.shutdown();
    }

  }

  public static void main(String[] args) {
    DataFetcher dataFetcher = new DataFetcher();
    dataFetcher.loadData();
  }
}

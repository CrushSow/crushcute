package com.xuzhoutechnologyuniversity.crush.flinkWorldCount;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * PACKAGE_NAMW   com.xuzhoutechnologyuniversity.crush.flinkWorldCount
 * DATE      04
 * Author     Crush
 */
public class WorldCount {

    public static void main(String[] args) {
        int port =0;
        try {
            ParameterTool parameterTool=ParameterTool.fromArgs(args);
            parameterTool.getInt("port");
        }catch (Exception e){
            System.out.println("port no set,user define port 9999");
            port=9999;
        }
        
        // 1 先获取flink的程序的入口 
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        String hostname = "106.15.191.63";
        String delimiter="\n";
        DataStreamSource<String> text = env.socketTextStream(hostname, port, delimiter);

        //对数据进行计算 

        SingleOutputStreamOperator<WorldAndCount> res = text.flatMap(new FlatMapFunction<String, WorldAndCount>() {
            @Override
            public void flatMap(String value, Collector<WorldAndCount> out) throws Exception {
                String[] splits = value.split(" ");
                for (String word : splits) {
                    out.collect(new WorldAndCount(word, 1));
                }
            }
        }).keyBy("word")
                .timeWindow(Time.seconds(2), Time.seconds(1))
                .sum("count");
        res.print();


        try {
            env.execute("worldandcount");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static class WorldAndCount{
        public String word;
        public int count;

        public WorldAndCount() {
        }

        public WorldAndCount(String word, int count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public String toString() {
            return "WorldAndCount{" +
                    "word='" + word + '\'' +
                    ", count=" + count +
                    '}';
        }
    }
}

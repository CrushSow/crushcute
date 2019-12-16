package jedis.crush;

import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

/**
 * PACKAGE_NAMW   jedis.crush
 * DATE      22
 * Author     Crush
 */
public class OperateStateMain {
    public static void main(String[] args) {
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        executionEnvironment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        executionEnvironment.enableCheckpointing(60000);
        CheckpointConfig checkpointConfig = executionEnvironment.getCheckpointConfig();

        checkpointConfig.setMinPauseBetweenCheckpoints(30000L);
        checkpointConfig.setCheckpointTimeout(10000L);
        checkpointConfig.enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);

        executionEnvironment.fromElements(1L,2L,4L,1L,4L,7L,9L,8L,1L,3L,5L,10L,7L,1L)
                .flatMap(new CountWithOperateState())
                .addSink(new SinkFunction<String>() {
                    @Override
                    public int hashCode() {
                        return super.hashCode();
                    }
                });


    }
}

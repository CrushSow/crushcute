package jedis.crush.func;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * PACKAGE_NAMW   jedis.crush.func
 * DATE      27
 * Author     Crush
 * <p>
 * 并行度必须是 1
 */
public class Datasource implements SourceFunction {
    // count
    private long count = 1L;

    // 定义一个终止条件
    private boolean isRunning = true;

    @Override
    public void run(SourceContext ctx) throws Exception {
        while (isRunning) {
            ctx.collect(count);
            count++;
            Thread.sleep(1000);
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }


}
class MydataSource{
    public static void main(String[] args) {
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource data = executionEnvironment.addSource((SourceFunction<Object>) new MydataSource());


        SingleOutputStreamOperator source = data.map(new MapFunction<Long, Long>() {
            @Override
            public Long map(Long value) throws Exception {
                System.out.println("输出" + value);
                return value;
            }
        });

        source.timeWindowAll(Time.seconds(2));
    }
}
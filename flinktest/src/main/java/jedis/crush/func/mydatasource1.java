package jedis.crush.func;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction;

/**
 * PACKAGE_NAMW   jedis.crush.func
 * DATE      27
 * Author     Crush
 *实现这个接口 可以去多个并行度
 */
public class mydatasource1 implements ParallelSourceFunction {
    private Long count =1L;
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
class Mydatasource11 {
    public static void main(String[] args) {
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource data = executionEnvironment.addSource(new mydatasource1()).setParallelism(1);


    }
}

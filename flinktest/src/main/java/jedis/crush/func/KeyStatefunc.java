package jedis.crush.func;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;

import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;


/**
 * PACKAGE_NAMW   jedis.crush.func
 * DATE      22
 * Author     Crush
 */


// keystate 的状态是存在 本地的 而operator state 是存在内存中的
public class KeyStatefunc extends RichFlatMapFunction<Tuple2<Long, Long>, Tuple2<Long, Long>> {
    private transient ValueState<Tuple2<Long, Long>> sum;

    public void flatMap(Tuple2<Long, Long> input, Collector<Tuple2<Long, Long>> out) throws Exception {
        Tuple2<Long, Long> curentSum = sum.value();

        curentSum.f0 += 1;

        curentSum.f1 += input.f1;

        sum.update(curentSum);

        if (curentSum.f0 > 3) {
            out.collect(new Tuple2<Long, Long>(input.f0, curentSum.f1 / curentSum.f0));
            sum.clear();
        }


    }

    @Override
    public void open(Configuration parameters) throws Exception {
        ValueStateDescriptor<Tuple2<Long, Long>> descriptor = new ValueStateDescriptor<Tuple2<Long, Long>>("average", TypeInformation.of(new TypeHint<Tuple2<Long, Long>>() {
        }));

        sum = getRuntimeContext().getState(descriptor);
    }
}

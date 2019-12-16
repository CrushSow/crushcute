package jedis.crush;


import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.util.Collector;

import java.util.List;


/**
 * PACKAGE_NAMW   jedis.crush
 * DATE      20
 * Author     Crush
 *
 * 想去实现operator state  是必须实现CheckpointedFunction
 */
public class CountWithOperateState extends RichFlatMapFunction<Long, String> implements CheckpointedFunction {

    /*
     * 保存结果的状态
     * */

    private transient ListState<Long> checkpointlist;
    private List<Long> ListBufferElements;

    public void flatMap(Long value, Collector<String> out) throws Exception {
        if (value == 1) {
            if (ListBufferElements.size() > 0) {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < ListBufferElements.size(); i++) {
                    stringBuffer.append(ListBufferElements.get(i) + " ");
                }
            }
        } else {
            ListBufferElements.add(value);
        }
    }

    public void snapshotState(FunctionSnapshotContext context) throws Exception {
        checkpointlist.clear();
        for (int i = 0; i < ListBufferElements.size(); i++) {
            checkpointlist.add(ListBufferElements.get(i));
        }
    }


    public void initializeState(FunctionInitializationContext context) throws Exception {

        ListStateDescriptor<Long> listStateDescriptor =
                new ListStateDescriptor("listforname", TypeInformation.of(new TypeHint() {
                    @Override
                    public TypeInformation getTypeInfo() {
                        return super.getTypeInfo();
                    }
                }));

        checkpointlist = context.getOperatorStateStore().getListState(listStateDescriptor);
        if (context.isRestored()) {
            for (Long element : checkpointlist.get()) {
                ListBufferElements.add(element);
            }
        }

    }
}

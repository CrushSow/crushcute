package jedis.crush.func;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.table.api.Table;

import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * PACKAGE_NAMW   jedis.crush.func
 * DATE      29
 * Author     Crush
 */
public class flinkTab {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

        BatchTableEnvironment tableEnvironment = TableEnvironment.getTableEnvironment(executionEnvironment);

        List list = new ArrayList<String>();

        //数据源
        String data = "xuzhou xuzhou lele lele lele yingzi yingzi gazige gazige";

        String[] split = data.split(" ");

        for (String s : split) {
            World world = new World(s, 1);
            list.add(world);
        }


        DataSet<World> dataSet = executionEnvironment.fromCollection(list);


        tableEnvironment.registerDataSet("wcount", dataSet, "name,cout");

        Table table = tableEnvironment.sqlQuery("select name,sum(cout) as cout from wcount group by name");

        DataSet<World> worldDataSet = tableEnvironment.toDataSet(table, World.class);
        worldDataSet.print();
    }

    public static class World {
        public String name;
        public int cout;
        public World(){}

        public World(String name, int count) {
            this.name = name;
            this.cout = count;
        }

        @Override
        public String toString() {
            return "world{" +
                    "name='" + name + '\'' +
                    ", count=" + cout +
                    '}';
        }
    }
}

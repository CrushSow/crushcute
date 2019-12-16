package jedis.crush;


import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.MapOperator;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.BatchTableEnvironment;

/**
 * PACKAGE_NAMW   jedis.crush
 * DATE      08
 * Author     Crush
 */
public class football {
    public static void main(String[] args) {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        //获取BatchTableEnvironment
        BatchTableEnvironment tableEnv = BatchTableEnvironment.getTableEnvironment(env);
        env.setParallelism(1);
        //获取数据源
        DataSource<String> data = env.readTextFile("D:\\java\\flinktest\\src\\football.txt");
        // data.print();
        DataSet<Player> source = data.map(new MapFunction<String, Player>() {
            @Override
            public Player map(String value) throws Exception {
                String[] splits = value.split(" ");
                return new Player(Integer.valueOf(splits[0]), String.valueOf(splits[1]),
                        String.valueOf(splits[2]), Integer.valueOf(splits[3]));
            }
        });
        //将DataSet转换成一张内存表
        Table player = tableEnv.fromDataSet(source);
        //这样做就是把整个的DataSet转换成了一个table对象，加载到tableEnv上下文环境中
        tableEnv.registerTable("Player",player);


        Table sqlQuery = tableEnv.sqlQuery("select name,goals from Player");
        DataSet<Result> resultDataSet = tableEnv.toDataSet(sqlQuery,Result.class);

           MapOperator<Result, Tuple2<String, Integer>> res = resultDataSet.map(new MapFunction<Result, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(Result result) throws Exception {
                String name = result.name;
                Integer price = result.goals;
                return Tuple2.of(name, price);
            }
        });
        try {
            res.print();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    //一个内部类
    public static class Player{
        public int id;
        public String name;
        public String query;
        public int goals;

        public Player() {
        }

        public Player(int id, String name, String query, int goals) {
            this.id = id;
            this.name = name;
            this.query = query;
            this.goals = goals;
        }

        @Override
        public String toString() {
            return "Player{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", query='" + query + '\'' +
                    ", goals=" + goals +
                    '}';
        }
    }
    public static class Result{
        public String name;
        public Integer goals;
        public Result(){}
    }

}

package jedis.crush;

import org.apache.calcite.rel.rel2sql.SqlImplementor;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.BatchTableEnvironment;

/**
 * PACKAGE_NAMW   jedis.crush
 * DATE      08
 * Author     Crush
 */
public class FlinkTable {
    public static void main(String[] args) {
        ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();


        BatchTableEnvironment tableEnvironment = BatchTableEnvironment.getTableEnvironment(executionEnvironment);
        executionEnvironment.setParallelism(1);


        DataSource<String> stringDataSource = executionEnvironment.readTextFile("stu.txt");



        stringDataSource.map(new MapFunction<String,Order>() {
            @Override
            public Order map(String value) throws Exception {
                String[] split = value.split(" ");

                return new Order(String.valueOf(split[1]),String.valueOf(split[2]),Double.valueOf(split[3]));


            }
        });

        Table order = tableEnvironment.fromDataSet(stringDataSource);

        tableEnvironment.registerTable("Orders",order);

        // scan 已经注册好的表格
        Table select = tableEnvironment.scan("Orders").select("name");
        select.printSchema();

        Table sqlQuery = tableEnvironment.sqlQuery("select name,sum(price) as price from Order group by name");

        DataSet<SqlImplementor.Result> resultDataSet = tableEnvironment.toDataSet(sqlQuery, SqlImplementor.Result.class);


    }

    public static class Order{
        public  String name;
        public  String book;
        public  Double price;

        public Order() {
        }

        public Order(String name, String book, Double price) {
            this.name = name;
            this.book = book;
            this.price = price;
        }

        @Override
        public String toString() {
            return "order{" +
                    "name='" + name + '\'' +
                    ", book='" + book + '\'' +
                    ", price=" + price +
                    '}';
        }
    }
}

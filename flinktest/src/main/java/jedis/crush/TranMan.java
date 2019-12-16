package jedis.crush;

import org.apache.flink.api.java.ExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * PACKAGE_NAMW   jedis.crush
 * DATE      18
 * Author     Crush
 */
public class TranMan {
    public static void main(String[] args) {
        ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();

        List list = new ArrayList<String>();
        list.add("hello xugong");
        list.add("hello xinji");


    }
}

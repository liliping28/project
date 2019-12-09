package thread;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程任务类
 */
public class CounterThread implements Runnable {
    private Counter counter;
    private int number;
    public CounterThread(Counter counter,int number){
        this.number = number;
        this.counter = counter;
    }
    @Override
    public void run() {
        for(int i =0 ; i< number ;i++){
          //counter.increment();
            String str ="{\"apiParamPapeVo\": {\"maxCount\": 12,\"pageNum\": 1,\"pageSize\": 10},\"parameterSetObject\": [{\"key\": \"a\",\"type\": \"table\",\"value\": \"1b2737f89a5a4522a9aa0731612496d5\"}],\"yj\":\"g.E().hasLabel(#{a})\"}";
            HttpUtil.postJson("http://localhost:9020/sjkf-cras/apiServer/runTest",str,null);
        }
    }
}

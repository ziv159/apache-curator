package zookeeper.curator;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.GetConfigBuilder;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.server.util.ConfigUtils;

import java.util.Scanner;

@Slf4j
public class CuratorReconfig {

    private static CuratorFramework curatorFramework;

    private final  static  String connectString = "localhost:2181,localhost:2182,localhost:2183";

    private  static  RetryPolicy  retryPolicy =new ExponentialBackoffRetry(5*1000,5);

    public static void main(String[] args) throws Exception {

         curatorFramework = CuratorFrameworkFactory.newClient(connectString, retryPolicy);

        curatorFramework.getConnectionStateListenable().addListener(((client, newState) -> {
            if (newState== ConnectionState.CONNECTED){
                log.info(" 连接建立");
            }
        }));
        curatorFramework.start();

        Scanner scanner=new Scanner(System.in);
        while (true){


            GetConfigBuilder config = curatorFramework.getConfig();
            byte[] bytes = config.forEnsemble();

            String clientConfigStr = ConfigUtils.getClientConfigStr(new String(bytes));
            log.info("CONFIG: {}",clientConfigStr);

            scanner.next();

        }

    }

}

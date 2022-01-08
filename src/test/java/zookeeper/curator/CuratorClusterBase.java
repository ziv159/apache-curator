package zookeeper.curator;


public  class CuratorClusterBase extends CuratorStandaloneBase {

    private final static  String CLUSTER_CONNECT_STR="localhost:2181,localhost:2182,localhost:2183";

    public   String getConnectStr() {
        return CLUSTER_CONNECT_STR;
    }
}

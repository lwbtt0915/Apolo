package org.framework.server.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lwb
 * zk 服务端
 */
public class DistributedServer {

	private static final String connectString = "localhost:2181,localhost:2182,localhost:2183";

	// 超时设置
	private static final int sessionTimeout = 2000;
	// 父节点
	private static final String parentNode = "/servers";

	private ZooKeeper zk = null;
	
	private static Logger logger = LoggerFactory.getLogger(DistributedServer.class);
	/***
	 * @Desc 获取Zookeeper 链接
	 * @throws IOException
	 */
	public void getConnect() throws IOException {
		/**
		 * Watcher 监听器 每监听一次之后都需要重新注册
		 */
		zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {

			@Override
			public void process(WatchedEvent watchEvent) {
				// 重新监听
				try {
					zk.getChildren("/", true);
				} catch (KeeperException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("[server : " + watchEvent.getType() + ":" + watchEvent.getPath() + "]");
			}
		});
	}

	/***
	 * 
	 * @author lwb
	 * @Desc 向Zookeeper集群注册服务器信息
	 * @param hostName
	 * @throws InterruptedException
	 * @throws KeeperException
	 */
	private void registerServer(String hostName) throws KeeperException, InterruptedException {
		/**
         *  param1 : 要创建的节点路径 
         *  param2 : 节点的数据,必须是byte类型.
         *  param3 : 节点的权限(枚举类型) 
         *  param4 : 创建的类别(枚举类型)
         * 
         */
		String create = zk.create(parentNode + "/server", hostName.getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println(hostName + " register ZooKeeper , Create Node Info :" + create);
		logger.info(hostName + " register ZooKeeper , Create Node Info :" + create);
	}

	private void handleBussiness(String hostName) throws InterruptedException {
		System.out.println(hostName + " start working.....");
		Thread.sleep(Long.MAX_VALUE);
	}
    
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        DistributedServer server = new DistributedServer();
        //获取ZK连接
        server.getConnect();
        //利用ZK连接注册服务器信息
        server.registerServer(args[0]);
        //业务功能
        server.handleBussiness(args[0]);
    }
	
	
}

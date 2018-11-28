package org.framework.server.zookeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lwb
 * @DESC zookeeper 客户端
 */
public class DistributedClient {
   
	private ZooKeeper zk = null;

	private static final String connectString = "localhost:2181,localhost:2182,localhost:2183";
	
	private static final int sessionTimeout = 200000;
	
	private static final String parentNode = "/servers";
	
	private volatile List<String> serverList;
	
	private static Logger logger = LoggerFactory.getLogger(DistributedClient.class);
	
	public void getConnection() throws IOException {
		zk=new ZooKeeper(connectString,sessionTimeout,new Watcher(){

			@Override
			public void process(WatchedEvent event) {
				//重新更新服务器列表，并且注册了监听
				try {
					getServerList();
				} catch (KeeperException e) {
					logger.error("重新更新服务器列表，并且注册了监听失败",e);
				} catch (InterruptedException e) {
					logger.error("重新更新服务器列表，并且注册了监听失败",e);
				}
			}});
	}
	
	/***
	 * @Desc 获取服务器信息列表
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void getServerList() throws KeeperException, InterruptedException {
		//获取服务器子节点信息，对父节点监听
		List<String> childrens=zk.getChildren(parentNode, true);
		List<String> servers = new ArrayList<>();
		for (String child : childrens) {
            //child 只是子节点名
            byte[] data = zk.getData(parentNode + "/" + child, false, null);
            servers.add(new String(data));
        }
		
		//把servers赋值给成员变量serverList,以提供给各业务线程使用
        serverList=servers;
        System.out.println(serverList);
	}
	
    /***
     * 业务功能
     * @throws InterruptedException
     */
    public void handleBussiness() throws InterruptedException {
        System.out.println(" client start work......");
        Thread.sleep(Long.MAX_VALUE);
    }
    
    
    
    public static void main(String[] args)throws Exception {
        //获取zk连接
        DistributedClient client = new DistributedClient();
        client.getConnection();

        //获取servers的子节点信息，从中获取服务器列表信息
        client.getServerList();

        //业务线程启动
        client.handleBussiness();
    }
}

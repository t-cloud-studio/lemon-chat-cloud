package com.tcloud.zkclient;

import com.tcloud.zkclient.factory.ClientFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * ZK 客户端
 *
 * @author Anker
 **/
@Slf4j
@Data
public class CuratorZkClient {


    private CuratorFramework client;

    private String connectionUrl;

    private Integer sessionTimeout;

    /**
     * Zk集群地址
     */
    public static CuratorZkClient instance = null;


    public CuratorZkClient(String connectionUrl, Integer sessionTimeout) {
        this.initClient(connectionUrl, sessionTimeout);
    }

    public void initClient(String connectionUrl, Integer sessionTimeout) {
        if (Objects.nonNull(client)) {
            return;
        }
        //创建客户端
        client = ClientFactory.createSimple(connectionUrl, sessionTimeout);
        //启动客户端实例,连接服务器
        client.start();
        instance = this;
    }

    public void destroy() {
        CloseableUtils.closeQuietly(client);
    }


    /**
     * 创建节点
     */
    public void createNode(String zkPath, String data) {
        try {
            // 创建一个 ZNode 节点
            // 节点的数据为 payload
            byte[] payload = "to set content".getBytes(StandardCharsets.UTF_8);
            if (data != null) {
                payload = data.getBytes(StandardCharsets.UTF_8);
            }
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(zkPath, payload);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除节点
     */
    public void deleteNode(String zkPath) {
        try {
            if (!isNodeExist(zkPath)) {
                return;
            }
            client.delete()
                    .forPath(zkPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 节点是否存在
     *
     * @param path zk路径
     */
    public boolean isNodeExist(String path) {
        try {
            Stat stat = client.checkExists().forPath(path);
            if (null == stat) {
                log.info("path:{} not exists", path);
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建 临时 顺序 节点
     *
     * @param srcPath 路径
     * @return 路径地址
     */
    public String createEphemeralSeqNode(String srcPath) {
        try {

            // 创建一个 ZNode 节点
            return client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(srcPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

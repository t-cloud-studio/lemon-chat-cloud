package com.tcloud.zkclient.cli;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Sets;
import com.tcloud.zkclient.common.constants.CoreConstant;
import com.tcloud.zkclient.common.exceptions.ZkNodeNoDataException;
import com.tcloud.zkclient.factory.ClientFactory;
import lombok.Data;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * ZK 客户端
 *
 * @author Anker
 **/
@Data
public class ZkClient implements InitializingBean, DisposableBean {

    /**
     * Curator 客户端
     */
    private CuratorFramework client;
    /**
     * ZK连接地址
     */
    private String connectionUrl;
    /**
     * 会话超时时间（单位：毫秒）
     */
    private Integer sessionTimeout;
    /**
     * 根节点
     */
    private String rootPath;




    public ZkClient(String connectionUrl, Integer sessionTimeout, String rootPath) {
        if (Objects.nonNull(client)) {
            return;
        }
        //创建客户端
        client = ClientFactory.createSimple(connectionUrl, sessionTimeout);
        //启动客户端实例,连接服务器
        client.start();
        // 根节点
        this.rootPath = rootPath;
    }


    public String buildPath(String childPath){
        if (CharSequenceUtil.isBlank(childPath)){
            throw new NullPointerException("please set you child path");
        }
        return StrUtil.builder(this.rootPath).append(StrPool.SLASH).append(childPath).toString();
    }

    /**
     * 创建持久化节点
     *
     * @param zkPath 路径
     * @param data   节点数据
     */
    public void createNode(String zkPath, String data) {
        try {
            // 创建一个 ZNode 节点
            byte[] payload = "to set content".getBytes(StandardCharsets.UTF_8);
            if (data != null) {
                payload = data.getBytes(StandardCharsets.UTF_8);
            }
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(zkPath, payload);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建持久化节点
     *
     * @param zkPath 路径
     * @param data   节点数据
     */
    public void createNode(String zkPath, Object data) {
        try {
            // 创建一个 ZNode 节点
            if (Objects.isNull(data)) {
                throw new NullPointerException("Data can't be null");
            }
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(zkPath, JSON.toJSONBytes(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建持久化节点
     *
     * @param zkPath 路径
     */
    public void createNode(String zkPath) {
        try {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(zkPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除节点
     *
     * @param zkPath 节点路径
     */
    public void deleteNode(String zkPath) {
        if (CharSequenceUtil.isBlank(zkPath)){
            return;
        }
        if (!nodeExists(zkPath)) {
            return;
        }
        try {
            client.delete().forPath(zkPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取节点数据
     *
     * @param zkPath 节点路径
     */
    public <T> T getData(String zkPath, Class<T> returnType) {
        if (CharSequenceUtil.isBlank(zkPath)){
            return null;
        }
        if (!nodeExists(zkPath)) {
            return null;
        }
        try {
            byte[] bytes = client.getData().forPath(zkPath);
            if (Objects.isNull(bytes) || bytes.length == 0) {
                return null;
            }
            return JSON.parseObject(bytes, returnType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 节点是否存在
     *
     * @param path zk路径
     */
    public boolean nodeExists(String path) {
        try {
            Stat stat = client.checkExists().forPath(path);
            if (null == stat) {
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
            return client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(srcPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void updateData(String nodePath, Object data) {
        if (Objects.isNull(data)) {
            throw new ZkNodeNoDataException("set the data is null");
        }
        if (nodeExists(nodePath)) {
            try {
                client.setData().forPath(nodePath, JSON.toJSONBytes(data));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        this.createNode(nodePath, data);
    }

    public Set<String> getRootChildrenData() {
        Set<String> data = Sets.newHashSet();
        try {
            // 获取所有服务节点
            List<String> children = client.getChildren().forPath(rootPath);
            if (CollUtil.isEmpty(children)){
                return null;
            }
            for (String childNode : children) {
                childNode = StrBuilder.create(rootPath).append(StrPool.SLASH).append(childNode).toString();
                if (!this.nodeExists(childNode)){
                    continue;
                }
                byte[] bytes = client.getData().forPath(childNode);
                if (Objects.isNull(bytes) || bytes.length == 0){
                    continue;
                }
                data.add(new String(bytes,StandardCharsets.UTF_8));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }


    public Set<String> getChildrenData(String zNode) {
        Set<String> data = Sets.newHashSet();
        try {
            List<String> children = client.getChildren().forPath(zNode);
            if (CollUtil.isEmpty(children)){
                return null;
            }
            for (String childNode : children) {
                byte[] bytes = client.getData().forPath(childNode);
                if (Objects.isNull(bytes) || bytes.length == 0){
                    continue;
                }
                data.add(Arrays.toString(bytes));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 雪花算法workerId生成
     *
     * @return
     * @throws Exception
     */
    public long getWorkerId(){
        // 创建临时顺序节点
        String workerPath = this.createEphemeralSeqNode(CoreConstant.SNOWFLAKE_ID_WORKS);
        // 从节点路径中提取worker ID
        String[] parts = workerPath.split("-");
        return Long.parseLong(parts[parts.length - 1]);
    }



    @Override
    public void afterPropertiesSet() throws Exception {
        // 创建根节点
        if (nodeExists(rootPath)) {
            return;
        }
        this.createNode(rootPath);
    }

    /**
     * 销毁客户端
     */
    @Override
    public void destroy() {
        CloseableUtils.closeQuietly(client);
    }


}

package com.tcloud.idgenerator.handler;

import com.tcloud.zkclient.cli.ZkClient;

/**
 * 分布式ID生成器
 *
 * @author Anker+AI
 */
public class DistributedIdGenerator {

    private final SnowflakeIdGenerator idGenerator;
    private final ZkClient zkClient;
    private final String path;

    public DistributedIdGenerator(SnowflakeIdGenerator snowflakeIdGenerator, ZkClient zkClient, String path) {
        this.idGenerator = snowflakeIdGenerator;
        this.zkClient = zkClient;
        this.path = path;
    }


    public long generateId() {
        // 构造一个临时有序节点
        String sequentialPath = zkClient.createEphemeralSeqNode(path);
        String[] parts = sequentialPath.split("/");
        long sequenceId = Long.parseLong(parts[parts.length - 1]);
        long distributeId = idGenerator.generateId() << 10;
        return distributeId | sequenceId;
    }

}

package com.kaifamiao.wendao.utils;

/**
 * 采用雪花算法生成惟一标识符
 */
public class SnowflakeIdGenerator {

    /** "雪花纪元" 即起始时间 (这里以2007年起点为雪花纪元) */
    private static final long SNOWFLAKE_EPOCH = 1167580800000L;

    /** 序列号部分 占用 12 个二进制位 ( 最低的12位 ) */
    private static final long SEQUENCE_BITS = 12L ;

    /** 工作机器编号( 数据机器位 )  占用 10 个二进制位， 其中 数据中心的编号 ( datacenterId ) 占用 5 个二进制位 */
    private static final long DATACENTER_ID_BITS = 5L;

    /** 工作机器编号( 数据机器位 )  占用 10 个二进制位 ， 其中同一个数据中心的不同机器的工作编号 ( workerId ) 占用 5 个二进制位 */
    private static final long WORKER_ID_BITS = 5L;

    /** 最大的数据中心编号 ( datacenterId ) ，数据中心编号最大值为 31 ，有效范围为 [ 0 , 31 ] */
    private static final long MAX_DATACENTER_ID = -1L ^ ( -1L << DATACENTER_ID_BITS );

    /** 最大的工作编号 ( workerId ) ，工作编号最大值为 31 ，有效范围为  [ 0 , 31 ] */
    private static final long MAX_WORKER_ID = -1L ^ ( -1L << WORKER_ID_BITS );

    /** 工作机器编号 中的 工作编号 ( workerId )  需要向左移 12 位才能到达其所占位置 */
    private static final long WORKER_ID_LEFT_SHIFT = SEQUENCE_BITS ;

    /** 工作机器编号 中的 数据中心编号 ( datacenterId )  需要向左移 17 位才能到达其所占位置 ( 5 + 12 ) */
    private static final long DATACENTER_ID_LEFT_SHIFT = WORKER_ID_BITS+ SEQUENCE_BITS ;

    /** 时间戳部分 需要 向左移 22 位 才能到达其所占位置 ( 5 + 5 + 12 ) */
    private static final long TIMESTAMP_LEFT_SHIFT = DATACENTER_ID_BITS + WORKER_ID_BITS+ SEQUENCE_BITS ;

    /** 生成序列的掩码，其取值 4095 ( 0xFFF ) */
    private static final long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS );

    // 用来缓存SnowflakeIdGenerator实例的类变量
    private static SnowflakeIdGenerator generator;

    /** 工作编号 ( 0 ~ 31 ) */
    private long workerId;

    /** 数据中心编号 ( 0 ~ 31 ) */
    private long datacenterId;

    /** 毫秒内序列 ( 0 ~ 4095 ) */
    private long sequence ;

    /** 上次生成ID时的时间截 */
    private long lastTimestamp = -1L;

    /**
     * @param workerId 工作编号，有效范围是 [ 0 ， 31 ]
     * @param datacenterId 数据中心编号，有效范围是 [ 0 ， 31 ]
     */
    private SnowflakeIdGenerator(long workerId, long datacenterId) {

        if ( workerId < 0 || workerId > MAX_WORKER_ID ) {
            throw new IllegalArgumentException( String.format("工作编号(workerId)不能小于零或大于%d", MAX_WORKER_ID ) );
        }

        if ( datacenterId < 0 || datacenterId > MAX_DATACENTER_ID ) {
            throw new IllegalArgumentException(String.format("数据中心编号(datacenterId)不能小于零或大于%d", MAX_DATACENTER_ID));
        }

        this.workerId = workerId;
        this.datacenterId = datacenterId;

    }

    /**
     * 用于创建SnowflakeIdGenerator实例并将其缓存的类方法
     * @param workerId 工作编号
     * @param datacenterId 数据中心编号
     * @return 返回基于雪花算法创建对象标识符的生成器
     */
    public static synchronized SnowflakeIdGenerator build(long workerId, long datacenterId){
        generator = generator == null ? new SnowflakeIdGenerator(workerId, datacenterId) : generator ;
        return generator;
    }

    /**
     * 获取SnowflakeIdGenerator实例
     * @return
     */
    public static synchronized SnowflakeIdGenerator getInstance(){
        return generator;
    }

    /**
     * 用来产生标识符的方法
     * @return
     */
    public synchronized Long generate() {

        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format( "Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        // 如果时间相同，说明在同一个时刻需要产生多个ID
        if ( lastTimestamp == timestamp ) {
            // 为同一时刻计算不同的序列值
            sequence = ( sequence + 1) & SEQUENCE_MASK;
            if ( sequence == 0 ) {
                // 阻塞线程到下一个毫秒
                timestamp = nextMillis(lastTimestamp);
            }
        } else { // 当时间戳改变时，重置毫秒内序列值
            sequence = 0L;
        }

        // 记录本次生成ID时的时间截，为下次生成做准备
        lastTimestamp = timestamp;

        // 移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - SNOWFLAKE_EPOCH) << TIMESTAMP_LEFT_SHIFT) | (datacenterId << DATACENTER_ID_LEFT_SHIFT) | (workerId << WORKER_ID_LEFT_SHIFT) | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID时的时间截
     * @return 当前时间戳
     */
    protected long nextMillis( final long lastTimestamp ) {

        long timestamp = System.currentTimeMillis() ;

        while ( timestamp <= lastTimestamp ) {
            timestamp = System.currentTimeMillis() ;
        }

        return timestamp;
    }
}

package com.zzzzzzzs.kafka.websocket.payload;

import com.google.common.collect.Lists;
import com.zzzzzzzs.kafka.websocket.payload.server.*;
import com.zzzzzzzs.kafka.websocket.model.Server;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 服务器信息VO
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-14 17:25
 */
@Data
public class ServerVO {
    List<MsgVO> msg = Lists.newArrayList();
    List<CpuVO> cpu = Lists.newArrayList();
    List<JvmVO> jvm = Lists.newArrayList();
    List<MemVO> mem = Lists.newArrayList();
    List<SysFileVO> sysFile = Lists.newArrayList();
    List<SysVO> sys = Lists.newArrayList();

    public ServerVO create(Server server) {
        msg.add(MsgVO.create(server.getMsg()));
        cpu.add(CpuVO.create(server.getCpu()));
        jvm.add(JvmVO.create(server.getJvm()));
        mem.add(MemVO.create(server.getMem()));
        sysFile.add(SysFileVO.create(server.getSysFiles()));
        sys.add(SysVO.create(server.getSys()));
        return null;
    }
}

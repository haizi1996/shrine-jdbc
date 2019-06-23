package com.hailin.shrine.jdbc.core.common.spi.algorithm.masterslave;

import com.hailin.shrine.jdbc.api.spi.masterslave.MasterSlaveLoadBalanceAlgorithm;
import com.hailin.shrine.jdbc.core.common.spi.NewInstanceServiceLoader;
import com.hailin.shrine.jdbc.core.common.spi.algorithm.TypeBasedSPIServiceLoader;

public class MasterSlaveLoadBalanceAlgorithmServiceLoader extends TypeBasedSPIServiceLoader<MasterSlaveLoadBalanceAlgorithm> {

    static {
        NewInstanceServiceLoader.register(MasterSlaveLoadBalanceAlgorithm.class);
    }

    public MasterSlaveLoadBalanceAlgorithmServiceLoader() {
        super(MasterSlaveLoadBalanceAlgorithm.class);
    }
}

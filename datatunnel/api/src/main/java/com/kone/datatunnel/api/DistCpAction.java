package com.kone.datatunnel.api;

import java.io.IOException;
import java.io.Serializable;

import com.gitee.melin.bee.core.extension.SPI;

@SPI
public interface DistCpAction extends Serializable{
    void run(DistCpContext context) throws IOException;
}
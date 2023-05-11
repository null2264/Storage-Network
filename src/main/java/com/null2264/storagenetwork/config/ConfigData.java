package com.null2264.storagenetwork.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigData
{
    public boolean logSpam = true;
    public boolean enableAutoSearchFocus = true;
    public boolean reloadNetworkWhenUnloadChunk = false;

    public int exchangeBufferSize = 134217727;
    public int remoteMaxRange = -1;
    public int autoRefreshTicks = 20;

    List<String> NotallowedBlocks = new ArrayList<String>();
}
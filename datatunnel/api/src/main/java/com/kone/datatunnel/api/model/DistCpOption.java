package com.kone.datatunnel.api.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class DistCpOption implements Serializable {
    private String[] srcPaths;
    private String destPath;
    private boolean update = false;
    private boolean overwrite = false;
    private boolean delete = false;
    private boolean ignoreErrors = false;
    private boolean dryRun = false;
    private int maxFilesPerTask = 1000;
    private Long maxBytesPerTask = 1073741824L;
    private int numListstatusThreads = 10;
    private boolean consistentPathBehaviour = false;
    private String[] includes;
    private String[] excludes;
    private boolean excludeHiddenFile = true;

    // 更新覆盖路径行为
    public boolean updateOverwritePathBehaviour(){
        return !consistentPathBehaviour && (update || overwrite);
    }
}

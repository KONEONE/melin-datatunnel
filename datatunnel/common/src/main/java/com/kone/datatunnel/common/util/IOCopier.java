package com.kone.datatunnel.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/*
 * 描述：将多个source文件合并为1个文件
 */
public class IOCopier {
    public static void joinFiles(File destination, File[] sources) throws IOException{
        OutputStream output = null;
        try{
            output = createAppendableStream(destination);
            // 将File合并为1个
            for(File source : sources){
                appendFile(output, source);
                FileUtils.delete(source); // 临时文件
            }
        }finally {
            IOUtils.closeQuietly();
        }
    }
    
    // 创建追加流
    private static BufferedOutputStream createAppendableStream(File destination) throws FileNotFoundException {
        return new BufferedOutputStream(new FileOutputStream(destination, true));
    }
    // 追加文件
    private static void appendFile(OutputStream output, File source) throws IOException{
        InputStream input = null;
        try{
            input = new BufferedInputStream(Files.newInputStream(source.toPath()));
            IOUtils.copy(input, output);
        }finally{
            IOUtils.closeQuietly(input);
        }
        
    }
}

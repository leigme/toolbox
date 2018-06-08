package me.leig.toolbox;

import me.leig.toolbox.resolve.AnalyzeConfig;
import me.leig.toolbox.resolve.ConfigBean;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * 程序入口
 *
 * @author leig
 * @version 20180301
 *
 */
public class App {

    private static Logger log = Logger.getLogger(App.class);

    public static void main(String[] args) {

        if (2 == args.length) {
            switch (args[0]) {
                case "-p":
                    start(args[1]);
                    break;
                default:
                    log.error("命令格式错误");
                    break;
            }
            return;
        }

        start("config/toolbox.json");

    }

    /**
     * 开始执行
     *
     * @param config
     *
     */
    private static void start(String config) {

        File file = new File(config);

        if (!file.exists()) {
            log.error("配置文件不存在...");
            return;
        }

        // 解析配置文件
        AnalyzeConfig analyzeConfig = new AnalyzeConfig(file);

        ConfigBean configBean = analyzeConfig.execute();

        log.info("程序版本是: " + configBean.getVersion());


    }

}

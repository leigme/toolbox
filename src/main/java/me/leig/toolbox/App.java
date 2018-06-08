package me.leig.toolbox;

import org.apache.log4j.Logger;

/**
 * 程序入口
 *
 * @author leig
 * @version 20180301
 *
 */
public class App {

    private static Logger log = Logger.getLogger(App.class);

    public static void main( String[] args ) {

        if (false)
            log.info("输出一行");
        log.info("输出二行");
    }

}

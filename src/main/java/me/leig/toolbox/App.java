package me.leig.toolbox;

import me.leig.toolbox.encrypt.EncryptUtil;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ) {

        try {
            String result = EncryptUtil.getMD5("3247666666");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

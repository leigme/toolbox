/**********************************************************************
*	湖南长沙阳环科技实业有限公司
*	@Copyright (c) 2003-2017 yhcloud, Inc. All rights reserved.
*	
*	This copy of Ice is licensed to you under the terms described in the
*	ICE_LICENSE file included in this distribution.
*	
*	@license http://www.yhcloud.com.cn/license/
**********************************************************************/
package me.leig.toolbox.encrypt;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 * 加密 工具类
 *
 * @author leig
 * @version 20180301
 *
 */
public class EncryptUtil {

	private Logger log = Logger.getLogger(EncryptUtil.class);
	
	private static final String AESTYPE ="AES/ECB/PKCS5Padding"; 
	
    /**
     * 加密
     * 
     * @param content 需要加密的内容
     * @param password  加密密码
     * @return
     */
    public static byte[] aseEncrypt(String content, String password) throws Exception {
	    try {           
	        //KeyGenerator kgen = KeyGenerator.getInstance("AES");
	        //kgen.init(128, new SecureRandom(password.getBytes()));

	        SecretKeySpec keySpec = new SecretKeySpec(password.getBytes(), "AES");
	        // 创建密码器
	        Cipher cipher = Cipher.getInstance(AESTYPE);
	        //byte[] byteContent = content.getBytes("utf-8");
	    	// 初始化
	        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
	        byte[] result = cipher.doFinal(content.getBytes());
	        // 加密结果
	        return result;
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	        throw new Exception("ASE加密失败1:" + e.getMessage());
	    } catch (NoSuchPaddingException e) {
	        e.printStackTrace();
	        throw new Exception("ASE加密失败2:" + e.getMessage());
	    } catch (InvalidKeyException e) {
	        e.printStackTrace();
	        throw new Exception("ASE加密失败3:" + e.getMessage());
	    } catch (IllegalBlockSizeException e) {
	        e.printStackTrace();
	        throw new Exception("ASE加密失败5:" + e.getMessage());
	    } catch (BadPaddingException e) {
	        e.printStackTrace();
	        throw new Exception("ASE加密失败6:" + e.getMessage());
	    }
    }

	/**
	 * 编码处理
	 *
	 * @param data
	 * @return
	 */
	public static String base_encode64(byte[] data) {
    	
    	String result = new String(Base64.encodeBase64(data));
    	 //$src  = array("/","+","=");
         //$dist = array("_a","_b","_c");
    	result = result.replaceAll("/", "_a");
    	result = result.replaceAll("\\+", "_b");
    	result = result.replaceAll("=", "_c");
    	
    	return result;
    }

	/**
	 * 解码处理
	 *
	 * @param result
	 * @return
	 */
	public static byte[] base_decode64(String result) {
	   	 //$src  = array("/","+","=");
	        //$dist = array("_a","_b","_c");
	   	result = result.replaceAll("_a", "/");
	   	result = result.replaceAll("_b", "+");
	   	result = result.replaceAll("_c", "=");
   	
	   	byte[] data = Base64.decodeBase64(result);
	   	
    	return data;
    }
    
	
    /**
	 * 解密
	 *
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
	 *
     */
    public static String aseDecrypt(byte[] content, String password) throws Exception {
	    try {
			
			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
			// 创建密码器
			Cipher cipher = Cipher.getInstance(AESTYPE);
			// 初始化
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = cipher.doFinal(content);
			 // 加密
			return new String(result).trim();
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	        throw new Exception("ASE解密失败1:" + e.getMessage());
	    } catch (NoSuchPaddingException e) {
	        e.printStackTrace();
	        throw new Exception("ASE解密失败2:" + e.getMessage());
	    } catch (InvalidKeyException e) {
	        e.printStackTrace();
	        throw new Exception("ASE解密失败3:" + e.getMessage());
	    } catch (IllegalBlockSizeException | BadPaddingException e) {
	        e.printStackTrace();
	        throw new Exception("ASE解密失败4:" + e.getMessage());
	    }
	}
    
	/**
	 * MD5 加密方法 16位
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 *
	 */
	public static String getMD5(String str) throws Exception {
		try {
			// 生成一个MD5加密计算摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 计算md5函数
			md.update(str.getBytes());
			// digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			return new BigInteger(1, md.digest()).toString(16);
		} catch (Exception e) {
			throw new Exception("MD5加密出现错误");
		}
	}

	/**
	 * MD5 加密方法 256位
	 * 
	 * @param sourceStr
	 * @return
	 * @throws Exception
	 */
	public static String YHMD5(String sourceStr) throws Exception {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(sourceStr.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
			// 转小写
			if (null != result && result.trim().length() > 0) {
				result = result.toLowerCase();
			}
			// System.out.println("MD5(" + sourceStr + ",32) = " + result);
			// System.out.println("MD5(" + sourceStr + ",16) = " +
			// buf.toString().substring(8, 24));
		} catch (Exception e) {
			// System.out.println(e);
			throw new Exception("加密算法失败(BY_YH):" + e.getMessage());
		}
		return result;
	}

}

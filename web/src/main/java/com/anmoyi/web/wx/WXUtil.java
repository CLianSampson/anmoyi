package com.anmoyi.web.wx;

import com.alibaba.fastjson.JSON;
import com.anmoyi.common.Const;
import com.anmoyi.common.StringUtil;
import org.apache.http.conn.HttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

public class WXUtil {
    private static final Logger logger = LoggerFactory.getLogger(WXUtil.class);


    public static DefaultHttpClient httpclient;


    static {
        httpclient = new DefaultHttpClient();
//        httpclient = (DefaultHttpClient) HttpClientConnectionManager.get(httpclient);
        httpclient = (DefaultHttpClient) com.anmoyi.web.wx.http.HttpClientConnectionManager.getSSLInstance(httpclient);
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
    }


    /**
     *
     * getOpenid:(这里用一句话描述这个方法的作用).  此处为获取 session_key   <br/>
     * @author chenlian
    //	 * @param url
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     * @since JDK 1.7
     */
    public static String getOpenid(String code){

        //String url="https://api.weixin.qq.com/sns/oauth2/access_token?";
        //小程序用此接口

//        https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code

        String url="https://api.weixin.qq.com/sns/jscode2session?";

        String appId = Const.APP_ID;
        String appSecret = Const.APP_SECRET;

        //此版本不由配置文件获取
        //ConfigService configService = new ConfigServiceImpl();
        //String appId = configService.getConfigValue(AttributeConst.GZH_APP_ID);
        //String appSecret =configService.getConfigValue(AttributeConst.GZH_APP_SECRET);


        String appid = "appid=" + appId + "&";
        String secret = "secret=" + appSecret + "&";
//		String codeString = "code=" + code + "&";  js_code
        String codeString = "js_code=" + code + "&";
        String grant_type ="grant_type=authorization_code";

        String getUrl = url+appid+secret+codeString+grant_type;

        logger.info("获取openid url ：" + getUrl);

//        HttpGet httpGet = HttpClientConnectionManager.getGetMethod(getUrl);
        HttpGet httpGet = com.anmoyi.web.wx.http.HttpClientConnectionManager.getGetMethod(getUrl);

        Map<String, Object> map =null;

        HttpResponse response;
        try {
            response = httpclient.execute(httpGet);
            String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            logger.info("wx pay return xml:" + jsonStr);

            map = JSON.parseObject(jsonStr,Map.class);

            if (null==map) {
                logger.error(" 获取openid失败，返回为空");
                return null;
            }


            if (StringUtil.isNullOrBlank(map.get("openid"))) {
                logger.error(" 获取openid失败，返回为空");
                return null;
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//        return (String)map.get("openid");
        return (String)map.get("session_key");

    }


    public static void main(String[] args) {
        WXUtil.getOpenid("071aBXKN13SRe916MXIN1NMYKN1aBXKu");
    }

}

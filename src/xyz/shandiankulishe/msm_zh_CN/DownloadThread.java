package xyz.shandiankulishe.msm_zh_CN;

import javafx.beans.property.ReadOnlyMapProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.commons.io.FileUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

class DownloadThread extends Thread {
    private static final Logger logger = LogManager.getLogger(Main.class);
    DownloadThread() {
        logger.info("���ؽ��ȴ�ӡ�߳̽������״̬");
        start();
    }
    public void run(){
        logger.info("���ؽ��ȴ�ӡ�߳�����");
        try {
            File PropertiesFile=new File("Properties\\msmProp.json");
            String GetProperties=FileUtils.readFileToString(PropertiesFile,"UTF-8");
            JSONObject Object=new JSONObject(GetProperties);
            String ServerVersion=Object.getString("ServerVersion");
            String ServerType=Object.getString("ServerType");
            String ServerName=ServerType+"-"+ServerVersion+"-Server.jar";
            URL DownloadUrl= null;
            try {
                DownloadUrl = new URL("http://114.55.143.223/msm/Target/"+ServerName);
            } catch (MalformedURLException e) {
                logger.error(e);
            }
            URLConnection con=DownloadUrl.openConnection();
            long RemoteFileSize=con.getContentLength();
            File DownloadFile=new File("Server\\"+ServerName);
            long DownloadFileSize=DownloadFile.length();
            DownloadFileSize=DownloadFileSize/1048576;
            RemoteFileSize=RemoteFileSize/1048576;
            while (DownloadFileSize<RemoteFileSize){
                DownloadFileSize=DownloadFile.length();
                DownloadFileSize=DownloadFileSize/1048576;
                logger.info("������ "+DownloadFileSize+"/"+RemoteFileSize);
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e){
            logger.error(e);
        }

    }
}

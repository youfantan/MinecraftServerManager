package xyz.shandiankulishe.msm_zh_CN;

import org.apache.commons.io.FileUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Update {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void checkUpdate() throws IOException {
        System.out.println("检查更新...");
        System.out.println("读取文件...");
        File UpdateFile=new File("CurrentVersion.json");
        File GitVerisonCheck=new File("GitVersionCheck.json");
        String FileContent=FileUtils.readFileToString(UpdateFile,"UTF-8");
        JSONObject Object=new JSONObject(FileContent);
        String VersionDesignation=Object.getString("Designation");
        int VersionUpdateCode=Object.getInt("UpdateCode");
        String StandardVersion=Object.getString("StandardVersion");
        System.out.println("----------当前版本信息----------");
        System.out.println("版本代号："+ VersionDesignation);
        System.out.println("更新码:"+VersionUpdateCode);
        System.out.println("标准版本号:"+StandardVersion);
        System.out.println("------------------------------------------------");
        String UpdateURL=Object.getString("UpdateURL");
        URL UpdateUrl=new URL("http://114.55.143.223/msm/GitVersionCheck.json");
        FileUtils.copyURLToFile(UpdateUrl,GitVerisonCheck);
        String GitVersionCheckContent=FileUtils.readFileToString(GitVerisonCheck,"UTF-8");
        JSONObject VersionObject=new JSONObject(GitVersionCheckContent);
        int CheckVersionUpdateCode=VersionObject.getInt("UpdateCode");
        if(CheckVersionUpdateCode>VersionUpdateCode){
            logger.info("找到新版本，是否立即更新？(y/n)");
            Scanner type=new Scanner(System.in);
            String GetType=type.next();
            if(GetType == "y"){
                String UpdateFileUrl=VersionObject.getString("UpdateURL");
                URL SourcesURL=new URL(UpdateFileUrl);
                logger.info("msm将要在"+UpdateFileUrl+"上更新软件");
                logger.info("开始下载");
                File DownloadUpdateFile=new File("TempUpdateFile.zip");
                long DownloadStartTime=System.currentTimeMillis();
                FileUtils.copyURLToFile(SourcesURL,DownloadUpdateFile);
                long DownloadEndTime=System.currentTimeMillis();
                long DownloadTime=DownloadEndTime-DownloadEndTime/1000;
                logger.info("成功下载！耗时"+DownloadTime+" 秒.");
                logger.info("msm将要解压更新文件");
                logger.info("msm正在下载7zip解压工具");
                long DownloadToolsStartTime=System.currentTimeMillis();
                File Tools=new File("Tools");
                Tools.mkdir();
                File unzipexe=new File("7za.exe");
                File unzipdll=new File("7za.dll");
                File unzipdllxa=new File("7zxa.dll");
                URL unzipexeu=new URL("https://github.com/sparanoid/7z/raw/master/extra/7za.exe");
                URL unzipdllu=new URL("https://github.com/sparanoid/7z/raw/master/extra/7za.dll");
                URL unzipdllxau=new URL("https://github.com/sparanoid/7z/raw/master/extra/7zxa.dll");
                FileUtils.copyURLToFile(unzipexeu,unzipexe);
                FileUtils.copyURLToFile(unzipdllu,unzipdll);
                FileUtils.copyURLToFile(unzipdllxau,unzipdllxa);
                long DownloadToolsEndTime=System.currentTimeMillis();
                long DownloadToolsTime=DownloadToolsEndTime-DownloadStartTime/1000;
                logger.info("成功下载！耗时"+DownloadTime+"秒");
                logger.info("msm将要创建一个新bat来更新软件.");
                String B1="Tools\\7za.exe x TempUpdateFile.zip";
                String B2="del msm.jar /s /q";
                String B3="del Properties /s /q";
                String B4="rd Properties /s /q";
                String B5="del CurrentVersion.json /s /q";
                String B6="del TempUpdateFile.zip /s /q";
                String B7="ren GitVersionCheck.json CurrentVersion.json";
                String B8="xcopy TempUpdateFile /s /f /h";
                String B9="java -jar msm.jar";
                String Command=B1+"\r\n"+B2+"\r\n"+B3+"\r\n"+B4+"\r\n"+B5+"\r\n"+B6+"\r\n"+B7+"\r\n"+B8+"\r\n"+B9;
                File UpdateBat=new File("Update.bat");
                FileUtils.write(UpdateFile,Command,"UTF-8");
                Process taskUpdate=Runtime.getRuntime().exec("cmd Update.bat");
                System.exit(0);
            }
            else {
                logger.info("未找到可用更新，msm是最新的版本");
            }
        }
    }
}

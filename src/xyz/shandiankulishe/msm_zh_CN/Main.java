package xyz.shandiankulishe.msm_zh_CN;

import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONObject;

import xyz.shandiankulishe.UploadPluginEditer.UPE;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String []agrs) throws IOException, InterruptedException {
        logger.trace("欢迎使用Minecraft服务器管理系统");
        Update.checkUpdate();
        logger.info("正在读取配置文件...");
        System.out.println("1.进入/安装服务端软件");
        System .out.println("2.生成插件描述文档");
        Scanner Options=new Scanner(System.in);
        int OI=Options.nextInt();
        if (OI == 2){
            new UPE().Frame();
            for (int i=1;i>0;i++){
                Scanner Wait=new Scanner(System.in);
            }
        }
        //Properties的基本内容，将采用json格式存储
        /*
        {
	        "isInstalled":${isinstalled}
	        "ServerVersion":${serverversion}
	        "ServerType":${servertype}
	        "msmVersion":${msmversion}
        }
         */
        File porpdir=new File("Properties");
        File PropFile=new File("Properties\\msmProp.json");
        if(!porpdir.exists()){
            logger.warn("找不到配置文件目录，正在尝试创建...");
            porpdir.mkdir();
            //创建Properties目录
        }
        if (!PropFile.exists()){
            logger.warn("找不到位于Properties\\msmProp.json文件，msm将要创建一个新的配置文件");
            PropFile.createNewFile();
            //创建文件并写入基本内容
            String CommonProperties="{\"isInstalled\":\"false\",\"msmVersion\":\"0.0.1-SNAPSHOT\"}";
            FileUtils.write(PropFile,CommonProperties,"UTF-8");
        }
        else {
            logger.info("成功找到配置文件！msm正在获取信息...");
            //读取msmProp.json内容
        }
        File msmProp=new File("Properties\\msmProp.json");
        String msmProperties=FileUtils.readFileToString(msmProp,"UTF-8");
        //使用org.apache.commons.io的FileUtills方法读取msmProp.json内容到字符串msmProperties
        JSONObject propob=new JSONObject(msmProperties);
        //创建一个JSONObject对应msmProperties
        String installState=propob.getString("isInstalled");
        String iva=new String(installState);
        String atrue=new String("true");
        //检测服务端软件是否安装
        if (iva.equals(atrue)){
            logger.info("服务端软件已经被正确地安装，马上开始运行服务端软件");
            //安装则执行RunServer方法
            RunServer();
        }
        else {
            logger.warn("未找到服务端软件，msm将会帮助您配置一个全新的服务端");
            //未安装则执行InstallServer方法
            InstallServer();
        }
    }
    public static void InstallServer() throws IOException, InterruptedException {
        long starttime=System.currentTimeMillis();
        File msmProp=new File("Properties\\msmProp.json");
        String msmProperties=FileUtils.readFileToString(msmProp,"UTF-8");
        JSONObject msmob=new JSONObject(msmProperties);
        System.out.println("请选择您要安装的服务端软件的类型，选择完成后需要确认您的服务端软件对应的Minecraft游戏版本");
        String Types[]={"Craftbukkit","Spigot","Vanilla"};
        for(int j=0;j<3;j++){
            String printtype=Types[j];
            System.out.println(j+"  "+printtype);
        }
        Scanner scanType = new Scanner(System.in);
        int getType=scanType.nextInt();
        String type=Types[getType];
        msmob.put("ServerType",type);
        if (type == "Craftbukkit"){
            String BVersion[]={"1.8","1.8.3","1.8.8","1.9","1.9.2","1.9.4","1.10.2","1.11","1.11.2","1.12","1.13","1.13.2"};
            for (int a=0;a<12;a++){
                String BPVersion=BVersion[a];
                System.out.println(a+"  "+BPVersion);
            }
            Scanner scanVersion=new Scanner(System.in);
            int getVersion=scanVersion.nextInt();
            String version=BVersion[getVersion];
            msmob.put("ServerVersion",version);
            String WriteJson=msmob.toString();
            FileUtils.write(msmProp,WriteJson,"UTF-8",false);
        }
        else if (type == "Spigot"){
            String SVersion[]={"1.8","1.8.3","1.8.8","1.9","1.9.2","1.9.4","1.10.2","1.11","1.11.2","1.12","1.13","1.13.2","1.14","1.14.4","1.15","1.15.2"};
            for (int b=0;b<16;b++){
                String PSVersion=SVersion[b];
                System.out.println(b+"  "+PSVersion);
            }
            Scanner scanVersion=new Scanner(System.in);
            int getVersion=scanVersion.nextInt();
            String version=SVersion[getVersion];
            msmob.put("ServerVersion",version);
            String WriteJson=msmob.toString();
            FileUtils.write(msmProp,WriteJson,"UTF-8",false);
        }
        else if (type == "Vanilla"){
            String VVersion[]={"1.8","1.8.3","1.8.4","1.8.5","1.8.6","1.8.8","1.9","1.9.2","1.9.4","1.10","1.10.2","1.11","1.11.2","1.12","1.12.2","1.13","1.13.2","1.14","1.14.4","1.15","1.15.2"};
            for (int c=0;c<21;c++){
                String PVVersion=VVersion[c];
                System.out.println(c+"  "+PVVersion);
            }
            Scanner scanVersion=new Scanner(System.in);
            int getVersion=scanVersion.nextInt();
            String version=VVersion[getVersion];
            msmob.put("ServerVersion",version);
            String WriteJson=msmob.toString();
            FileUtils.write(msmProp,WriteJson,"UTF-8",false);

        }
        File jsonFile=new File("Properties\\msmProp.json");
        String GetJsonVersion=FileUtils.readFileToString(jsonFile,"UTF-8");
        JSONObject object=new JSONObject(GetJsonVersion);
        String version=object.getString("ServerVersion");
        String servertype=object.getString("ServerType");
        String ServerName=servertype+"-"+version+"-Server.jar";
        logger.info("正在创建目录...");
        File ServerDir=new File("Server");
        ServerDir.mkdir();
        File DownloadFile=new File("Server\\"+ServerName);
        logger.info("开始下载服务端软件...");
        String Url="http://114.55.143.223/msm/Target/"+ServerName;
        URL Downloadurl=new URL(Url);
        logger.info("等待下载进度打印线程启动...");
        new DownloadThread();
        Thread.sleep(1000);
        FileUtils.copyURLToFile(Downloadurl,DownloadFile);
        Thread.sleep(1000);
        logger.info("下载成功！");
        System.out.println("现在msm将要协助您设置服务端软件的堆栈内存（即运行内存），请注意，如果您的计算机系统是基于X86架构的32位操作系统，您最多可以设置4*1024MiB即4GiB可用堆栈内存，而如果您的计算机系统是基于amd64架构的64位操作系统，则您最多可以设置128*1024MiB即128GiB可用堆栈内存");
        Properties systemProp=new Properties(System.getProperties());
        String arch=systemProp.getProperty("os.arch");
        System.out.println("您的操作系统架构是 "+arch+"。");
        System.out.println("同样的，您也可以在Windows命令行中输入systeminfo来查看您的系统架构.如果您获得的结果异于msm给定的结果，请检查您的jre(java运行时环境)或jdk(java开发包)的版本（i386/amd64)");
        System.out.print("The Stack Memory You Need in Minecraft Server.(Recommended Stack Memory is 2048M)");
        Scanner Memory=new Scanner(System.in);
        int ServerStackMemory=Memory.nextInt();
        String RunServerBat="java -Xmx"+ServerStackMemory+"M"+" -jar "+"Server\\"+type+"-"+version+"-"+"Server.jar nogui";
        File run=new File("Server\\run.bat");
        run.createNewFile();
        FileUtils.write(run,RunServerBat,"UTF-8");
        logger.info("成功地配置了服务器");
        long endtime=System.currentTimeMillis();
        long taketimelong=endtime-starttime/1000;
        String taketime=String.valueOf(taketimelong);
        logger.info("配置成功！耗时:"+taketime+"秒");
        logger.info("更改配置文件...");
        object.put("isInstalled","true");
        String WriteJson=object.toString();
        FileUtils.write(jsonFile,WriteJson,"UTF-8");
        for(int i=3;i>0;i--){
            Thread.sleep(1000);
            String exitinfo="msm将在"+i+"秒退出，请随后手动重启msm";
            logger.info(exitinfo);
        }
        System.exit(0);
    }
    public static void RunServer() throws IOException, InterruptedException {
        logger.info("开始运行服务端软件");
        File runbat=new File("Server\\run.bat");
        Process runtask=Runtime.getRuntime().exec("cmd /k Start Server\\run.bat");
        for(int i=3;i>0;i--){
            Thread.sleep(1000);
            String exitinfo="服务端软件正在启动...msm将在"+i+"秒退出";
            logger.info(exitinfo);
        }
        System.exit(0);
    }
}
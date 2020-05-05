package xyz.shandiankulishe.msm;

import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

import org.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import xyz.shandiankulishe.UploadPluginEditer.UPE;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String []agrs) throws IOException, InterruptedException {
        logger.trace("Welcome To MinecraftServerManager");
        Update.checkUpdate();
        //调用Update类，实例化并调用方法
        logger.info("Reading The Properties for MSM...");
        System.out.println("1.Enter Minecraft Server Manager");
        System .out.println("2.Generate Plugin Docs");
        Scanner Options=new Scanner(System.in);
        //获取初始化选项
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
        File PropFile=new File("Properties\\msmprop.json");
        if(!porpdir.exists()){
            logger.warn("Can't Find Folder Prperties.msm Will make a new Folder");
            porpdir.mkdir();
            //创建Properties目录
        }
        if (!PropFile.exists()){
            logger.warn("Can't Find Properties\\msmProp.json,msm Will Create a new Properties");
            PropFile.createNewFile();
            //创建文件并写入基本内容
            String CommonProperties="{\"isInstalled\":\"false\",\"msmVersion\":\"0.0.1-SNAPSHOT\"}";
            FileUtils.write(PropFile,CommonProperties,"UTF-8");
        }
        else {
            logger.info("Successful Find Properties.msm is getting INFORMATION...");
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
            logger.info("Server is Installed.Waiting For Run Server");
            //安装则执行RunServer方法
            RunServer();
        }
        else {
            logger.warn("Server Not Installed.Now msm will help you to install server");
            //未安装则执行InstallServer方法
            InstallServer();
        }
    }
   public static void InstallServer() throws IOException, InterruptedException {
        long starttime=System.currentTimeMillis();
        File msmProp=new File("Properties\\msmProp.json");
        String msmProperties=FileUtils.readFileToString(msmProp,"UTF-8");
        JSONObject msmob=new JSONObject(msmProperties);
        System.out.println("Please Choose Which Server Type Do You Want To Install");
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
        logger.info("Making Directory...");
        File ServerDir=new File("Server");
        ServerDir.mkdir();
        File DownloadFile=new File("Server\\"+ServerName);
        logger.info("Start To Download Server Files...");
        String Url="http://114.55.143.223/msm/Target/"+ServerName;
        URL Downloadurl=new URL(Url);
        logger.info("Waiting For Start Download Progress Print Thread...");
        new DownloadThread();
        Thread.sleep(1000);
        FileUtils.copyURLToFile(Downloadurl,DownloadFile);
        Thread.sleep(1000);
        logger.info("Download Successful");
        System.out.println("Now msm Will Set Server's Stack Memory,Please Attention,If your computer is x86,the Server's MAX Stack Memory is 4096M(4 GiB),But if your computer is amd64,the Server's MAX Stack Memory is 131072M(128GiB)");
        Properties systemProp=new Properties(System.getProperties());
        String arch=systemProp.getProperty("os.arch");
        System.out.println("Your Computer's Arch is "+arch+".");
        System.out.println("But You Can Also Type systeminfo in Windows Console to check your computer's arch.If the result is different from msm's result,please check your jre(java runtime environment) or jdk(java development kits) version.If msm's result is x86 but your Windows Console's result is amd64,you should change your java version as amd64（x64)");
        System.out.print("The Stack Memory You Need in Minecraft Server.(Recommended Stack Memory is 2048M)");
        Scanner Memory=new Scanner(System.in);
        int ServerStackMemory=Memory.nextInt();
        String RunServerBat="java -Xmx"+ServerStackMemory+"M"+" -jar "+"Server\\"+type+"-"+version+"-"+"Server.jar nogui";
        File run=new File("Server\\run.bat");
        run.createNewFile();
        FileUtils.write(run,RunServerBat,"UTF-8");
        logger.info("Successful Installed Server");
        long endtime=System.currentTimeMillis();
        long taketimelong=endtime-starttime;
        String taketime=String.valueOf(taketimelong);
        logger.info("Take time:"+taketime+" ms");
        logger.info("Changing Properties...");
        object.put("isInstalled","true");
        String WriteJson=object.toString();
        FileUtils.write(jsonFile,WriteJson,"UTF-8");
       for(int i=3;i>0;i--){
           Thread.sleep(1000);
           String exitinfo="Server is Start...msm Will Exit in"+i+"Seconds";
           logger.info(exitinfo);
       }
       System.exit(0);
   }
   public static void RunServer() throws IOException, InterruptedException {
        logger.info("Start to Run Server");
        File runbat=new File("Server\\run.bat");
        Process runtask=Runtime.getRuntime().exec("cmd /k Start Server\\run.bat");
        for(int i=3;i>0;i--){
            Thread.sleep(1000);
            String exitinfo="Server is Start...msm Will Exit in"+i+"Seconds";
            logger.info(exitinfo);
        }
        System.exit(0);
   }
}
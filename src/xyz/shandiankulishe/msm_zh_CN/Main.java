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
        logger.trace("��ӭʹ��Minecraft����������ϵͳ");
        Update.checkUpdate();
        logger.info("���ڶ�ȡ�����ļ�...");
        System.out.println("1.����/��װ��������");
        System .out.println("2.���ɲ�������ĵ�");
        Scanner Options=new Scanner(System.in);
        int OI=Options.nextInt();
        if (OI == 2){
            new UPE().Frame();
            for (int i=1;i>0;i++){
                Scanner Wait=new Scanner(System.in);
            }
        }
        //Properties�Ļ������ݣ�������json��ʽ�洢
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
            logger.warn("�Ҳ��������ļ�Ŀ¼�����ڳ��Դ���...");
            porpdir.mkdir();
            //����PropertiesĿ¼
        }
        if (!PropFile.exists()){
            logger.warn("�Ҳ���λ��Properties\\msmProp.json�ļ���msm��Ҫ����һ���µ������ļ�");
            PropFile.createNewFile();
            //�����ļ���д���������
            String CommonProperties="{\"isInstalled\":\"false\",\"msmVersion\":\"0.0.1-SNAPSHOT\"}";
            FileUtils.write(PropFile,CommonProperties,"UTF-8");
        }
        else {
            logger.info("�ɹ��ҵ������ļ���msm���ڻ�ȡ��Ϣ...");
            //��ȡmsmProp.json����
        }
        File msmProp=new File("Properties\\msmProp.json");
        String msmProperties=FileUtils.readFileToString(msmProp,"UTF-8");
        //ʹ��org.apache.commons.io��FileUtills������ȡmsmProp.json���ݵ��ַ���msmProperties
        JSONObject propob=new JSONObject(msmProperties);
        //����һ��JSONObject��ӦmsmProperties
        String installState=propob.getString("isInstalled");
        String iva=new String(installState);
        String atrue=new String("true");
        //�����������Ƿ�װ
        if (iva.equals(atrue)){
            logger.info("���������Ѿ�����ȷ�ذ�װ�����Ͽ�ʼ���з�������");
            //��װ��ִ��RunServer����
            RunServer();
        }
        else {
            logger.warn("δ�ҵ�����������msm�������������һ��ȫ�µķ����");
            //δ��װ��ִ��InstallServer����
            InstallServer();
        }
    }
    public static void InstallServer() throws IOException, InterruptedException {
        long starttime=System.currentTimeMillis();
        File msmProp=new File("Properties\\msmProp.json");
        String msmProperties=FileUtils.readFileToString(msmProp,"UTF-8");
        JSONObject msmob=new JSONObject(msmProperties);
        System.out.println("��ѡ����Ҫ��װ�ķ������������ͣ�ѡ����ɺ���Ҫȷ�����ķ���������Ӧ��Minecraft��Ϸ�汾");
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
        logger.info("���ڴ���Ŀ¼...");
        File ServerDir=new File("Server");
        ServerDir.mkdir();
        File DownloadFile=new File("Server\\"+ServerName);
        logger.info("��ʼ���ط�������...");
        String Url="http://114.55.143.223/msm/Target/"+ServerName;
        URL Downloadurl=new URL(Url);
        logger.info("�ȴ����ؽ��ȴ�ӡ�߳�����...");
        new DownloadThread();
        Thread.sleep(1000);
        FileUtils.copyURLToFile(Downloadurl,DownloadFile);
        Thread.sleep(1000);
        logger.info("���سɹ���");
        System.out.println("����msm��ҪЭ�������÷��������Ķ�ջ�ڴ棨�������ڴ棩����ע�⣬������ļ����ϵͳ�ǻ���X86�ܹ���32λ����ϵͳ��������������4*1024MiB��4GiB���ö�ջ�ڴ棬��������ļ����ϵͳ�ǻ���amd64�ܹ���64λ����ϵͳ����������������128*1024MiB��128GiB���ö�ջ�ڴ�");
        Properties systemProp=new Properties(System.getProperties());
        String arch=systemProp.getProperty("os.arch");
        System.out.println("���Ĳ���ϵͳ�ܹ��� "+arch+"��");
        System.out.println("ͬ���ģ���Ҳ������Windows������������systeminfo���鿴����ϵͳ�ܹ�.�������õĽ������msm�����Ľ������������jre(java����ʱ����)��jdk(java������)�İ汾��i386/amd64)");
        System.out.print("The Stack Memory You Need in Minecraft Server.(Recommended Stack Memory is 2048M)");
        Scanner Memory=new Scanner(System.in);
        int ServerStackMemory=Memory.nextInt();
        String RunServerBat="java -Xmx"+ServerStackMemory+"M"+" -jar "+"Server\\"+type+"-"+version+"-"+"Server.jar nogui";
        File run=new File("Server\\run.bat");
        run.createNewFile();
        FileUtils.write(run,RunServerBat,"UTF-8");
        logger.info("�ɹ��������˷�����");
        long endtime=System.currentTimeMillis();
        long taketimelong=endtime-starttime/1000;
        String taketime=String.valueOf(taketimelong);
        logger.info("���óɹ�����ʱ:"+taketime+"��");
        logger.info("���������ļ�...");
        object.put("isInstalled","true");
        String WriteJson=object.toString();
        FileUtils.write(jsonFile,WriteJson,"UTF-8");
        for(int i=3;i>0;i--){
            Thread.sleep(1000);
            String exitinfo="msm����"+i+"���˳���������ֶ�����msm";
            logger.info(exitinfo);
        }
        System.exit(0);
    }
    public static void RunServer() throws IOException, InterruptedException {
        logger.info("��ʼ���з�������");
        File runbat=new File("Server\\run.bat");
        Process runtask=Runtime.getRuntime().exec("cmd /k Start Server\\run.bat");
        for(int i=3;i>0;i--){
            Thread.sleep(1000);
            String exitinfo="����������������...msm����"+i+"���˳�";
            logger.info(exitinfo);
        }
        System.exit(0);
    }
}
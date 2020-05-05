package xyz.shandiankulishe;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

import org.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LaunchApp {
    private static final Logger logger = LogManager.getLogger(LaunchApp.class);
    public static void main(String[]args){
        try {
            File PropertiesFile=new File("Appcation.json");
            if (!PropertiesFile.exists()){
                PropertiesFile.createNewFile();
                String WriteInit="{}";
                FileUtils.write(PropertiesFile,WriteInit,"UTF-8");
                String AppPropertiesString=FileUtils.readFileToString(PropertiesFile,"UTF-8");
                JSONObject Object=new JSONObject(AppPropertiesString);
                System.out.println("Please Choose Your Language(cn/en)");
                System.out.println("«Î—°‘Òƒ˙µƒ”Ô—‘£®cn/en£©");
                Scanner scn=new Scanner(System.in);
                String lang=new String(scn.next());
                String ENlang=new String("en");
                String CNlang=new String("cn");
                if (lang.equals(ENlang)){
                    Object.put("lang","en");
                    String WriteProperties=Object.toString();
                    FileUtils.write(PropertiesFile,WriteProperties,"UTF-8");
                    xyz.shandiankulishe.msm.Main EnMain=new xyz.shandiankulishe.msm.Main();
                    EnMain.main(null);
                }
                else if (lang.equals(CNlang)){
                    Object.put("lang","cn");
                    String WriteProperties=Object.toString();
                    FileUtils.write(PropertiesFile,WriteProperties,"UTF-8");
                    xyz.shandiankulishe.msm_zh_CN.Main CnMain=new xyz.shandiankulishe.msm_zh_CN.Main();
                    CnMain.main(null);
                }
            }
            else {
                String AppPropertiesString=FileUtils.readFileToString(PropertiesFile,"UTF-8");
                JSONObject Object=new JSONObject(AppPropertiesString);
                String lang=Object.getString("lang");
                String launguage=new String(lang);
                String Statecn=new String("cn");
                String Stateen=new String("en");
                if (launguage.equals(Stateen)){
                    xyz.shandiankulishe.msm.Main EnMain=new xyz.shandiankulishe.msm.Main();
                    EnMain.main(null);
                } else if (launguage.equals(Statecn)){
                    xyz.shandiankulishe.msm_zh_CN.Main CnMain=new xyz.shandiankulishe.msm_zh_CN.Main();
                    CnMain.main(null);
                }
            }
        } catch (IOException | InterruptedException e){
            logger.error(e);
        }
    }


}

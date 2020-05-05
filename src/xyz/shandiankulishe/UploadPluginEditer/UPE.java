package xyz.shandiankulishe.UploadPluginEditer;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;

import org.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.commons.io.FileUtils;

public class UPE extends JFrame{
    private static final Logger logger = LogManager.getLogger(UPE.class);
    public static void Frame(){
        logger.info("UploadPluginEditer Start Successful|插件描述文档生成类启动成功");
        String L="\r\n";
        JFrame Frame=new JFrame("Plugin Upload Editer|插件描述文档生成");
        JPanel Panel=new JPanel();
        JLabel PluginNameTextEn=new JLabel("<html><body><p>Please enter the plugin name in the following TextField</p><br></body></html>");
        JLabel PluginNameTextCn=new JLabel("<html><body><p>请在下面的输入框中输入插件的名字</p><br></body></html>");
        JTextField PluginName=new JTextField(20);
        JLabel PluginVersionTextEn=new JLabel("<html><body><p>Please enter the plugin version in following TextField</p><br></body></html>");
        JLabel PluginVersionTextCn=new JLabel("<html><body><p>请在下面的输入框中输入插件的版本</p><br></body></html>");
        JTextField PluginVersion=new JTextField(20);
        JLabel PluginTypeTextEn=new JLabel("<html><body><p>Please enter the type of the plugin version in following TextField(CraftBukkit/Spigot/All)</p><br></body></html>");
        JLabel PluginTypeTextCn=new JLabel("<html><body><p>请在下面的输入框中输入插件的类型（CraftBukkit/Spigot/All）</p><br></body></html>");
        JTextField PluginType=new JTextField(20);
        JLabel PluginDescriptionEn=new JLabel("<html><body><p>Please enter the description of the plugin"+"(such as how to run it,what are the functions etc.)</p><br></body></html>");
        JLabel PluginDescriptionCn=new JLabel("<html><body><p>请输入这个插件的描述，例如如何运行它，配置文件怎么写，有哪些功能等</p><br></body></html>");
        JTextArea PluginDescription=new JTextArea(10,30);
        JScrollPane PluginDescriptionSP=new JScrollPane(PluginDescription);
        JButton generate=new JButton("Generate Plugin Documentation|生成插件描述文档");
        generate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String Name=PluginName.getText();
                    String Version=PluginVersion.getText();
                    String Type=PluginType.getText();
                    String Description=PluginDescription.getText();
                    String HEAD="<!DICTYPE html>"+L+"<html>"+L+"<head>"+L+"<title>"+Name+"</title>"+L+"<head>"+"<body>"+L;
                    String NAME="<h3>Plugin Name:"+Name+"</h3><br>"+L;
                    String VERSION="<h3>Plugin Version:"+Version+"</h3><br>"+L;
                    String TYPE="<h3>Plugin Type:"+Type+"</h3><br>"+L;
                    String DESCRIPTION="<p>Plugin Description:"+Description+"</p><br>"+L;
                    String FOOT="</body>"+L+"</html>";
                    String TEXT=HEAD+NAME+VERSION+TYPE+DESCRIPTION+FOOT;
                    String TITLE=Name+".html";
                    File PluginUploadFile=new File(TITLE);
                    PluginUploadFile.createNewFile();
                    FileUtils.write(PluginUploadFile,TEXT,"UTF-8");
                    logger.info("Write Successful！|成功写入！");
                    System.exit(0);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
        Frame.add(Panel);
        Panel.add(PluginNameTextEn);
        Panel.add(PluginNameTextCn);
        Panel.add(PluginName);
        Panel.add(PluginVersionTextEn);
        Panel.add(PluginVersionTextCn);
        Panel.add(PluginVersion);
        Panel.add(PluginTypeTextEn);
        Panel.add(PluginTypeTextCn);
        Panel.add(PluginType);
        Panel.add(PluginDescriptionEn);
        Panel.add(PluginDescriptionCn);
        Panel.add(PluginDescriptionSP);
        Panel.add(generate);
        Font MSBB=new Font("??????",Font.PLAIN,14);
        Font MSBS=new Font("??????",Font.PLAIN,10);
        PluginNameTextEn.setFont(MSBB);
        PluginNameTextCn.setFont(MSBB);
        PluginVersionTextEn.setFont(MSBB);
        PluginVersionTextCn.setFont(MSBB);
        PluginTypeTextEn.setFont(MSBS);
        PluginTypeTextCn.setFont(MSBB);
        PluginDescriptionEn.setFont(MSBS);
        PluginDescriptionCn.setFont(MSBS);
        Panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setSize(460,650);
        Frame.setVisible(true);
    }
}

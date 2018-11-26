package com.tianyi.helmet.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 输出源代码到1个文本文件
 * Created by liuhanc on 2018/5/8.
 */
public class OutputSrcToTxt {
    private BufferedWriter bw = null;
    private int counter = 0;
    public OutputSrcToTxt() {
        File txtSrcFile = new File("D:\\work\\doc\\北京田一\\软件著作权\\java-src-all.txt");
        try {
            FileWriter fw = new FileWriter(txtSrcFile);
            bw = new BufferedWriter(fw);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> readFileContent(File file) {
        List<String> lineList = new ArrayList();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
                lineList.add(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lineList;
    }

    private void writeLine(String line) {
        try {
            bw.write(line);
            bw.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void flush(){
        try{
            bw.flush();
        }catch(Exception e){
           e.printStackTrace();
        }
    }

    public void outputFile(File file) {
        counter++;
        System.out.println(counter+":"+file.getName());
        writeLine("--------" + file.getName() + "--------");
        List<String> lines = readFileContent(file);
        if (lines == null || lines.size() == 0)
            return;

        lines.stream()
                .filter(line -> !line.contains(" Created by "))
                .filter(line -> !line.contains("@author "))
                .filter(line -> !line.trim().startsWith("* http://"))
                .forEach(line -> {
                    writeLine(line);
                });
        writeLine("");
        flush();
    }

    public void loopDir(File dir) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                loopDir(file);
            } else {
                outputFile(file);
            }
        }
    }

    public static void main(String[] a) throws Exception {
        File dir = new File("D:\\workplace\\tianyi\\backend\\hm-server\\src\\main\\java");
        OutputSrcToTxt outputSrcToTxt = new OutputSrcToTxt();
        outputSrcToTxt.loopDir(dir);
    }
}

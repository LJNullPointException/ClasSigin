package com.design.classsign.utils;

/**
 * Created by yuer on 2017/2/27.
 */


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class FileUtil {


    /**
     * 初始化保存路径
     *
     * @return
     */
    public static String initPath(String fileDir) {
      //初始化存储路径
        String storagePath = "";
        return storagePath;
    }

    public static String initFile(String filename) {
        //初始化文件
        return "";
    }



    public static boolean createDirectory(String dirPath, boolean clear) {
        if (null == dirPath) {
            return false;
        }

        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (clear && dir.exists()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                file.delete();
            }
        }
        return dir.exists();
    }


//    public static String readSerialNumber() {
//        String content = "";
//        String configTxt = readConfigFile();
//        String[] configs = configTxt.split("\n");
//        for(String config : configs){
//            if(config.contains(CONFIG_KEY_SERIAL_NUMBER)){
//                content = config.split("=")[1];
//                break;
//            }
//        }
//        if(TextUtils.isEmpty(content)){
//            return configTxt;
//        }
//        return content;
//    }


    public static void delFile(String path) {
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    //删除文件和目录
    public static void clearFiles(String workspaceRootPath) {
        File file = new File(workspaceRootPath);
        if (file.exists()) {
            deleteFile(file);
        }
    }

    private static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFile(files[i]);
            }
        }
        file.delete();
    }



    public static String[] getSceneFiles(String path) {
        String files[] = null;
        File localDir = new File(path);
        if (!localDir.exists()) {
            Log.e("willz", "目录 " + path + " 不存在");
        } else {
            if (localDir.isDirectory() && localDir.list().length > 0) {
                String[] tmps = localDir.list();
                files = new String[tmps.length];
                for (int i = 0; i < tmps.length; i++) {
                    files[i] = path + File.separator + tmps[i];
                }
            } else {
                files = new String[1];
                files[0] = path;
            }
        }
        return files;
    }


    public static void saveBitmap2File(Bitmap bitmap, String path, String fileName) throws IOException {
        String name = initPath(path) + File.separator + fileName + ".jpg";
        Log.e("willz", "saveBitmap2File : " + name);
        FileOutputStream fos = null;
        fos = new FileOutputStream(name);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();
    }

    public static void saveString2File(String string, String path, String fileName) throws IOException {
        String name = initPath(path) + File.separator + fileName + ".txt";
        FileOutputStream fos = null;
        fos = new FileOutputStream(name);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bos.write(string.getBytes());
        bos.flush();
        bos.close();
        fos.flush();
        fos.close();
    }

    public static void saveStringShort2File(short[] array, String path, String fileName) throws IOException {

        FileWriter fw = null;
        String name = initPath(path) + File.separator + fileName + ".txt";
        fw = new FileWriter(name);

        for (int i = 0; i < array.length; i++) {
            if (i == array.length - 1) {
                fw.write(array[i] + "");
            } else {
                fw.write(array[i] + " ");
            }
        }
        fw.close();
    }

    public static void saveShortArray2File(short[] array, String path, String fileName) throws IOException {
        String name = initPath(path) + File.separator + fileName + ".txt";
        FileOutputStream fos = null;
        fos = new FileOutputStream(name);
        DataOutputStream dos = new DataOutputStream(fos);
        for (Short s : array) {
            dos.writeShort(s);
        }
        dos.flush();
        dos.close();
        fos.flush();
        fos.close();
    }

    public static void copyFolder(AssetManager assetManager, String assetName, String targetPath) {
        // "Name" is the name of your folder!
//        AssetManager assetManager = getAssets();
        String[] files = null;

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            // Checking file on assets subfolder
            try {
                files = assetManager.list(assetName);
            } catch (IOException e) {
                Log.e("ERROR", "Failed to get asset file list.", e);
            }
            // Analyzing all file on assets subfolder
            for (String filename : files) {
                InputStream in = null;
                OutputStream out = null;
                // First: checking if there is already a target folder
                File folder = new File(targetPath + File.separator + assetName);
                boolean success = true;
                if (!folder.exists()) {
                    success = folder.mkdir();
                }
                if (success) {
                    // Moving all the files on external SD
                    try {
                        in = assetManager.open(assetName + "/" + filename);
                        out = new FileOutputStream(targetPath + File.separator + assetName + "/" + filename);
                        Log.i("WEBVIEW", targetPath + File.separator + assetName + "/" + filename);
                        copyFile(in, out);
                        in.close();
                        in = null;
                        out.flush();
                        out.close();
                        out = null;
                    } catch (IOException e) {
                        Log.e("ERROR", "Failed to copy asset file: " + filename, e);
                    } finally {
                        // Edit 3 (after MMs comment)
//                        in.close();
                        in = null;
//                        out.flush();
//                        out.close();
                        out = null;
                    }
                } else {
                    // Do something else on failure
                }
            }
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
        } else {
            // Something else is wrong. It may be one of many other states, but all we need
            // is to know is we can neither read nor write
        }
    }

    // Method used by copyAssets() on purpose to copy a file.
    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    //按日期排序
    public static File[] getFilesByDate(String fliePath) {
        File file = new File(fliePath);
        File[] fs = file.listFiles();
        Log.e("willz", "排序前");
        for (File f : fs) {
            Log.i("", f.getName() + " - " + new Date(f.lastModified()));
        }

        Arrays.sort(fs, new Comparator<File>() {
            public int compare(File f1, File f2) {
                long diff = f1.lastModified() - f2.lastModified();
                if (diff > 0)
                    return 1;
                else if (diff == 0)
                    return 0;
                else
                    return -1;
            }

            public boolean equals(Object obj) {
                return true;
            }

        });
        Log.e("willz", "排序后");
        for (File f : fs) {
            Log.i("", f.getName() + " - " + new Date(f.lastModified()));
        }
        return fs;
    }

    /**
     * 读取asset下的文件
     *
     * @param mContext
     * @param filePath
     * @return
     */
    public static String readAssetFile(Context mContext, String filePath) {
        try {
            InputStream is = mContext.getAssets().open(filePath);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String string = new String(buffer, "utf-8");
            return string;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * bitmap存储到本地文件夹中
     *
     * @param b
     * @param filepath
     * @return
     */
    public static void saveBitmap(Bitmap b, String filepath) {
        File file = new File(filepath);
        if (file.exists()) {
            return;
        }
        try {
            long dataTake = System.currentTimeMillis();
            FileOutputStream fout = new FileOutputStream(filepath);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 读取文件内容
     *
     * @param fileName
     * @return
     */
    public static String readFileContent(String fileName) {
        try {
            File urlFile = new File(fileName);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String str = "";
            String mimeTypeLine = null;
            while ((mimeTypeLine = br.readLine()) != null) {
                str = str + mimeTypeLine;
            }
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 复制文件
     *
     * @param oldPath
     * @param newPath
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.flush();
                fs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将字符串追加到文件中
     *
     * @param str
     * @param filePath
     */
    public static void saveFile(String str, String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(file, true);
            outStream.write(str.getBytes());
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getImageFromAssetsFile(AssetManager am, String fileName)
    {
        Bitmap image = null;
        try
        {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return image;

    }
}
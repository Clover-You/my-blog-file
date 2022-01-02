package io;

import java.io.*;

public class FileCopy {

  private String PATH;// 存储当前需要拷贝的File对象路径
  private int READER_BUFFER_SIZE = 10; // 设置读取的缓冲区大小

  public FileCopy(String path) {
    this.PATH = path;
  }

  public FileCopy(String path, int bufferSize) {
    this.PATH = path;
    this.READER_BUFFER_SIZE = bufferSize;
  }

  /*
   * @Param tarPath 目标路径
   * */
  public boolean copyTo(String tarPath) {
    File f = new File(tarPath);
    File PATH_F = new File(tarPath + "/" + new File(PATH).getName());
    if (f.exists()) {
      return handler(PATH, PATH_F.getAbsolutePath());
    }
    return false;
  }
  /*
  * 处理文件、文件夹
  * */
  private boolean handler(String source, String target) {
    new File(target).mkdirs(); // 每次进入需要创建，文件夹，因为只有两种情况，1：第一次进入。2：拷贝时遇到文件夹递归时
    File[] files = new File(source).listFiles();
    for (File file : files) {
      if (file.isFile())
        copyFileTo(file.getAbsolutePath(), target + "/" + file.getName());
      else if (file.isDirectory())
        handler(source + "/" + file.getName(), target + "/" + file.getName());
    }
    return true;
  }

  /*
   * 文件拷贝
   * @param sourcePath 待备份的文件路径
   * @param targetPath 目标路径
   * */
  private void copyFileTo(String sourcePath, String targetPath) {

    try (FileInputStream fis = new FileInputStream(sourcePath);
         FileOutputStream fos = new FileOutputStream(targetPath)) {
      byte[] bytes = new byte[1024 * 1024 * READER_BUFFER_SIZE];// 设置缓冲区大小，以MB计算
      int readCount = 0;// 每次写入缓冲区长度
      while ((readCount = fis.read(bytes)) > -1) {
        fos.write(bytes, 0, readCount); // 读多少写多少
      }
      fos.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
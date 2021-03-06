package cloud.unionj.generator;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author created by wubin
 * @version v0.1
 *   cloud.unionj.generator
 *  date 2020/11/26
 */
public class GeneratorUtils {

  public static String getOutputDir(String outputDir) {
    if (StringUtils.isBlank(outputDir)) {
      outputDir = System.getProperty("user.dir");
    } else {
      File file = new File(outputDir);
      if (!file.isAbsolute()) {
        outputDir = System.getProperty("user.dir") + File.separator + outputDir;
      }
      file = new File(outputDir);
      if (!file.exists()) {
        file.mkdirs();
      }
    }
    return outputDir;
  }

  @SneakyThrows
  public static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) {
    if (fileToZip.isDirectory()) {
      if (fileName.endsWith("/")) {
        zipOut.putNextEntry(new ZipEntry(fileName));
        zipOut.closeEntry();
      } else {
        zipOut.putNextEntry(new ZipEntry(fileName + "/"));
        zipOut.closeEntry();
      }
      File[] children = fileToZip.listFiles();
      for (File childFile : children) {
        zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
      }
      return;
    }
    FileInputStream fis = new FileInputStream(fileToZip);
    ZipEntry zipEntry = new ZipEntry(fileName);
    zipOut.putNextEntry(zipEntry);
    byte[] bytes = new byte[1024];
    int length;
    while ((length = fis.read(bytes)) >= 0) {
      zipOut.write(bytes, 0, length);
    }
    fis.close();
  }

  @SneakyThrows
  public static String generateFolder(String sourceFile, String outputFile) {
    FileOutputStream fos = new FileOutputStream(outputFile);
    ZipOutputStream zipOut = new ZipOutputStream(fos);
    File fileToZip = new File(sourceFile);

    try {
      zipFile(fileToZip, fileToZip.getName(), zipOut);
    } catch (Exception exception) {
      throw exception;
    } finally {
      zipOut.close();
      fos.close();
    }

    return outputFile;
  }

  public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
    File destFile = new File(destinationDir, zipEntry.getName());

    String destDirPath = destinationDir.getCanonicalPath();
    String destFilePath = destFile.getCanonicalPath();

    if (!destFilePath.startsWith(destDirPath + File.separator)) {
      throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
    }

    return destFile;
  }

  @SneakyThrows
  public static void unzip(String fileZip, File destDir) {
    byte[] buffer = new byte[1024];
    ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
    ZipEntry zipEntry = zis.getNextEntry();
    while (zipEntry != null) {
      File newFile = newFile(destDir, zipEntry);
      if (zipEntry.isDirectory()) {
        if (!newFile.isDirectory() && !newFile.mkdirs()) {
          throw new IOException("Failed to create directory " + newFile);
        }
      } else {
        // fix for Windows-created archives
        File parent = newFile.getParentFile();
        if (!parent.isDirectory() && !parent.mkdirs()) {
          throw new IOException("Failed to create directory " + parent);
        }

        // write file content
        FileOutputStream fos = new FileOutputStream(newFile);
        int len;
        while ((len = zis.read(buffer)) > 0) {
          fos.write(buffer, 0, len);
        }
        fos.close();
      }
      zipEntry = zis.getNextEntry();
    }
    zis.closeEntry();
    zis.close();

  }

}

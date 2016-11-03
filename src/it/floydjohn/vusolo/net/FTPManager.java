package it.floydjohn.vusolo.net;

import it.floydjohn.vusolo.settings.Setting;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by alessandro on 10/22/16.
 */

public class FTPManager {
    private static final String TEMP_FILE = ".temp";
    private static FTPManager __instance;
    private FTPClient ftpClient;

    private FTPManager() {}

    public static FTPManager getInstance() {
        if (__instance == null) __instance = new FTPManager();
        return __instance;
    }

    public void connect() throws IOException {
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(Setting.FTP_IP.v(), Integer.valueOf(Setting.FTP_PRT.v()));
            ftpClient.login(Setting.FTP_USR.v(), Setting.FTP_PWD.v());
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (IOException e) {
            throw new IOException("Error while connecting: " + e.getMessage());
        }
    }

    public void disconnect() {
        try {
            if (ftpClient != null && ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
                ftpClient = null;
            }
            Files.deleteIfExists(Paths.get(TEMP_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendFile(String content) throws IOException {
        try {
            if (ftpClient == null) throw new IOException("ftpClient null");


            writeTempFile(content);
            File localFile = new File(TEMP_FILE);

            InputStream inputStream = new FileInputStream(localFile);

            boolean success = ftpClient.storeFile(Setting.FTP_FIL.v(), inputStream);
            inputStream.close();
            if (!success) throw new IOException("Failed to send file");
        } catch (IOException e) {
            throw new IOException("Error while sending file: "+e.getMessage());
        }
    }

    public String receiveFile() throws IOException {
        try {
            if (ftpClient == null) throw new IOException("ftpClient null");
            File downloadFile = new File(TEMP_FILE);
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
            boolean success = ftpClient.retrieveFile(Setting.FTP_FIL.v(), outputStream);
            outputStream.close();

            if (success) return readTempFile();
            else throw new IOException("Failed to retrieve file");
        } catch (IOException e) {
            throw new IOException("Error while receiving file: "+e.getMessage());
        }
    }

    private String readTempFile() throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(TEMP_FILE));
        return new String(encoded);
    }

    private void writeTempFile(String content) throws IOException {
        Files.write(Paths.get(TEMP_FILE), content.getBytes());
    }
}

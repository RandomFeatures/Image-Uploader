package cal;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class FileUtils {
	
	private static Logger logger = Logger.getLogger(FileUtils.class);
	private AmazonS3 m_s3client;
	private final String m_Workspace;

	public FileUtils() {
		super();
		m_Workspace = System.getProperty("user.home") + "/version2";
		(new File(m_Workspace)).mkdirs();
		try {
			m_s3client = new AmazonS3Client(new PropertiesCredentials(FileUtils.class.getResourceAsStream("/AwsCredentials.properties")));
		} catch (IOException e) {
			logger.error(e, e);
		}
	}

	public String downloadFile(URL url) {
		String rtn = "";

		try {

			
			String filename = url.getFile();

			InputStream in = new BufferedInputStream(url.openStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while (-1 != (n = in.read(buf))) {
				out.write(buf, 0, n);
			}
			out.close();
			in.close();
			byte[] response = out.toByteArray();

			FileOutputStream fos = new FileOutputStream(m_Workspace + filename);
			fos.write(response);
			fos.close();

			rtn = m_Workspace + filename;
		} catch (MalformedURLException e) {
			logger.error(e, e);
		} catch (IOException e) {
			logger.error(e, e);
		}

		return rtn;
	}

	public boolean S3FileUpload(String fileName, String keyName, String bucketName) {
		
		 boolean rtn = false;
		try {
			File file = new File(fileName);
			logger.debug("keyName: " + keyName);
			logger.debug("bucketName: " + bucketName);
			m_s3client.putObject(new PutObjectRequest(bucketName, keyName, file).withCannedAcl(CannedAccessControlList.PublicRead));
			rtn = true;
		} catch (AmazonServiceException ase) {
			logger.error(ase, ase);
		} catch (AmazonClientException ace) {
			logger.error(ace, ace);
		}
		return rtn;
	}
	
	public void deleteTempFile(String fileName) {
		//delete the temp file
		File f1 = new File(fileName);
		f1.delete();

	}
}

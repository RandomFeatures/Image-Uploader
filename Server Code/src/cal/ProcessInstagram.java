package cal;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.jinstagram.Instagram;
import org.jinstagram.entity.media.MediaInfoFeed;
import org.jinstagram.exceptions.InstagramException;

import dal.dao.DomainImagesDao;
import dal.dao.DomainSettingsDao;
import dal.dto.DomainImageSubmit;
import dal.dto.DomainImageSubmit_Attr;
import dal.dto.DomainImages;
import dal.dto.DomainSettings;
import dal.exceptions.DomainImagesException;
import dal.exceptions.DomainSettingsException;

public class ProcessInstagram {

	private static Logger logger = Logger.getLogger(SubmitWorker.class);
	private final FileUtils m_FileUtil;
	private final Instagram m_Instagram;
	private final DomainSettingsDao m_dalSettings;
	private DomainImagesDao m_dalImages;

	public ProcessInstagram() {
		super();
		m_FileUtil = new FileUtils();
		m_Instagram = new Instagram("6804288d444949eea536868981f72e4a");
		m_dalSettings = new DomainSettingsDao();
		m_dalImages = new DomainImagesDao();
	}

	public boolean process(DomainImageSubmit dto) {
		boolean rtn = false;

		try {
			if (!isAlreadySubmitted(dto)) {

				DomainSettings m_Setting = m_dalSettings.findMaster();
				Long instID = Long.parseLong(dto.getSourceID().split("_")[0]);
				MediaInfoFeed mediaInfoFeed = m_Instagram.getMediaInfo(instID);

				// Get the source URL
				String sourceURL = mediaInfoFeed.getData().getImages().getStandardResolution().getImageUrl();
				URL url = new URL(sourceURL);

				// Download the file and get back the temp name
				String tmpFileName = m_FileUtil.downloadFile(url);

				// key, image and bucket name for S3
				String keyName = "intstaimage" + Integer.toString((Math.abs(instID.hashCode() % 10))) + url.getFile();
				String bucket = m_Setting.getBucket();
				String imageURL = m_Setting.getDownLoadURL() + bucket + "/" + keyName;
				String caption = "";

				try {
					caption = mediaInfoFeed.getData().getCaption().getText();
				} catch (Exception e) {
					logger.error(e, e);
				}

				// Store the image is S3
				if (m_FileUtil.S3FileUpload(tmpFileName, keyName, bucket)) {
					// Update the images db
					DomainImages imgDto = new DomainImages.Builder()
																.withCaption(caption)
																.withTags(mediaInfoFeed.getData().getTags())
																.withSource(DomainImageSubmit_Attr.Instagram)
																.withSourceURL(sourceURL)
																.withSourceID(dto.getSourceID())
																.withImageURL(imageURL)
																.withUserID(dto.getUserID())
																.build();

					m_dalImages.insert(imgDto);
					rtn = true;

					// delete temp file now that we are done with it;
					m_FileUtil.deleteTempFile(tmpFileName);
				}
			}
		} catch (InstagramException e) {
			logger.error(e, e);
		} catch (MalformedURLException e) {
			logger.error(e, e);
		} catch (DomainSettingsException e) {
			logger.error(e, e);
		} catch (DomainImagesException e) {
			logger.error(e, e);
		}

		return rtn;
	}

	private boolean isAlreadySubmitted(DomainImageSubmit dto) {
		boolean rtn = false;

		DomainImages imgDto;
		try {
			imgDto = m_dalImages.findImageBySource(dto.getSource(), dto.getSourceID());
			if (imgDto != null)
				rtn = true;

		} catch (DomainImagesException e) {
			logger.error(e, e);
		}

		return rtn;
	}
}

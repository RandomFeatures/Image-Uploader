package cal;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import util.Const;
import dal.dao.DomainImagesDao;
import dal.dao.DomainTagsDao;
import dal.dto.DomainImages;
import dal.dto.DomainTags;
import dal.dto.json.DomainImagesJSON;
import dal.exceptions.DomainImagesException;

public class ImageCache {

	private final DomainImagesDao m_dalImages;
	private final DomainTagsDao m_dalTags;
	private MemcachedClient m_Cache;
	private static Logger logger = Logger.getLogger(ImageCache.class);
	
	public ImageCache(String servers) {
		super();
			// m_Cache = new MemcachedClient(new InetSocketAddress("hostname",
			// 111));
			try {
				
				if(servers.trim().indexOf(" ") > 0)
					m_Cache = new MemcachedClient(AddrUtil.getAddresses(servers));
				else {
					String s[] = servers.split(":");
					int port = Integer.parseInt(s[1]);
					m_Cache = new MemcachedClient(new InetSocketAddress(s[0], port));
				}
					

			} catch (IOException e) {
				logger.error(e, e);
			}
			m_dalImages = new DomainImagesDao();
			m_dalTags = new DomainTagsDao();

	}

	public void topRanked() {

		String json;
		List<DomainImages> imageList = Collections.emptyList();
		try {

			imageList = m_dalImages.findTopRanked();

			json = DomainImagesJSON.getImages(imageList, Const.DescTopRanked);
			m_Cache.set(Const.TopRankedImages, 3600, json);

		} catch (DomainImagesException e) {
			logger.error(e, e);
		} catch (Exception e) {
			logger.error(e,e);
		} finally {
			imageList.clear();
			imageList = null;
		}
	}

	public void newest() {

		String json;

		List<DomainImages> imageList = Collections.emptyList();

		try {

			imageList = m_dalImages.findNewest();

			json = DomainImagesJSON.getImages(imageList, Const.DescNewest);
			m_Cache.set(Const.NewestImages, 3600, json);

		} catch (DomainImagesException e) {
			logger.error(e, e);
		} catch (Exception e) {
			logger.error(e,e);
		} finally {

			imageList.clear();
			imageList = null;
		}
	}

	public void topRankedByTag(String tag) {

		String json;

		List<DomainImages> imageList = Collections.emptyList();

		try {

			imageList = m_dalImages.findTopRankedByTag(tag);

			json = DomainImagesJSON.getImages(imageList, Const.DescTopRankedByTag);
			m_Cache.set(Const.TopRankedByTag + tag, 3600, json);

		} catch (DomainImagesException e) {
			logger.error(e, e);
		} catch (Exception e) {
			logger.error(e,e);
		} finally {

			imageList.clear();
			imageList = null;
		}
	}

	public void newestByTag(String tag) {

		String json;

		List<DomainImages> imageList = Collections.emptyList();

		try {

			imageList = m_dalImages.findNewestByTag(tag);

			json = DomainImagesJSON.getImages(imageList, Const.DescNewestByTag);
			m_Cache.set(Const.NewestByTag + tag, 3600, json);

		} catch (DomainImagesException e) {
			logger.error(e, e);
		} catch (Exception e) {
			logger.error(e,e);
		} finally {
			imageList.clear();
			imageList = null;
		}

	}

	public void topRankedByTopTag() {

		List<DomainTags> tagList = Collections.emptyList();
		int tagCount = 0;
		try {
			tagList = m_dalTags.findTopRanked();

			for (DomainTags tag : tagList) {
				m_Cache.set("tag" + Integer.toString(tagCount), 3600, tag.getID());
				tagCount++;
				topRankedByTag(tag.getID());
			}
		} catch (Exception e) {
			logger.error(e,e);
		} finally {
			tagList.clear();
			tagList = null;
		}

	}

	public void newestByTopTag() {

		List<DomainTags> tagList = Collections.emptyList();
		int tagCount = 0;
		try {
			tagList = m_dalTags.findTopRanked();

			for (DomainTags tag : tagList) {
				m_Cache.set("tag" + Integer.toString(tagCount), 3600, tag.getID());
				newestByTag(tag.getID());
				tagCount++;
			}
		} catch (Exception e) {
			logger.error(e,e);
		} finally {
			tagList.clear();
			tagList = null;
		}

	}

	public void topTags() {

		String json;

		List<DomainTags> tagList = Collections.emptyList();

		try {
			tagList = m_dalTags.findTopRanked();

			json = DomainImagesJSON.getTagList(tagList);
			m_Cache.set(Const.TopTags, 3600, json);

		} catch (Exception e) {
			logger.error(e,e);
		} finally {
			tagList.clear();
			tagList = null;
		}

	}
}

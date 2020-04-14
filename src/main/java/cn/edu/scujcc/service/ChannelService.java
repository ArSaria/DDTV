package cn.edu.scujcc.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.scujcc.api.ChannelController;
import cn.edu.scujcc.dao.ChannelRepository;
import cn.edu.scujcc.model.Channel;

@Service
public class ChannelService {
	@Autowired
	private ChannelRepository repo;
	public static final Logger logger = LoggerFactory.getLogger(ChannelService.class);

	//��ȡ����Ƶ��
	public List<Channel> getAllChannels(){
		return repo.findAll();
	}
	
	
	/**
	 * ��ȡһ��Ƶ��
	 * 
	 * @param id 
	 * @return
	 */
	public Channel getChannel(String channelId){
		Optional<Channel> result = repo.findById(channelId);
		
		if (result.isPresent()) {
			return result.get();
		} else {
			return null;
		}
		
	}
	
	public List<Channel> searchByTitle(String title) {
		return repo.findByTitle(title);
	}
	
	public List<Channel> searchByQuality(String quality) {
		return repo.findByQuality(quality);
	}
	
	/**
	 * ɾ��
	 * @param id
	 * @return
	 */
	public boolean deleteChannel(String channelId) {
		boolean result = true;
		repo.deleteById(channelId);
		
		return result;
	}
	
	//����
	public Channel createChannel(Channel c) {
		return repo.save(c);
	}
	
	/**
	 * ����ָ��Ƶ����Ϣ��
	 * @param c �µ�Ƶ����Ϣ�����ڸ����Ѵ��ڵ�ͬһƵ����
	 * @return ���º��Ƶ����Ϣ
	 */
	public Channel updateChannel(Channel c) {
		Channel saved = getChannel(c.getId());
		if (saved != null) {
			if (c.getTitle() != null) {
				saved.setTitle(c.getTitle());
			}
			if (c.getQuality() != null) {
				saved.setQuality(c.getQuality());
			}
			if (c.getUrl() != null) {
				saved.setUrl(c.getUrl());
			}
			if (c.getComments() != null) {
				saved.getComments().addAll(c.getComments());
			}else {
				saved.setComments(c.getComments());
			}
		}
		logger.debug(saved.toString());
		return repo.save(saved);
	}
	
	/**
	 * �ҳ����������۵�Ƶ��
	 * 
	 * @return Ƶ���б�
	 */
	public List<Channel> getLatestCommentsChannel() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime today = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 0, 0);
		return repo.findByCommentsDtAfter(today);
	}
}

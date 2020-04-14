package cn.edu.scujcc.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.scujcc.model.Channel;
import cn.edu.scujcc.service.ChannelService;

@RestController
@RequestMapping("/channel")
public class ChannelController {
	public static final Logger logger = LoggerFactory.getLogger(ChannelController.class);
	
	@Autowired
	private ChannelService service;
	
	@GetMapping
	public List<Channel> getAllChannel() {
		logger.info("���ڲ�������Ƶ����Ϣ...");
		List<Channel> results = service.getAllChannels();
		logger.debug("����Ƶ���������ǣ�" + results.size());
		
		return results;
	}
	
	
	@GetMapping("/{id}")
	public Channel getChannel(@PathVariable String id) {
		Channel c = service.getChannel(id);
		if (c != null) {
			return c;
		}else {
			logger.error("�Ҳ���ָ����Ƶ����");
			return null;
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteChannel(@PathVariable String id){
		System.out.println("����ɾ��Ƶ����id="+id);
		boolean result = service.deleteChannel(id);
		if (result) {
			return ResponseEntity.ok().body("ɾ���ɹ�");
		}else {
			return ResponseEntity.ok().body("ɾ��ʧ��");
		}
	}
	
	@PostMapping
	public Channel createChannel(@RequestBody Channel c) {
		System.out.println("�����½�Ƶ����Ƶ�����ݣ�" + c);
		Channel saved = service.createChannel(c);
		return saved;
	}
	
	@PutMapping
	public Channel updateChannel(@RequestBody Channel c) {
		System.out.println("��������Ƶ����Ƶ�����ݣ�" + c);
		Channel updated = service.updateChannel(c);
		return updated;
	}
	
	@GetMapping("/t/{title}")
	public List<Channel> searchByTitle(@PathVariable String title) {
		System.out.println("���ڸ��ݱ��⣺" + title + " ����Ƶ����");
		return service.searchByTitle(title);
	}
	
	@GetMapping("/q/{quality}")
	public List<Channel> searchByQuality(@PathVariable String quality) {
		System.out.println("���ڸ��������ȣ�" + quality + " ����Ƶ����");
		return service.searchByQuality(quality);
	}
	
	@GetMapping("/hot")
	public List<Channel> getHotChannels() {
		return service.getLatestCommentsChannel();
	}
	
}

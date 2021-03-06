package com.doppler.blog.Service;

import com.doppler.blog.mappers.HashtagMapper;
import com.doppler.blog.models.Hashtag;
import com.doppler.blog.utils.DateFormatter;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.doppler.blog.GlobalConstants.INSERTHASHTAG;

/**
 * Created by doppler on 2016/5/30.
 */
@Service
public class HashtagService {

    @Resource
    private HashtagMapper hashtagMapper;

    private static final String CACHE_TAGS = "tags";

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(HashtagService.class);

    @CacheEvict(value = CACHE_TAGS, allEntries = true)
    @Transactional
    public Hashtag findOrCreateByName(String name){
        Hashtag hashtag = hashtagMapper.findTagByName(name);
        if(hashtag == null || hashtag.getName().isEmpty()) {
            hashtag = new Hashtag(name);
           hashtag.setCreatedAt(DateFormatter.format(new Date()));
          hashtagMapper.insertHashTag(hashtag);
            logger.info(INSERTHASHTAG.value());
        }
        return hashtag;
    }


    public Hashtag findByName(String tagName){
        return hashtagMapper.findTagByName(tagName);
    }


    @Cacheable(value = CACHE_TAGS)
    public List<Hashtag> findAll(){
        logger.info("not caches,get all tags from db");
        return hashtagMapper.findAllTags();
    }


    @CacheEvict(value = CACHE_TAGS, allEntries = true)
    @Transactional
    public void deleteTag(Long hashtagId) {

        hashtagMapper.deleteTagById(hashtagId);
    }


    @Transactional
    public void savePostAndTags(Long hashtagId, Long postId) {
        hashtagMapper.savePostAndTags(hashtagId,postId);
    }
}

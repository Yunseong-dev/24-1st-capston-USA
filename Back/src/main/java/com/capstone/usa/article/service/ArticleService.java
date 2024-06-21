package com.capstone.usa.article.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.capstone.usa.article.dto.CreateArticleDto;
import com.capstone.usa.article.model.Article;
import com.capstone.usa.article.repository.ArticleRepository;
import com.capstone.usa.user.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public ArticleService(ArticleRepository articleRepository, AmazonS3 amazonS3) {
        this.articleRepository = articleRepository;
        this.amazonS3 = amazonS3;
    }

    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    public void createArticle(User user, CreateArticleDto dto, MultipartFile image) throws IOException {
        String originalFilename = image.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(image.getSize());
        metadata.setContentType(image.getContentType());

        amazonS3.putObject(bucket, originalFilename, image.getInputStream(), metadata);
        String imgUrl = amazonS3.getUrl(bucket, originalFilename).toString();

        LocalDateTime now = LocalDateTime.now();

        Article article = new Article(
                null,
                dto.getTitle(),
                dto.getContent(),
                user,
                imgUrl,
                now,
                now
        );
        articleRepository.save(article);
    }

    public Article getArticle(Long id) {
        return articleRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new IllegalArgumentException("게시물이 없습니다."));
    }
}
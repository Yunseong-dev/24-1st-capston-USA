package com.capstone.usa.article.service;

import com.capstone.usa.article.dto.ArticleDto;
import com.capstone.usa.article.model.Article;
import com.capstone.usa.article.repository.ArticleRepository;
import com.capstone.usa.auth.model.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final S3Service s3Service;

    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    @Transactional
    public Article getArticle(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 없습니다."));
    }

    public void createArticle(User user, ArticleDto dto, MultipartFile image) throws IOException {
        String imgUrl = s3Service.uploadImage(image);

        Article article = new Article(
                null,
                dto.getTitle(),
                dto.getContent(),
                user,
                imgUrl,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        articleRepository.save(article);
    }

    public void modifyArticle(Long id, User user, ArticleDto dto) {
        Article article = getArticle(id);

        if (articleRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("존재하지 않는 게시물입니다");
        }
        if (!article.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("내 글만 수정 가능합니다.");
        }

        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setUpdatedAt(LocalDateTime.now());
        articleRepository.save(article);
    }

    public void deleteArticle(Long id, User user) {
        Article article = getArticle(id);

        if (articleRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("존재하지 않는 게시물입니다");
        }
        if (!article.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("내 글만 삭제 가능합니다.");
        }
        s3Service.deleteImage(article.getImgUrl());

        articleRepository.delete(article);
    }
}

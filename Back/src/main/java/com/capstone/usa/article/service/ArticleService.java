package com.capstone.usa.article.service;

import com.capstone.usa.article.dto.ArticleDto;
import com.capstone.usa.article.model.Article;
import com.capstone.usa.article.repository.ArticleRepository;
import com.capstone.usa.auth.model.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final S3Service s3Service;

    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    @Transactional
    public ResponseEntity<Article> getArticle(Long id) {
        Optional<Article> oArticle = articleRepository.findById(id);

        if (oArticle.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(oArticle.get());
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

    private ResponseEntity<?> checkArticleOwnership(Long id, User user) {
        Optional<Article> oArticle = articleRepository.findById(id);

        if (oArticle.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Article article = oArticle.get();
        if (!article.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("내 글만 수정할 수 있습니다");
        }

        return ResponseEntity.ok(article);
    }

    public ResponseEntity<?> modifyArticle(Long id, User user, ArticleDto dto) {
        ResponseEntity<?> response = checkArticleOwnership(id, user);
        if (response.getStatusCode() != HttpStatus.OK) {
            return response;
        }

        Article article = (Article) response.getBody();
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setUpdatedAt(LocalDateTime.now());
        articleRepository.save(article);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deleteArticle(Long id, User user) {
        ResponseEntity<?> response = checkArticleOwnership(id, user);
        if (response.getStatusCode() != HttpStatus.OK) {
            return response;
        }

        Article article = (Article) response.getBody();
        s3Service.deleteImage(article.getImgUrl());
        articleRepository.delete(article);

        return ResponseEntity.ok().build();
    }
}

package com.capstone.usa.article.service;

import com.capstone.usa.article.dto.CreateArticleDto;
import com.capstone.usa.article.model.Article;
import com.capstone.usa.article.repository.ArticleRepository;
import com.capstone.usa.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ArticleService {
    private ArticleRepository articleRepository;

    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    public void createArticle(User user, CreateArticleDto dto, MultipartFile image) throws IOException {
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\imgFile";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + image.getOriginalFilename();
        File saveFile = new File(path , fileName);
        image.transferTo(saveFile);

        LocalDateTime now = LocalDateTime.now();

        Article article = new Article(
                0,
                dto.getTitle(),
                dto.getContent(),
                user,
                fileName,
                path,
                now,
                now
        );
        articleRepository.save(article);
    }

    public Article getArticle(int id) {
        return articleRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new IllegalArgumentException("게시물이 없습니다."));
    }
}

package com.zlatoust.controllers.contents;

import com.zlatoust.configuration.ApiConstants;
import com.zlatoust.models.News;
import com.zlatoust.services.ImageService;
import com.zlatoust.services.contents.AnnouncementService;
import com.zlatoust.services.contents.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.API_PATH_NEWS)
public class NewsController extends AbstractContentController<News> {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService, ImageService imageService) {
        super(imageService);
        this.newsService = newsService;
    }

    @Override
    protected NewsService getService() {
        return newsService;
    }
}

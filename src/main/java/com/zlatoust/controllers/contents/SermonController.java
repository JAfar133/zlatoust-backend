package com.zlatoust.controllers.contents;

import com.zlatoust.configuration.ApiConstants;
import com.zlatoust.models.Sermon;
import com.zlatoust.services.ImageService;
import com.zlatoust.services.contents.NewsService;
import com.zlatoust.services.contents.SermonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.API_PATH_SERMON)
public class SermonController extends AbstractContentController<Sermon> {

    private final SermonService sermonService;

    @Autowired
    public SermonController(SermonService sermonService, ImageService imageService) {
        super(imageService);
        this.sermonService = sermonService;
    }

    @Override
    protected SermonService getService() {
        return sermonService;
    }
}

package com.zlatoust.controllers.contents;


import com.zlatoust.configuration.ApiConstants;
import com.zlatoust.models.Announcement;
import com.zlatoust.services.ImageService;
import com.zlatoust.services.contents.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.API_PATH_ANNOUNCEMENT)
public class AnnouncementController extends AbstractContentController<Announcement> {

    private final AnnouncementService announcementService;

    @Autowired
    public AnnouncementController(AnnouncementService announcementService, ImageService imageService) {
        super(imageService);
        this.announcementService = announcementService;
    }

    @Override
    protected AnnouncementService getService() {
        return announcementService;
    }
}


package com.interquest.backend.controller;

import com.interquest.backend.service.ScraperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScraperController {

    private final ScraperService scraperService;

    @GetMapping("/run-scraper")
    public String runScraper() {
        int saved = scraperService.fetchAndSaveOpportunities();
        return "Scraper finished! " + saved + " new records saved.";
    }
}

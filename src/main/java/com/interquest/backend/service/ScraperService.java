package com.interquest.backend.service;

import com.interquest.backend.model.Opportunity;
import com.interquest.backend.repository.OpportunityRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScraperService {

    private final OpportunityRepository opportunityRepository;

    @Value("${scraper.target.urls}")
    private List<String> targetUrls;

    @Transactional
    public int fetchAndSaveOpportunities() {
        int totalSavedCount = 0;

        for (String url : targetUrls) {
            totalSavedCount += scrapeSingleUrl(url);
        }

        System.out.println(STR."Multi-site scraping complete. Total new records saved: \{totalSavedCount}");
        return totalSavedCount;
    }


    private int scrapeSingleUrl(String targetUrl) {
        System.out.println(STR."Starting web scraping on: \{targetUrl}");
        List<Opportunity> newOpportunities = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(targetUrl)
                    .userAgent("InterQuest-Scraper/1.0")
                    .timeout(10000)
                    .get();
            Elements listings = doc.select(".opportunity-listing");

            for (Element element : listings) {
                String title = element.select(".title").text();
                String description = element.select(".description").text();
                String category = element.select(".category").text();
                String deadlineStr = element.select(".deadline").attr("data-date");

                if (title != null && !title.isEmpty()) {
                    Opportunity opp = new Opportunity();
                    opp.setTitle(title);
                    opp.setDescription(description.length() > 500 ? description.substring(0, 500) + "..." : description);
                    opp.setCategory(category);
                    try {
                        opp.setDeadline(LocalDate.parse(deadlineStr));
                    } catch (Exception e) {
                        opp.setDeadline(LocalDate.now().plusYears(1));
                    }
                    newOpportunities.add(opp);
                }
            }

            opportunityRepository.saveAll(newOpportunities);
            return newOpportunities.size();

        } catch (IOException e) {
            System.err.println("Scraping failed for URL " + targetUrl + ": " + e.getMessage());
            return 0;
        }
    }
}
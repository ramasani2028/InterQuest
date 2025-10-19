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

        System.out.println("Multi-site scraping complete. Total new records saved: " + totalSavedCount);
        return totalSavedCount;
    }


    private int scrapeSingleUrl(String targetUrl) {
        System.out.println("Starting web scraping on: " + targetUrl);
        List<Opportunity> newOpportunities = new ArrayList<>();

        try {
            // 1. Fetch data from the external source
            Document doc = Jsoup.connect(targetUrl)
                    .userAgent("InterQuest-Scraper/1.0")
                    .timeout(10000)
                    .get();

            // NOTE: Selector logic may need to be different for each site!
            Elements listings = doc.select(".opportunity-listing");

            // 2. Parse and Normalize Data (U3)
            for (Element element : listings) {
                // This logic block remains the same, converting Jsoup data to Opportunity entities
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

            // 3. Save new opportunities (U3 - Opportunities stored in system)
            opportunityRepository.saveAll(newOpportunities);
            return newOpportunities.size();

        } catch (IOException e) {
            // Log the error for the specific URL that failed
            System.err.println("Scraping failed for URL " + targetUrl + ": " + e.getMessage());
            // Continue to the next URL by returning 0
            return 0;
        }
    }
}
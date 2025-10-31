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
        int totalSaved = 0;

        for (String url : targetUrls) {
            System.out.println("🔗 Scraping: " + url);

            if (url.contains("internshala.com")) {
                totalSaved += scrapeInternshala(url);
            } else {
                totalSaved += scrapeGeneric(url);
            }
        }

        System.out.println("✅ Multi-site scraping complete. Total new records saved: " + totalSaved);
        return totalSaved;
    }

    // -------------------------------
    // 🔹 INTERN SHALA SCRAPER
    // -------------------------------
    private int scrapeInternshala(String baseUrl) {
        System.out.println("🧠 Starting Internshala scraping...");

        int totalSaved = 0;
        for (int page = 1; page <= 5; page++) {
            String pageUrl = baseUrl + "/page-" + page;
            System.out.println("📄 Scraping Internshala page: " + pageUrl);

            try {
                Document doc = Jsoup.connect(pageUrl)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                        .timeout(15000)
                        .get();

                // Extract embedded JSON data (internship_list)
                Element jsonScript = doc.selectFirst("script#internship_list");
                if (jsonScript == null) {
                    System.out.println("⚠️ No JSON data found on this page.");
                    continue;
                }

                String jsonData = jsonScript.html();
                List<Opportunity> opportunities = parseInternshalaJson(jsonData);

                opportunityRepository.saveAll(opportunities);
                System.out.println("💾 Saved " + opportunities.size() + " internships from this page.");
                totalSaved += opportunities.size();

            } catch (IOException e) {
                System.err.println("❌ Error fetching page " + pageUrl + ": " + e.getMessage());
            }
        }

        System.out.println("✅ Scraping complete. Total saved: " + totalSaved);
        return totalSaved;
    }

    // -------------------------------
    // 🔹 PARSE JSON (Internshala embedded data)
    // -------------------------------
    private List<Opportunity> parseInternshalaJson(String jsonData) {
        List<Opportunity> opportunities = new ArrayList<>();

        // Extract internship blocks (each contains "name", "hiringOrganization", etc.)
        String[] items = jsonData.split("\\{\\s*\"@type\"\\s*:\\s*\"Internship\"");

        for (String item : items) {
            String title = extractJsonField(item, "\"name\"");
            String company = extractJsonField(item, "\"hiringOrganization\"");
            String location = extractJsonField(item, "\"addressLocality\"");

            if (title != null && !title.isEmpty()) {
                Opportunity opp = new Opportunity();
                opp.setTitle(title);
                opp.setDescription("Company: " + company + ", Location: " + location);
                opp.setCategory("Internship");
                opp.setDeadline(LocalDate.now().plusWeeks(2));
                opportunities.add(opp);
            }
        }

        return opportunities;
    }

    // Utility: Extracts a value from JSON text by key name
    private String extractJsonField(String json, String key) {
        try {
            int start = json.indexOf(key);
            if (start == -1) return null;
            start = json.indexOf(":", start) + 1;
            int firstQuote = json.indexOf("\"", start);
            int secondQuote = json.indexOf("\"", firstQuote + 1);
            if (firstQuote == -1 || secondQuote == -1) return null;
            return json.substring(firstQuote + 1, secondQuote).trim();
        } catch (Exception e) {
            return null;
        }
    }

    // -------------------------------
    // 🔹 GENERIC SCRAPER (for other URLs)
    // -------------------------------
    private int scrapeGeneric(String targetUrl) {
        System.out.println("🌐 Generic scraping for: " + targetUrl);
        List<Opportunity> newOpportunities = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(targetUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .timeout(10000)
                    .get();

            Elements titles = doc.select("h2, h3, .title, .job-title");

            for (Element t : titles) {
                String title = t.text();
                if (title.length() > 15 && title.length() < 120) {
                    Opportunity opp = new Opportunity();
                    opp.setTitle(title);
                    opp.setDescription("Found on: " + targetUrl);
                    opp.setCategory("General");
                    opp.setDeadline(LocalDate.now().plusMonths(1));
                    newOpportunities.add(opp);
                }
            }

            opportunityRepository.saveAll(newOpportunities);
            System.out.println("💾 Saved " + newOpportunities.size() + " opportunities from " + targetUrl);
            return newOpportunities.size();

        } catch (IOException e) {
            System.err.println("⚠️ Scraping failed for URL " + targetUrl + ": " + e.getMessage());
            return 0;
        }
    }
}

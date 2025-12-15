package com.example.outsourcingprojects.domain.search.controller;

import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import com.example.outsourcingprojects.domain.search.dto.SearchResponse;
import com.example.outsourcingprojects.domain.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    // 통합 검색
    @GetMapping("/api/search")
    public GlobalResponse<SearchResponse> searchHandler(@RequestParam String query) {

        SearchResponse result = searchService.search(query);

        return GlobalResponse.success("검색 성공", result);
    }
}
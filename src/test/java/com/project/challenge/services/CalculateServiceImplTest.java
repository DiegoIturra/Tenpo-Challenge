package com.project.challenge.services;

import com.project.challenge.models.entities.History;
import com.project.challenge.services.interfaces.ExternalPercentageService;
import com.project.challenge.services.interfaces.HistoryAsyncService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CalculateServiceImplTest {

    @InjectMocks
    private CalculateServiceImpl calculateService;

    @Mock
    private ExternalPercentageService externalPercentageService;

    @Mock
    private HistoryAsyncService historyAsyncService;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    private final int num1 = 10;
    private final int num2 = 20;

    @Test
    @DisplayName("Must calculate correctly when external service works")
    void shouldCalculateSuccessfullyWhenExternalServiceWorks() {
        double percentageValue = 5.0;
        double expectedResult = (num1 + num2) + percentageValue;

        when(externalPercentageService.getPercentage()).thenReturn(percentageValue);
        when(cacheManager.getCache("percentageCache")).thenReturn(cache);

        Double currentResult = calculateService.calculate(num1, num2);

        assertEquals(expectedResult, currentResult, "Result must be correct");

        //Verify that external dependencies are called in mocked services
        verify(externalPercentageService, times(1)).getPercentage();
        verify(cacheManager, times(1)).getCache(anyString());
        verify(cache, times(1)).put(eq("percentage"), eq(percentageValue));
        verify(historyAsyncService, times(1)).saveHistoryAsync(any(History.class));
    }

    @Test
    @DisplayName("Must use cache value when external service fails")
    void shouldCalculateSuccessfullyFallbackToCache() {
        double cachedPercentage = 2.5;
        double expectedResult = (num1 + num2) + cachedPercentage;

        when(externalPercentageService.getPercentage()).thenThrow(new RuntimeException("External service failed"));

        when(cacheManager.getCache("percentageCache")).thenReturn(cache);
        when(cache.get("percentage", Double.class)).thenReturn(cachedPercentage);

        Double currentResult = calculateService.calculate(num1, num2);

        assertEquals(expectedResult, currentResult, "Must use cache value for calculate");

        //Verify that external dependencies are called in mocked services
        verify(externalPercentageService, times(1)).getPercentage();
        verify(cacheManager, times(1)).getCache("percentageCache");
        verify(cache, times(1)).get("percentage", Double.class);
        verify(historyAsyncService, times(1)).saveHistoryAsync(any(History.class));
    }

    @Test
    @DisplayName("Must throw a ResponseStatusException when external service fails and cache is empty")
    void shouldFailureWhenExternalServiceFailsAndCacheIsEmpty() {
        when(externalPercentageService.getPercentage()).thenThrow(new RuntimeException("External service failed"));

        when(cacheManager.getCache("percentageCache")).thenReturn(cache);
        when(cache.get("percentage", Double.class)).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            calculateService.calculate(num1, num2);
        }, "Must throw ResponseStatusException.");

        assertEquals(500, exception.getStatusCode().value(), "Status code must be 500");

        //Verify that external dependencies are called in mocked services
        verify(externalPercentageService, times(1)).getPercentage();
        verify(cacheManager, times(1)).getCache("percentageCache");
        verify(cache, times(1)).get("percentage", Double.class);
        verify(historyAsyncService, times(1)).saveHistoryAsync(any(History.class));
    }
}

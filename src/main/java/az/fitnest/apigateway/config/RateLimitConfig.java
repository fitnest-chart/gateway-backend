package az.fitnest.apigateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "fitnest.rate-limit")
public class RateLimitConfig {

    private Map<String, Integer> limits = new HashMap<>();

    public RateLimitConfig() {
        limits.put("admin.write", 30);
        limits.put("checkout.write", 10);
        limits.put("general.write", 60);
        limits.put("general.read", 300);
    }

    public int getLimit(String key) {
        return limits.getOrDefault(key, 300);
    }

    public boolean isCheckoutEndpoint(String path) {
        return path.contains("/checkout") ||
               path.contains("/payments") ||
               path.contains("/orders") ||
               path.contains("/subscriptions");
    }

    public String getReadCategory(String path) {
        if (path.contains("/gyms") || path.contains("/catalog") || path.contains("/trainers")) {
            return "CATALOG_GET";
        } else if (path.contains("/search") || path.contains("/filter")) {
            return "SEARCH_GET";
        } else if (path.startsWith("/api/v1/auth/") || path.startsWith("/api/v1/admin/")) {
            return "AUTH_GET";
        } else {
            return "GENERAL_GET";
        }
    }

    public String getRateLimitKey(String path, String method) {
        boolean isRead = "GET".equals(method);
        boolean isWrite = Arrays.asList("POST", "PUT", "DELETE", "PATCH").contains(method);

        if (path.startsWith("/api/v1/admin/") && isWrite) {
            return "admin.write";
        } else if (isCheckoutEndpoint(path) && isWrite) {
            return "checkout.write";
        } else if (isWrite) {
            return "general.write";
        } else if (isRead) {
            return "general.read";
        }

        return "general.read";
    }

    public Map<String, Integer> getLimits() {
        return limits;
    }

    public void setLimits(Map<String, Integer> limits) {
        this.limits = limits;
    }
}

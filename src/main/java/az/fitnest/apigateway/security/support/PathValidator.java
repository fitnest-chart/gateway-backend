package az.fitnest.apigateway.security.support;

import java.util.Set;

public class PathValidator {

    private static final Set<String> CSRF_EXEMPTED_PATHS = Set.of();

    private static final Set<String> AUTH_REQUIRED_PATHS = Set.of();

    public static boolean requiresAuthForContent(String path) {
        return false;
    }

    private PathValidator() {
    }

    public static boolean isCsrfExempted(String path) {
        return CSRF_EXEMPTED_PATHS.contains(path);
    }

    public static boolean requiresAuth(String path) {
        return path.startsWith("/api/v1/admin/") ||
               AUTH_REQUIRED_PATHS.contains(path) ||
               requiresAuthForContent(path);
    }

    public static boolean isAdminRoute(String path) {
        return path.startsWith("/api/v1/admin/") &&
               !path.equals("/api/v1/admin/create-admin");
    }

    public static boolean startsWithApiV1(String path) {
        return path.startsWith("/api/v1/");
    }
}

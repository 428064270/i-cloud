package com.icloud.common.utils.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 42806
 */
public class UrlMatcher implements Serializable {

    private static final String TMP_PLACEHOLDER = "@@@@@#####$$$$$";
    private List<Pattern> includePatterns;
    private List<Pattern> excludePatterns;

    public UrlMatcher(String includes, String excludes) {
        this.includePatterns = valueToPatterns(includes);
        this.excludePatterns = valueToPatterns(excludes);
    }

    private List<Pattern> valueToPatterns(String value) {
        List<Pattern> patterns = new ArrayList<>();
        if (value == null) {
            return patterns;
        }

        String[] patternItems = value.split(",");
        for (String patternItem : patternItems) {
            patternItem = patternItem.trim();
            if ("".equals(patternItem)) {
                continue;
            }
            patternItem = patternItem.replace("**", TMP_PLACEHOLDER);
            patternItem = patternItem.replace("*", "[^/]*?");
            patternItem = patternItem.replace(TMP_PLACEHOLDER, "**");
            patternItem = patternItem.replace("**", ".*?");
            patterns.add(Pattern.compile(patternItem));
        }

        return patterns;
    }

    public boolean matches(String url) {
        return matches(includePatterns, url) && !matches(excludePatterns, url);
    }

    private boolean matches(List<Pattern> patterns, String url) {
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }


}

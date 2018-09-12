package com.service.router;

import com.service.router.matcher.AbsMatcher;
import com.service.router.matcher.BrowserMatcher;
import com.service.router.matcher.DirectMatcher;
import com.service.router.matcher.ImplicitMatcher;
import com.service.router.matcher.SchemeMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * AbsMatcher registry.
 * <p>
 * Created by chenenyu on 2017/1/5.
 */
public class MatcherRegistry {

    private static final List<AbsMatcher> registry = new ArrayList<>();

    static {
        registry.add(new DirectMatcher(0x1000));
        registry.add(new SchemeMatcher(0x0100));
        registry.add(new ImplicitMatcher(0x0010));
        registry.add(new BrowserMatcher(0x0000));
        Collections.sort(registry);
    }

    public static void register(AbsMatcher matcher) {
        registry.add(matcher);
        Collections.sort(registry);
    }

    public static List<AbsMatcher> getMatcher() {
        return registry;
    }

    public static void clear() {
        registry.clear();
    }
}

package com.bra.common.scanner.filter;

import java.net.URL;

/**
 * Created by xiaobin268 on 2014-9-23.
 */
public interface IScannerFilter {

    boolean filter(String urlStr, URL url, ClassLoader classLoader);
}

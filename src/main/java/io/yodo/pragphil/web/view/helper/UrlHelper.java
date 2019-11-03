package io.yodo.pragphil.web.view.helper;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
@Scope("request")
public class UrlHelper {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final HttpServletRequest request;

    @Autowired
    public UrlHelper(HttpServletRequest request) {
        this.request = request;
    }

    public String withPage(String path, String pageParam, int value) throws Exception {
        // we're want a relative URL, not interested in the hostname part
        URIBuilder builder = new URIBuilder("localhost");

        for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
            for (String v : e.getValue()) {
                builder.addParameter(e.getKey(), v);
            }
        }

        builder.setParameter(pageParam, value+"");
        return path + "?" + builder.build().getRawQuery();
    }
}

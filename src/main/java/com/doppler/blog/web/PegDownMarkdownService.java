package com.doppler.blog.web;

import org.pegdown.Extensions;
import org.pegdown.LinkRenderer;
import org.pegdown.PegDownProcessor;
import org.pegdown.ToHtmlSerializer;
import org.pegdown.ast.RootNode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("pegdown")
public class PegDownMarkdownService implements MarkdownService {

    private final PegDownProcessor pegdown;

    public PegDownMarkdownService() {
        pegdown = new PegDownProcessor(Extensions.ALL);
    }

    @Override
    public String renderToHtml(String markdownSource) {
        // synchronizing on pegdown instance since neither the processor nor the underlying parser is thread-safe.
        synchronized (pegdown) {
            RootNode astRoot = pegdown.parseMarkdown(markdownSource.toCharArray());
            ToHtmlSerializer serializer = new ToHtmlSerializer(new LinkRenderer());
            return serializer.toHtml(astRoot);
        }
    }
}

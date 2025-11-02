package com.rain.aicodehelper.ai.rag;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 加载RAG：专门复制初始化RAG，并称生成需要的bean
 */
@Configuration
public class RagConfig {

    //注入配置文件中让SpringBoot生成的Embedding模型
    @Resource
    private EmbeddingModel qwenEmbeddingModel;

    //这里缺少了依赖导致初始化错误，因为少了langchain4j-spring-boot-starter依赖
    @Resource
    private EmbeddingStore<TextSegment> embeddingStore;

    //给AIService提供一个内容检索器（）
    @Bean
    public ContentRetriever contentRetriever() {
        // ----- RAG -----
        //1. 加载文档
        List<Document> documents = FileSystemDocumentLoader.loadDocuments("src/main/resources/docs");
        //2. 文档切割：每个文档按照段落进行分割，最大1000个字符，每次最多重叠200个字符
        DocumentByParagraphSplitter documentByParagraphSplitter =
                new DocumentByParagraphSplitter(1000, 200);
        //3. 自定义文档加载器，把文档转换成向量并存储到向量数据库中
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(documentByParagraphSplitter)
                //为了提高文档质量，为每个切割后的文档碎片 TextSegment 添加文档名称作为元信息
                .textSegmentTransformer(textSegment -> TextSegment.from(textSegment.metadata().getString("file_name") +
                        "\n" + textSegment.text(), textSegment.metadata()))
                //使用的向量模型
                .embeddingModel(qwenEmbeddingModel)
                .embeddingStore(embeddingStore)
                .build();

        //加载文档
        ingestor.ingest(documents);

        //4. 因为这个bean需要返回一个内容检索器，这里自定义一个返回
        EmbeddingStoreContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(qwenEmbeddingModel)
                .maxResults(5)  //最多 5 条结果
                .minScore(0.75)  //过滤分数小于0.75的结果，如果文档少了降低数值，文档多了提高数值
                .build();

        return contentRetriever;
    }
}
package com.zlatoust.services;

import com.zlatoust.models.Content;
import com.zlatoust.models.User;
import com.zlatoust.security.auth.AuthContext;
import com.zlatoust.services.dto.LuceneDocument;
import com.zlatoust.services.dto.SearchResult;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LuceneService {

    private final StandardAnalyzer analyzer;
    private final Directory directory;

    public LuceneService() throws IOException {
        this.analyzer = new StandardAnalyzer();
        this.directory = FSDirectory.open(Paths.get("./lucene/directory"));
    }

    public void indexContentsDocuments(List<Content> contents) throws IOException {
        for (Content content : contents) {
            indexContentDocument(content);
        }
    }

    public List<SearchResult> search(String prompt, int numHints) throws Exception {
        DirectoryReader ireader = DirectoryReader.open(directory);
        IndexSearcher isearcher = new IndexSearcher(ireader);

        BooleanQuery.Builder booleanQueryBuilder = new BooleanQuery.Builder();
        String[] searchTerms = prompt.toLowerCase().split(" ");
        booleanQueryBuilder.add(new FuzzyQuery(new Term("title", prompt), 2), BooleanClause.Occur.SHOULD);
        String regexPattern = prompt + ".*";
        booleanQueryBuilder.add(new RegexpQuery(new Term("title", regexPattern)), BooleanClause.Occur.SHOULD);

        for (String term : searchTerms) {
            booleanQueryBuilder.add(new BoostQuery(new TermQuery(new Term("title", term)), 3.0f), BooleanClause.Occur.SHOULD);
            booleanQueryBuilder.add(new FuzzyQuery(new Term("description", term), 2), BooleanClause.Occur.SHOULD);
//            booleanQueryBuilder.add(new FuzzyQuery(new Term("title", term), 2), BooleanClause.Occur.SHOULD);
        }

        List<SearchResult> results = new ArrayList<>();
        Query query = booleanQueryBuilder.build();
        ScoreDoc[] hits = isearcher.search(query, numHints).scoreDocs;
        float topScore = 0;
        if (hits.length > 0) {
            topScore = hits[0].score;
        }
        float scoreDiffThreshold = 3f;
        for (ScoreDoc hit : hits) {
            if (hit.score > 1) {
                if (hit.score > topScore) {
                    topScore = hit.score;
                }
                StoredFields storedFields = isearcher.storedFields();
                Document hitDoc = storedFields.document(hit.doc);
                results.add(new SearchResult(
                        hitDoc.get("ID"),
                        hitDoc.get("title"),
                        hitDoc.get("author"),
                        hitDoc.get("description"),
                        hitDoc.get("shortDescription"),
                        hitDoc.get("contentId"),
                        hitDoc.get("type"),
                        hitDoc.get("url"),
                        hit.score
                ));
            }
        }

        ireader.close();
        final float finalTopScore = topScore;
        return results.stream()
                .filter(sr -> finalTopScore - sr.getScore() < scoreDiffThreshold)
                .collect(Collectors.toList());
    }

    public void deleteDocument(Content content) {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        try (IndexWriter iwriter = new IndexWriter(directory, config)) {
            iwriter.deleteDocuments(new Term("ID", content.getId() + content.getType()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteDocument(LuceneDocument document) {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        try (IndexWriter iwriter = new IndexWriter(directory, config)) {
            iwriter.deleteDocuments(new Term("ID", document.getId() + document.getType()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteDocumentById(String id) {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        try (IndexWriter iwriter = new IndexWriter(directory, config)) {
            iwriter.deleteDocuments(new Term("ID", id));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void indexContentDocument(Content content) throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        try (IndexWriter iwriter = new IndexWriter(directory, config)) {
            Document doc = createDocumentFromContent(content);
            String uniqueId = content.getId() + content.getType();
            iwriter.updateDocument(new Term("ID", uniqueId), doc);
        }
    }

    public void indexLuceneDocument(LuceneDocument document) throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        try (IndexWriter iwriter = new IndexWriter(directory, config)) {
            Document doc = createDocumentFromLuceneDocument(document);
            String uniqueId = document.getId() + document.getType();
            iwriter.updateDocument(new Term("ID", uniqueId), doc);
        }
    }


    public List<LuceneDocument> getAllIndexedDocuments() throws IOException {
        List<LuceneDocument> results = new ArrayList<>();
        try (DirectoryReader ireader = DirectoryReader.open(directory)) {
            IndexSearcher isearcher = new IndexSearcher(ireader);
            Query query = new MatchAllDocsQuery();
            ScoreDoc[] hits = isearcher.search(query, Integer.MAX_VALUE).scoreDocs;

            for (ScoreDoc hit : hits) {
                StoredFields storedFields = isearcher.storedFields();
                Document doc = storedFields.document(hit.doc);
                results.add(new LuceneDocument(
                        doc.get("ID"),
                        doc.get("title"),
                        doc.get("description"),
                        doc.get("type"),
                        doc.get("url"),
                        doc.get("author"),
                        Boolean.parseBoolean(doc.get("generated"))
                ));
            }
        }
        return results;
    }

    private Document createDocumentFromContent(Content content) {
        Document doc = new Document();
        String type = content.getType();
        String uniqueId = content.getId() + type;

        doc.add(new StringField("ID", uniqueId, Field.Store.YES));
        doc.add(new TextField("title", content.getTitle(), Field.Store.YES));
        doc.add(new TextField("description", content.getDescription(), Field.Store.YES));
        doc.add(new TextField("shortDescription", content.getShortDescription() != null ? content.getShortDescription() : "", Field.Store.YES));
        doc.add(new TextField("author", content.getAuthor().getEmail(), Field.Store.YES));
        doc.add(new TextField("type", type, Field.Store.YES));
        doc.add(new TextField("url", "/" + type + "/view/" + content.getId().toString(), Field.Store.YES));
        doc.add(new TextField("contentId", content.getId().toString(), Field.Store.YES));
        doc.add(new TextField("generated", "true", Field.Store.YES));
        return doc;
    }

    private Document createDocumentFromLuceneDocument(LuceneDocument document) {
        Document doc = new Document();
        String type = document.getType();
        String uniqueId = document.getId() + type;

        User user = AuthContext.getUserFromSecurityContext();
        doc.add(new StringField("ID", uniqueId, Field.Store.YES));
        doc.add(new TextField("title", document.getTitle(), Field.Store.YES));
        doc.add(new TextField("description", document.getDescription(), Field.Store.YES));
        doc.add(new TextField("type", type, Field.Store.YES));
        doc.add(new TextField("url", document.getUrl(), Field.Store.YES));
        doc.add(new TextField("author", user.getEmail(), Field.Store.YES));
        doc.add(new TextField("contentId", document.getId(), Field.Store.YES));
        doc.add(new TextField("generated", "false", Field.Store.YES));

        return doc;
    }

}

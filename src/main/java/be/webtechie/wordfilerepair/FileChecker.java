package be.webtechie.wordfilerepair;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.OldWordFileFormatException;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class FileChecker {

    public static void check(Path inputPath, Path outputPath) throws IOException {
        try (FileInputStream fis = new FileInputStream(inputPath.toFile().getAbsolutePath())) {
            String filename = inputPath.toFile().getName();

            String fileExtension = getFileExtension(inputPath.toString());
            String content = "";
            if (fileExtension.equals("doc")) {
                HWPFDocument document = new HWPFDocument(fis);
                WordExtractor DocExtractor = new WordExtractor(document);
                content = firstTextInDoc(DocExtractor);
            } else if (fileExtension.equals("docx")) {
                XWPFDocument documentX = new XWPFDocument(fis);
                List<XWPFParagraph> pera = documentX.getParagraphs();
                content = firstTextInDocX(pera);
            }
            if (!content.isEmpty()) {
                // Save
                System.out.println("Content found in " + filename + ": " + content);
                content = content.replace("\n", "").replace(".", " ")
                        .replace(",", " ").replace("  ", " ").replace(" ", "_");
                if (content.length() > 50) {
                    content = content.substring(0, 50);
                }
                String uniqueFileName = content + "_" + System.currentTimeMillis() + "." + fileExtension;
                Files.copy(inputPath, Paths.get(outputPath.toString(), uniqueFileName));
            } else {
                System.out.println("No content found in " + filename);
            }
        } catch (Exception ex) {
            System.err.println("Exception: " + ex.getMessage());
        } catch (Error err) {
            System.err.println("Error: " + err.getMessage());
        }
    }

    public static String firstTextInDoc(WordExtractor extractor) {
        for (String paragraph : extractor.getParagraphText()) {
            if (!paragraph.isEmpty()) {
                return paragraph;
            }
        }
        return "";
    }

    public static String firstTextInDocX(List<XWPFParagraph> extractor) {
        for (XWPFParagraph paragraph : extractor) {
            if (!paragraph.getParagraphText().isEmpty()) {
                return paragraph.getParagraphText();
            }
        }
        return "";
    }

    public static String getFileExtension(String filename) {
        String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
        return extension;
    }
}
